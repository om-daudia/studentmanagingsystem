package com.studentmanagement.repository;

import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<StandardEntity, Integer> {
    StandardEntity findByStandardAndSchoolEntityId(int std, int school);
    StandardEntity findByIdAndSchoolEntityId(int std, int school);
    List<StandardEntity> findBySchoolEntityId(int schoolId);
}
