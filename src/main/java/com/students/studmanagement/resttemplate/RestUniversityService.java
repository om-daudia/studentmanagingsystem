package com.students.studmanagement.resttemplate;

import com.students.studmanagement.common.exceptionhandling.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestUniversityService {
    @Value("${universityurl}")
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
    public String getUserFromParent(int id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl+"/"+id, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ApplicationException(ex.getResponseBodyAsString());
        }catch (Exception ex) {
            throw new RuntimeException("Invalid university request");
        }
    }
}
