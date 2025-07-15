package com.students.studmanagement.resttemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.students.studmanagement.exeptionhandling.ApplicationException;
import com.students.studmanagement.response.ErrorResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class UniversityService {
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
            // Extract the response body as a string
            String responseBody = ex.getResponseBodyAsString();

            // Parse the JSON response body
            JSONObject errorJson = new JSONObject(responseBody);

            // Extract the custom error message from the "msg" field
            String errorMessage = errorJson.optString("msg", "Unknown error");
            int httpStatus = errorJson.optInt("httpStatus", 401);
            throw new ApplicationException(errorMessage,HttpStatus.valueOf(httpStatus));
        }catch (Exception ex) {
            throw new RuntimeException("Something went wrong");
        }
    }
}
