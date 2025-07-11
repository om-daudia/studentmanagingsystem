package com.students.studmanagement.fiegnclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university/fiegn")
public class UniversityFiegnController {
    @Autowired
    private UniversityInterface universityInterface;

    @GetMapping
    public String getUniversity(){
        return universityInterface.getUniversity();
    }
    @PostMapping()
    public String putUniversity(){
        return universityInterface.putUniversity();
    }
    @DeleteMapping()
    public String deleteUniversity(){
        return universityInterface.deleteUniversity();
    }
}
