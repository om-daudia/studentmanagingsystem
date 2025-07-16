package com.students.studmanagement.service;

import com.students.studmanagement.dto.SubjectMarkDTO;
import com.students.studmanagement.entity.StudentEntity;
import com.students.studmanagement.entity.SubjectMarkEntity;
import com.students.studmanagement.exeptionhandling.ApplicationException;
import com.students.studmanagement.repository.StudentRepository;
import com.students.studmanagement.repository.SubjectMarkRepository;
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
public class SubjectMarkService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectMarkRepository subjectMarkRepository;
    @Autowired
    StudentService studentService;
    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<Object> getSubMarksByStudentId(int studentId) {
        StudentEntity studentEntiry = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));

        List<SubjectMarkEntity> subjectMarks = subjectMarkRepository.findByStudentEntity_Id(studentId);
        List<SubjectMarkDTO> subMarksList = subjectMarks.stream()
                .map(sm -> new SubjectMarkDTO(sm.getId(), sm.getSubjectName(), sm.getMarks())).collect(Collectors.toList());
        return ResponseHandler.responseEntity(
                subMarksList,
                "subject and mark valailable",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> addSubjectMarks(SubjectMarkDTO subjectMarkDTO, int studentId) {

        try {
            StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));
            SubjectMarkEntity findSubMark = subjectMarkRepository.findBySubjectNameAndStudentEntity_Id(subjectMarkDTO.getSubName(), studentId);
            if (findSubMark == null) {
                SubjectMarkEntity submark = modelMapper.map(subjectMarkDTO, SubjectMarkEntity.class);
                submark.setStudentEntity(student);
                subjectMarkRepository.save(submark);
                studentService.calculateMarks(studentId);
                return ResponseHandler.responseEntity(
                        submark,
                        "successful",
                        true,
                        HttpStatus.OK
                );
            } else {
                return ResponseHandler.responseEntity(
                        "subject & mark already exist",
                        "unsuccessful",
                        false,
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return ResponseHandler.responseEntity(
                    "something wroeng",
                    "unsuccessful",
                    false,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    public ResponseEntity<Object> modifyMark(SubjectMarkDTO subjectMarkDTO, int subMarkId, int studentId){
        SubjectMarkEntity subMark = subjectMarkRepository.findById(subMarkId).orElseThrow(() -> new ApplicationException("subject and mark not found", HttpStatus.NOT_FOUND));

        subMark.setMarks(subjectMarkDTO.getMarks());
        subjectMarkRepository.save(subMark);
        studentService.calculateMarks(studentId);
        return ResponseHandler.responseEntity(
                subMark,
                "successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteSubMark(int subMarkId) {
        SubjectMarkEntity findSubMark = subjectMarkRepository.findById(subMarkId).orElseThrow(() -> new ApplicationException("subject and mark not found", HttpStatus.NOT_FOUND));

        subjectMarkRepository.deleteById(subMarkId);
        return ResponseHandler.responseEntity(
                "subject & mark deleted",
                "successfully",
                true,
                HttpStatus.OK
        );
    }
}
