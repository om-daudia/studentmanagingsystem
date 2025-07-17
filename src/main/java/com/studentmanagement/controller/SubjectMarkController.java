package com.studentmanagement.controller;

import com.studentmanagement.dto.SubjectMarkDTO;
import com.studentmanagement.service.SubjectMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students/{studentId}/subjects-marks")
public class SubjectMarkController {
    @Autowired
    SubjectMarkService subjectMarkService;

    @GetMapping()
    public ResponseEntity<Object> getSubMarksByStudentId(@PathVariable int studentId){
        return subjectMarkService.getSubMarksByStudentId(studentId);
    }

    @PostMapping()
    public ResponseEntity<Object> addSubjectMarks(@RequestBody SubjectMarkDTO subjectMarkDTO, @PathVariable int studentId){
        return subjectMarkService.addSubjectMarks(subjectMarkDTO, studentId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{subMarkId}")
    public ResponseEntity<Object> deleteSubMark(@PathVariable int subMarkId){
        return subjectMarkService.deleteSubMark(subMarkId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{subMarkId}")
    public ResponseEntity<Object> modifyMark(@RequestBody SubjectMarkDTO subjectMarkDTO, @PathVariable int subMarkId, @PathVariable int studentId){
        return subjectMarkService.modifyMark(subjectMarkDTO, subMarkId, studentId);
    }
}
