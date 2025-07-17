package com.studentmanagement.repository;

import com.studentmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String pass);
}
