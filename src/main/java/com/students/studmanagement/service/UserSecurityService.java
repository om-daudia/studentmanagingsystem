package com.students.studmanagement.service;

import com.students.studmanagement.entity.UserEntity;
import com.students.studmanagement.entity.UserPrincipal;
import com.students.studmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user != null){
            return new UserPrincipal(user);
        }
        else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
