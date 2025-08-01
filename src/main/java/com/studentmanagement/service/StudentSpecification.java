package com.studentmanagement.service;

import com.studentmanagement.dto.SpecificationFilterDTO;
import com.studentmanagement.dto.StudentRequestDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<StudentEntity> matchesSearchKey(String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            try{
                int id = Integer.parseInt(keyword);
                predicates.add(cb.equal(root.get("id"), id));
            }catch (Exception ignore){
            }

            predicates.add(cb.like(cb.lower(root.get("studentName")), "%" + keyword.toLowerCase() + "%"));

            predicates.add(cb.like(cb.lower(root.get("result")), "%" + keyword.toLowerCase() + "%"));
            try{
                int obtainMark = Integer.parseInt(keyword);
                predicates.add(cb.equal(root.get("obtainMarks"), obtainMark));
            }catch (Exception ignore){
            }
            try{
                int percentage = Integer.parseInt(keyword);
                predicates.add(cb.equal(root.get("obtainMarks"), percentage));
            }catch (Exception ignore){
            }
            try {
                LocalDate date = LocalDate.parse(keyword);
                predicates.add(cb.equal(root.get("dateOfBirth"), date));
            } catch (Exception ignore) {}

//            try {
//                DayOfWeek dayOfWeek = DayOfWeek.valueOf(keyword.toUpperCase());
//                predicates.add(cb.equal(cb.function("dayname", String.class, root.get("dateOfBirth")), dayOfWeek.name()));
//            } catch (Exception ignored) {}

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<StudentEntity> matchesFields(StudentResponseDTO requestDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestDTO.getId() != 0) {
                predicates.add(cb.equal(root.get("id"), requestDTO.getId()));
            }

            if (requestDTO.getStudentName() != null && !requestDTO.getStudentName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("studentName")), "%" + requestDTO.getStudentName().toLowerCase() + "%"));
            }

            if (requestDTO.getResult() != null && !requestDTO.getResult().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("result")), "%" + requestDTO.getResult().toLowerCase() + "%"));
            }

            if (requestDTO.getObtainMarks() != 0.0f) {
                predicates.add(cb.equal(root.get("obtainMarks"), requestDTO.getObtainMarks()));
            }

            if (requestDTO.getPercentage() != 0.0f) {
                predicates.add(cb.equal(root.get("percentage"), requestDTO.getPercentage()));
            }

            if (requestDTO.getDateOfBirth() != null) {
                predicates.add(cb.equal(root.get("dateOfBirth"), requestDTO.getDateOfBirth()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<StudentEntity> studentsFilter(SpecificationFilterDTO requestDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestDTO.getStudentName() != null && !requestDTO.getStudentName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("studentName")), "%"+requestDTO.getStudentName().toLowerCase() +"%"));
            }

            if (requestDTO.getFromPercentage() > 0 || requestDTO.getToPercentage() > 0) {
                float fromPercentage = 0;
                float toPercentage = 100;
                if(requestDTO.getFromPercentage() > 0){
                    fromPercentage = requestDTO.getFromPercentage();
                }
                if(requestDTO.getToPercentage() > 0){
                    toPercentage = requestDTO.getToPercentage();
                }
                predicates.add(cb.between(root.get("percentage"), fromPercentage, toPercentage));
            }

            if (requestDTO.getResult() != null && !requestDTO.getResult().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("result")), requestDTO.getResult().toLowerCase()));
            }

            if (requestDTO.getDivisionList() != null && !requestDTO.getDivisionList().isEmpty()) {
                predicates.add(root.get("divisionEntity").get("id").in(requestDTO.getDivisionList()));
            }
            if (requestDTO.getDivisionList() != null && !requestDTO.getStandardList().isEmpty()) {
                predicates.add(root.get("divisionEntity").get("standardEntity").get("id").in(requestDTO.getStandardList()));
            }

            if (requestDTO.getFromDOB() != null || requestDTO.getToDOB() != null) {
                if (requestDTO.getToDOB().isAfter(requestDTO.getFromDOB())) {
                    LocalDate fromDOB = LocalDate.of(1900, 1, 1);
                    LocalDate toDOB = LocalDate.now();
                    if(requestDTO.getFromDOB() != null){
                        fromDOB = requestDTO.getFromDOB();
                    }
                    if(requestDTO.getToDOB() != null){
                        toDOB = requestDTO.getToDOB();
                    }
                    predicates.add(cb.between(root.get("dateOfBirth"), fromDOB, toDOB));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}

