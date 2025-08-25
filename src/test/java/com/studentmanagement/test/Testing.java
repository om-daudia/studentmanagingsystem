package com.studentmanagement.test;

import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.controller.StandardController;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.service.StandardService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class Testing {
    @Inject
    StandardController standardController;
    @MockitoBean
    StandardService standardService;
    @Test
    @WithMockUser(username = "om@gmail.com", roles = {"ADMIN"})
    void addStandardSuccessTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.addStandard(requestDTO,1)).thenReturn(
                ResponseHandler.responseEntity(
                        responseDTO,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = standardController.addStandard(requestDTO, 1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
