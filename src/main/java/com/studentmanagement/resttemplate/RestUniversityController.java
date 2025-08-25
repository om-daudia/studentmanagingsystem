package com.studentmanagement.resttemplate;

import com.studentmanagement.dto.RestTemplateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university")
public class RestUniversityController {
    @Autowired
    RestUniversityService restUniversityService;

    @GetMapping()
    public RestTemplateResponseDTO getUniversity(){
        return restUniversityService.getUniversity();
    }
    @PostMapping()
    public RestTemplateResponseDTO putUniversity(){
        return restUniversityService.putUniversity();
    }
    @DeleteMapping()
    public RestTemplateResponseDTO deleteUniversity(){
        return restUniversityService.deleteUniversity();
    }
    @GetMapping("/{id}")
    public RestTemplateResponseDTO getUser(@PathVariable int id) {
        return restUniversityService.getUserFromParent(id);
    }
}

