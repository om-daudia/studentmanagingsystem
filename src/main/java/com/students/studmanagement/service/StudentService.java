package com.students.studmanagement.service;

import com.students.studmanagement.dto.StudentRequestDTO;
import com.students.studmanagement.dto.StudentResponseDTO;
import com.students.studmanagement.entity.*;
import com.students.studmanagement.exeptionhandling.DataNotFoundException;
import com.students.studmanagement.repository.DivisionRepository;
import com.students.studmanagement.repository.SchoolRepository;
import com.students.studmanagement.repository.StandardRepository;
import com.students.studmanagement.repository.StudentRepository;
import com.students.studmanagement.response.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(student -> new StudentResponseDTO(student.getId(),student.getStudentName(),student.getObtainMarks(),student.getPercentage(),student.getResult())).collect(Collectors.toList());
        return ResponseHandler.responseEntity(
                list,
                "all students list",
                true,
                HttpStatus.OK
        );
    }


    public ResponseEntity<Object> addStudent(StudentRequestDTO studentRequest, int divisionId){
        try {
            DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new DataNotFoundException("division not found"));

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
        StudentEntity findStudent = studentRepository.findById(studentId).orElseThrow(() -> new DataNotFoundException("student not found"));
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
            StudentEntity student = studentRepository.findById(studentId).orElseThrow(()->new DataNotFoundException("student not found"));
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
            throw new DataNotFoundException("something wrong");
        }
    }

    public ResponseEntity<Object> modifyStudent(StudentResponseDTO studentResponseDTO, int studentId) {
        StudentEntity updateStudent = studentRepository.findById(studentId).orElseThrow(() -> new DataNotFoundException("student not found"));

        updateStudent.setStudentName(studentResponseDTO.getStudentName());
        studentRepository.save(updateStudent);
        return ResponseHandler.responseEntity(
                updateStudent,
                "modify successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> getStudentById(int studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> new DataNotFoundException("student not found"));
        StudentResponseDTO studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        return ResponseHandler.responseEntity(
                studentResponseDTO,
                "modify successfully",
                true,
                HttpStatus.OK
        );
    }
}

