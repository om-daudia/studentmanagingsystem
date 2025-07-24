package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
     StudentEntity findByIdAndStudentName(int id, String name);
//     List<StudentEntity> findByDivisionEntity_DivisionId_StandardId(int std);
     List<StudentEntity> findTop3ByDivisionEntityIdAndResultOrderByPercentageDesc(int divisionId, String res);
     List<StudentEntity> findByDateOfBirthBetweenAndDivisionEntityStandardEntityId(LocalDate from, LocalDate to, int standard);

     List<StudentEntity> findByDivisionEntityStandardEntityId(int std);
     List<StudentEntity> findByDateOfBirthBetween(LocalDate fromDate, LocalDate toDate);
     Page<StudentEntity> findByDateOfBirthBetweenAndDivisionEntityStandardEntityId(
             LocalDate from,
             LocalDate to,
             int stdId,
             Pageable pageable
     );
     Page<StudentEntity> findByDivisionEntityStandardEntityId(int std, Pageable pageable);
     Page<StudentEntity> findByDateOfBirthBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
