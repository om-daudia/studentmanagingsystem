package com.students.studmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UniversityService {
    @Value("${UNIVERSITY_URL}")
    private String apiUrl;

    @Autowired
    RestTemplate restTemplate;

    public String getUniversity() {
        return restTemplate.getForObject(apiUrl, String.class);
    }
    public String putUniversity() {
//        String apiUrl = "http://localhost:8081/university";
        return restTemplate.postForObject(apiUrl,"new university", String.class);
    }
    public String deleteUniversity() {
//        String apiUrl = "http://localhost:8081/university";

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.DELETE,
                null,
                String.class
        );

        return response.getBody();
    }
}
