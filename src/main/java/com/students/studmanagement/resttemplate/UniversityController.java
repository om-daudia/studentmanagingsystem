package com.students.studmanagement.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/{id}")
    public String getUser(@PathVariable int id) {
        return universityService.getUserFromParent(id);
    }
}

