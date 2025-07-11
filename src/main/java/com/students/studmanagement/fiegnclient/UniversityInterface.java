package com.students.studmanagement.fiegnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "unversity", url = "http://localhost:8081/university")
public interface UniversityInterface {
    @GetMapping()
    public String getUniversity();
    @PostMapping()
    public String putUniversity();
    @DeleteMapping()
    public String deleteUniversity();
}
