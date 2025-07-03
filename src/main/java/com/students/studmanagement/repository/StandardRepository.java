package com.students.studmanagement.repository;

import com.students.studmanagement.entity.SchoolEntity;
import com.students.studmanagement.entity.StandardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<StandardEntity, Integer> {
    StandardEntity findByStandardAndSchoolEntity_Id(int std, int school);
    StandardEntity findByIdAndSchoolEntity_Id(int std, int school);
    List<StandardEntity> findBySchoolEntityId(int schoolId);
}
