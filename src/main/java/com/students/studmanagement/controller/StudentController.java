package com.students.studmanagement.controller;

import com.students.studmanagement.dto.StudentRequestDTO;
import com.students.studmanagement.dto.StudentResponseDTO;
import com.students.studmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int studentId){
        return studentService.deleteStudent(studentId);
    }

    @PatchMapping("/marks-calculation/{studentId}")
    public ResponseEntity<Object> calculateMarks(@PathVariable int studentId){
        return studentService.calculateMarks(studentId);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<Object> modifyStudent(@RequestBody StudentResponseDTO studentResponseDTO, @PathVariable int studentId){
        return studentService.modifyStudent(studentResponseDTO,studentId);
    }

}
