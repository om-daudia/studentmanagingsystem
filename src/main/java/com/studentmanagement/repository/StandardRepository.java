package com.studentmanagement.repository;

import com.studentmanagement.entity.StandardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandardRepository extends JpaRepository<StandardEntity, Integer> {
    StandardEntity findByStandardAndSchoolEntity_Id(int std, int school);
    StandardEntity findByIdAndSchoolEntity_Id(int std, int school);
    List<StandardEntity> findBySchoolEntityId(int schoolId);
}
