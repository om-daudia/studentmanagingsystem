package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.repository.UserRepository;
import com.studentmanagement.dto.UserDTO;
import com.studentmanagement.entity.UserEntity;
import com.studentmanagement.common.response.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Transactional
    public ResponseEntity<Object> addUser(UserDTO userDTO){
        try {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            UserEntity user = modelMapper.map(userDTO, UserEntity.class);
            userRepository.save(user);
            String token = null;

            token = jwtService.getToken(userDTO);
            return ResponseHandler.responseEntity(
                    token,
                    "successful",
                    true,
                    HttpStatus.OK
            );
        }catch (ApplicationException appEx){
            throw new ApplicationException(appEx.getMessage(), appEx.getStatusCode());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<Object> loginUser(UserDTO userDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

        if(authentication.isAuthenticated()){
            String token = jwtService.getToken(userDTO);
            return ResponseHandler.responseEntity(
                token,
                "successful",
                true,
                HttpStatus.OK
            );
        }else {
            throw new ApplicationException("email and password not match", HttpStatus.NOT_FOUND);
        }
    }
}
