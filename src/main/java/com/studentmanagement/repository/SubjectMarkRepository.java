package com.studentmanagement.repository;

import com.studentmanagement.entity.SubjectMarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectMarkRepository extends JpaRepository<SubjectMarkEntity, Integer> {
    List<SubjectMarkEntity> findByStudentEntity_Id(int studentId);
    SubjectMarkEntity findBySubjectNameAndStudentEntity_Id(String subName, int studentId);
}
