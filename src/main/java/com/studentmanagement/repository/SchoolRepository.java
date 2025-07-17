package com.studentmanagement.repository;

import com.studentmanagement.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer> {
    SchoolEntity findBySchoolName(String school);
}
