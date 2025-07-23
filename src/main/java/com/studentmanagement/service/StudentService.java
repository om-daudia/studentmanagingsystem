package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.dto.StudentPageingReportDTO;
import com.studentmanagement.dto.StudentUpdateRequestDTO;
import com.studentmanagement.entity.DivisionEntity;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.entity.SubjectMarkEntity;
import com.studentmanagement.repository.DivisionRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.dto.StudentRequestDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DivisionRepository divisionRepository;
    ModelMapper modelMapper = new ModelMapper();
    public ResponseEntity<Object> getAllStudents(){
        List<StudentResponseDTO> list = studentRepository.findAll().stream()
                .map(student -> new StudentResponseDTO(student.getId(),student.getStudentName(),student.getDateOfBirth(),student.getObtainMarks(),student.getPercentage(),student.getResult())).collect(Collectors.toList());
        return ResponseHandler.responseEntity(
                list,
                "all students list",
                true,
                HttpStatus.OK
        );
    }


    public ResponseEntity<Object> addStudent(StudentRequestDTO studentRequest, int divisionId){
        try {
            DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new ApplicationException("division not found", HttpStatus.NOT_FOUND));

//                Optional<StudentEntity> findstudent = studentRepository.findByIdAndStudentName(student.getId, divisionId);
//                if(findstudent.isEmpty()) { //student check
            StudentEntity studentEntity = modelMapper.map(studentRequest, StudentEntity.class);
            for (SubjectMarkEntity sm : studentEntity.getSubjectMarksEntityList()) {
                sm.setStudentEntity(studentEntity);
            }
            studentEntity.setDivisionEntity(division);
            studentRepository.save(studentEntity);
            calculateMarks(studentEntity.getId());
            return ResponseHandler.responseEntity(
                    studentEntity,
                    "successful",
                    true,
                    HttpStatus.OK
            );
//                }
//                else {
//                    return "Student Exist";
//                }

        } catch (Exception e) {
            return ResponseHandler.responseEntity(
                    e.getMessage(),
                    "unknown error",
                    false,
                    HttpStatus.OK
            );
        }
    }

    public ResponseEntity<Object> deleteStudent(int studentId){
        StudentEntity findStudent = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));
        studentRepository.deleteById(studentId);
        return ResponseHandler.responseEntity(
                "student deleted",
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> calculateMarks(int studentId) {
        try {
            StudentEntity student = studentRepository.findById(studentId).orElseThrow(()->new ApplicationException("student not found", HttpStatus.NOT_FOUND));
            List<SubjectMarkEntity> markList = student.getSubjectMarksEntityList();
            float obtainMark=0;
            float percentage=0;
            int subjectNumber = 0;
            String result = "Fail";

            for(SubjectMarkEntity subjectMark : markList){
                if(subjectMark.getMarks() > 28) {
                    obtainMark += subjectMark.getMarks();
                    subjectNumber++;
                }else {
                    obtainMark = 0;
                    break;
                }
            }
            if(obtainMark > 0) {
                percentage = obtainMark / subjectNumber;
                result = "Pass";
            }
            student.setObtainMarks(obtainMark);
            student.setPercentage(percentage);
            student.setResult(result);
            student.setStudentName(student.getStudentName());
            studentRepository.save(student);
            return ResponseHandler.responseEntity(
                    "marks added",
                    "successful",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something wrong");
        }
    }

    public ResponseEntity<Object> modifyStudent(StudentUpdateRequestDTO studentUpdateRequestDTO, int studentId) {
        StudentEntity updateStudent = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));

        updateStudent.setStudentName(studentUpdateRequestDTO.getStudentName());
        updateStudent.setDateOfBirth(studentUpdateRequestDTO.getDateOfBirth());
        studentRepository.save(updateStudent);
        return ResponseHandler.responseEntity(
                studentUpdateRequestDTO,
                "modify successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> getStudentById(int studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));
        StudentResponseDTO studentResponseDTO = null;
        try {
            studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ResponseHandler.responseEntity(
                studentResponseDTO,
                "modify successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<StudentPageingReportDTO> getStudents(int pageNumber, int pageSize){
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<StudentEntity> studentPage = studentRepository.findAll(pageable);

            List<StudentResponseDTO> dtoList = studentPage.getContent().stream()
                    .map(item -> modelMapper.map(item,StudentResponseDTO.class))
                    .collect(Collectors.toList());

            Page<StudentResponseDTO> dtoPage = new PageImpl<>(dtoList, pageable, studentPage.getTotalElements());

            StudentPageingReportDTO reportDTO = new StudentPageingReportDTO();
            reportDTO.setStudentList(dtoPage.getContent());
            reportDTO.setPageSize(dtoPage.getSize());
            reportDTO.setPageNumber(dtoPage.getNumber());
            reportDTO.setTotalRecords(dtoPage.getTotalElements());
            reportDTO.setTotalPages(dtoPage.getTotalPages());
            return new ResponseEntity<>(reportDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new ApplicationException("paging exception", HttpStatus.BAD_REQUEST);
        }
    }
}

