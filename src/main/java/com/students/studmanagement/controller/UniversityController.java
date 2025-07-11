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
        try {
            return universityService.getUniversity();
        } catch (Exception e) {
            throw new RuntimeException("server not responding");
        }

    }
    @PostMapping()
    public String putUniversity(){
        try {
            return universityService.putUniversity();
        } catch (Exception e) {
            throw new RuntimeException("server not responding");
        }
    }
    @DeleteMapping()
    public String deleteUniversity(){
        try {
            return universityService.deleteUniversity();
        } catch (Exception e) {
            throw new RuntimeException("server not responding");
        }
    }
}
