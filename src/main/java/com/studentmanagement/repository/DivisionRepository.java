package com.studentmanagement.repository;

import com.studentmanagement.entity.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<DivisionEntity, Integer> {
    DivisionEntity findByDivisionAndStandardEntity_Id(char div, int std);
    List<DivisionEntity> findByIdAndStandardEntity_Id(int div, int standard);
    List<DivisionEntity> findByStandardEntity_Id(int std);

    List<DivisionEntity> findByStandardEntityIdAndStudentEntityListResult(int stdId, String res);
    List<DivisionEntity> findByIdAndStandardEntityIdAndStudentEntityListResult(int divId,int stdId, String res);
//    long countByStandardEntity_Id(int stdId);
    long countByStandardEntityIdAndStudentEntityListResult(int stdId, String result);
    long countByIdAndStandardEntityIdAndStudentEntityListResult(int divId, int stdId, String result);
}
