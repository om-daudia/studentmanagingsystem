package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
     StudentEntity findByIdAndStudentName(int id, String name);
//     List<StudentEntity> findByDivisionEntity_DivisionId_StandardId(int std);
     List<StudentEntity> findTop3ByDivisionEntityIdAndResultOrderByPercentageDesc(int divisionId, String res);
}
