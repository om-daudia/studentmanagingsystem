package com.studentmanagement.resttemplate;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.dto.RestTemplateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
    public RestTemplate restTemplate;

    public RestTemplateResponseDTO getUniversity() {
        return restTemplate.getForObject(apiUrl, RestTemplateResponseDTO.class);
    }
    public RestTemplateResponseDTO putUniversity() {
        return restTemplate.postForObject(apiUrl,"new university", RestTemplateResponseDTO.class);
    }
    public RestTemplateResponseDTO deleteUniversity() {
        ResponseEntity<RestTemplateResponseDTO> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.DELETE,
                null,
                RestTemplateResponseDTO.class
        );

        return response.getBody();
    }
    public RestTemplateResponseDTO getUserFromParent(int id) {
        try {
            ResponseEntity<RestTemplateResponseDTO> response = restTemplate.getForEntity(apiUrl+"/"+id, RestTemplateResponseDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ApplicationException(ex.getResponseBodyAsString(), HttpStatus.NOT_FOUND);
        }catch (Exception ex) {
            throw new RuntimeException("Invalid university request");
        }
    }
}
