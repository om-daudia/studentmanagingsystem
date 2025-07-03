package com.students.studmanagement.controller;

import com.students.studmanagement.dto.SchoolRequestDTO;
import com.students.studmanagement.dto.SchoolResponseDTO;
import com.students.studmanagement.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping()
    public ResponseEntity<Object> getAllSchools(){
        return schoolService.getAllSchools();
    }
    @PostMapping()
    public ResponseEntity<Object> addSchool(@RequestBody SchoolRequestDTO schoolRequest){
        return schoolService.addSchool(schoolRequest);
    }
    @GetMapping("/{schoolId}")
    public ResponseEntity<Object> getSchoolById(@PathVariable int schoolId){
        return schoolService.getSchoolById(schoolId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<Object> deleteSchool(@PathVariable int schoolId){
        return schoolService.deleteSchool(schoolId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{schoolId}")
    public ResponseEntity<Object> modifyschool(@RequestBody SchoolResponseDTO schoolResponseDto, @PathVariable int schoolId){
        return schoolService.modifySchool(schoolResponseDto, schoolId);
    }
}
