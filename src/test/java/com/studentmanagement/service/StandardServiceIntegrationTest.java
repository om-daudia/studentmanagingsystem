package com.studentmanagement.service;

import com.studentmanagement.dto.SchoolResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmanagement.controller.StandardController;
import com.studentmanagement.controller.UserController;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.dto.UserDTO;
import com.studentmanagement.entity.UserEntity;
import com.studentmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StandardServiceIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SchoolRepository schoolRepository;
    @Autowired
    StandardRepository standardRepository;
    @Autowired
    MockMvc mockMvc;
    private String baseUrl;
    private String jwtToken;

    @BeforeEach()
    void setToken(){
        standardRepository.deleteAll();
        schoolRepository.deleteAll();
        baseUrl = "http://localhost:"+port;
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardSuccessTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);

        mockMvc.perform(post("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.standard").value(1))
                .andExpect(jsonPath("$.message", is("successful")));

    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardInvalidInputTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardRequestDTO requestDTO = new StandardRequestDTO(0);
        mockMvc.perform(post("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardSchoolNotFoundTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        mockMvc.perform(post("/schools/2/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardAlreadyExistTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(new SchoolEntity(1,"podar school"));
        standardRepository.save(std);
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        mockMvc.perform(post("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isConflict());
    }

    //get all standards test case
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void getAllStandardSuccessTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        List<StandardEntity> standardList = Arrays.asList(
          new StandardEntity(1,schoolEntity),
          new StandardEntity(2,schoolEntity),
          new StandardEntity(3,schoolEntity)
        );
        standardRepository.saveAll(standardList);
        mockMvc.perform(get("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].standard", is(1)))
                .andExpect(jsonPath("$.data[1].standard", is(2)))
                .andExpect(jsonPath("$.data[2].standard", is(3)))
                .andExpect(jsonPath("$.message", is("successful")));
        ;
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void getAllStandardTEmptyTest() throws Exception {
        mockMvc.perform(get("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void getStandardbyIdSuccessTest() throws Exception {
        StandardResponseDTO responseDTO = new StandardResponseDTO(1,1);
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(new SchoolEntity(1,"podar school"));
        standardRepository.save(std);
        mockMvc.perform(get("/schools/1/standards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.standard", is(1)))
                .andExpect(jsonPath("$.message", is("successful")))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void getStandardbyIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/schools/1/standards/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("standard not found")));
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void getStandardbyIdInvelidInputTest() throws Exception {
        mockMvc.perform(get("/schools/1/standards/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("standard id missing")));
    }

    //delete standard test cases
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void deleteStandardSuccessTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(new SchoolEntity(1,"podar school"));
        standardRepository.save(std);
        mockMvc.perform(delete("/schools/1/standards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.standard", is(1)))
                .andExpect(jsonPath("$.message", is("successful")));
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void deleteStandardNotFoundTest() throws Exception {
        mockMvc.perform(delete("/schools/1/standards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("standard not found")));
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void deleteStandardInvalidInputTest() throws Exception {
        mockMvc.perform(delete("/schools/1/standards/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("standard id missing")));
    }

    //update standard test cases
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void updateStandardSuccessTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(new SchoolEntity(1,"podar school"));
        standardRepository.save(std);
        StandardRequestDTO requestDTO = new StandardRequestDTO(2);
        mockMvc.perform(patch("/schools/1/standards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.standard").value(2))
                .andExpect(jsonPath("$.message", is("successful")));
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void updateStandardNotFoundTest() throws Exception {
        StandardRequestDTO requestDTO = new StandardRequestDTO(2);
        mockMvc.perform(patch("/schools/1/standards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("standard not found")));
    }
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void updateStandardInvalidInputTest() throws Exception {
        StandardRequestDTO requestDTO = new StandardRequestDTO(0);
        mockMvc.perform(patch("/schools/1/standards/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("invalid input")));
    }
}
