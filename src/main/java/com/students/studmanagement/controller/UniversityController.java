package com.students.studmanagement.controller;

import com.students.studmanagement.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university")
public class UniversityController {
    @Autowired
    UniversityService universityService;

    @GetMapping()
    public String getUniversity(){
        return universityService.getUniversity();
    }
    @PostMapping()
    public String putUniversity(){
        return universityService.putUniversity();
    }
    @DeleteMapping()
    public String deleteUniversity(){
        return universityService.deleteUniversity();
    }
}
