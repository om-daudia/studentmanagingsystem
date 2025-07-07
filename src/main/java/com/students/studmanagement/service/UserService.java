package com.students.studmanagement.service;

import com.students.studmanagement.dto.UserDTO;
import com.students.studmanagement.entity.UserEntity;
import com.students.studmanagement.exeptionhandling.InvelidTokenException;
import com.students.studmanagement.exeptionhandling.USerNotExist;
import com.students.studmanagement.repository.UserRepository;
import com.students.studmanagement.response.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<Object> addUser(UserDTO userDTO){
        try {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            UserEntity user = modelMapper.map(userDTO, UserEntity.class);
            userRepository.save(user);
            userDTO.setToken(jwtService.getToken(userDTO));
            return ResponseHandler.responseEntity(
                    userDTO,
                    "successful",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("user not added {}",e.getMessage(), e);
            throw new USerNotExist(e.getMessage());
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
            log.error("email and password not match");
            throw new USerNotExist("email and password not match");
        }
    }
}
