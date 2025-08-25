package com.studentmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void setup() {
        standardRepository.deleteAll();
        schoolRepository.deleteAll();
        baseUrl = "http://localhost:" + port;
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
    void addStandardWithZeroStandardInputTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardRequestDTO requestDTO = new StandardRequestDTO(0); //0 invalid input
        mockMvc.perform(post("/schools/1/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardSchoolNotFoundTest() throws Exception {
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        mockMvc.perform(post("/schools/99/standards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardWhichAlreadyExistTest() throws Exception {
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(schoolEntity);
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
                new StandardEntity(1, schoolEntity),
                new StandardEntity(2, schoolEntity),
                new StandardEntity(3, schoolEntity)
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
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        SchoolEntity schoolEntity = new SchoolEntity("podar school");
        schoolRepository.save(schoolEntity);
        StandardEntity std = new StandardEntity(1);
        std.setSchool(new SchoolEntity(1, "podar school"));
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
        std.setSchool(new SchoolEntity(1, "podar school"));
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
        std.setSchool(new SchoolEntity(1, "podar school"));
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
