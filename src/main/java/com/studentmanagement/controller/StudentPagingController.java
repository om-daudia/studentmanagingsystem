package com.studentmanagement.controller;

import com.studentmanagement.dto.StudentPageingReportDTO;
import com.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentPagingController {
    @Autowired
    StudentService studentService;

    @GetMapping("/page/{pageNumber}/size/{pageSize}")
    public ResponseEntity<StudentPageingReportDTO> getStudents(
            @PathVariable int pageNumber, @PathVariable int pageSize){
        try {
            return studentService.getStudents(pageNumber, pageSize);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
