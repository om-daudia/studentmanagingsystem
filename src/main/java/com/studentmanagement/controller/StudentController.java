package com.studentmanagement.controller;

import com.studentmanagement.dto.StudentRequestDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import com.studentmanagement.dto.StudentUpdateRequestDTO;
import com.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/divisions/{divisionId}/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping()
    public ResponseEntity<Object> getAllStudents(){
        return studentService.getAllStudents();

    }

    @PostMapping()
    public ResponseEntity<Object> addStudent(@RequestBody StudentRequestDTO studentRequest, @PathVariable int divisionId){
        return studentService.addStudent(studentRequest, divisionId);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Object> getStudentById(@PathVariable int studentId){
        return studentService.getStudentById(studentId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int studentId){
        return studentService.deleteStudent(studentId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/marks-calculation/{studentId}")
    public ResponseEntity<Object> calculateMarks(@PathVariable int studentId){
        return studentService.calculateMarks(studentId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{studentId}")
    public ResponseEntity<Object> modifyStudent(@RequestBody StudentUpdateRequestDTO studentUpdateRequestDTO, @PathVariable int studentId){
        return studentService.modifyStudent(studentUpdateRequestDTO,studentId);
    }
}
