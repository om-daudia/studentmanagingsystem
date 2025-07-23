package com.studentmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "university", url = "${university.url}")
public interface FeignUniversityInterface {
    @GetMapping()
    public String getUniversity();
    @PostMapping()
    public String putUniversity();
    @DeleteMapping()
    public String deleteUniversity();
}
