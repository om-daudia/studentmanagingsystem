package com.studentmanagement.feignclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university/fiegn")
public class FiegnUniversityController {
    @Autowired
    private FeignUniversityInterface feignUniversityInterface;

    @GetMapping
    public String getUniversity(){
        return feignUniversityInterface.getUniversity();
    }
    @PostMapping()
    public String putUniversity(){
        return feignUniversityInterface.putUniversity();
    }
    @DeleteMapping()
    public String deleteUniversity(){
        return feignUniversityInterface.deleteUniversity();
    }
}
