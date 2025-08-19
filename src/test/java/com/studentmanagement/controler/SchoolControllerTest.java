package com.studentmanagement.controler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ErrorResponse;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.controller.SchoolController;
import com.studentmanagement.dto.SchoolRequestDTO;
import com.studentmanagement.dto.SchoolResponseDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.service.SchoolService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SchoolControllerTest {
    @InjectMocks
    SchoolController schoolController;
    @Mock
    SchoolService schoolService;
    //get all schools test cases
    @Test
    void getAllSchoolsHttpCodeTest(){
        List<SchoolResponseDTO> schoolList = Arrays.asList(
                new SchoolResponseDTO(1, "podar school"),
                new SchoolResponseDTO(2, "world school")
        );
        when(schoolService.getAllSchools()).thenReturn(
                ResponseHandler.responseEntity(
                schoolList,
                "successful",
                true,
                HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = schoolController.getAllSchools();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getAllSchoolsResponseEmptyTest(){
        List<SchoolEntity> schoolList = Arrays.asList(
        );
        when(schoolService.getAllSchools()).thenReturn(
                ResponseHandler.responseEntity(
                        schoolList,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolController.getAllSchools().getBody();
        List<SchoolResponseDTO> data = (List<SchoolResponseDTO>) responseBody.get("data");
        assertTrue(data.isEmpty());
    }
    @Test
    void getAllSchoolsResponseTest(){
        List<SchoolResponseDTO> schoolList = Arrays.asList(
                new SchoolResponseDTO(1, "podar school"),
                new SchoolResponseDTO(2, "world school")
        );
        when(schoolService.getAllSchools()).thenReturn(
                ResponseHandler.responseEntity(
                        schoolList,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolController.getAllSchools().getBody();
        List<SchoolResponseDTO> data = (List<SchoolResponseDTO>) responseBody.get("data");
        SchoolResponseDTO first =  data.get(0);
        assertEquals("podar school", first.getSchoolName());
    }
    //--------------------------------------------------
    //add school test cases
    @Test
    void addSchoolSuccessTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("new school name");
        when(schoolService.addSchool(schoolRequest)).thenReturn(
                ResponseHandler.responseEntity(
                    new SchoolRequestDTO("new school name"),
                    "successful",
                    true,
                    HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = schoolService.addSchool(schoolRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void addSchoolResponseTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("new school name");
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "new school name");
        when(schoolService.addSchool(schoolRequest)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolService.addSchool(schoolRequest).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("new school name", data.getSchoolName());
    }
    @Test
    void addSchoolConflictTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolService.addSchool(schoolRequest)).thenReturn(null);
        ResponseEntity<Object> response = schoolController.addSchool(schoolRequest);
        ErrorResponse ex = new ErrorResponse(
                "school already exist",
                HttpStatus.CONFLICT.name(),
                HttpStatus.CONFLICT.value()
        );
        assertEquals(HttpStatus.CONFLICT.value(), ex.getHttpStatus());
    }
    @Test
    void addSchoolEmptyInputTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("");
        when(schoolService.addSchool(schoolRequest)).thenReturn(null);
        ResponseEntity<Object> responseBody = schoolController.addSchool(schoolRequest);
        ErrorResponse ex = new ErrorResponse(
                "school name is missing",
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value()
        );
        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getHttpStatus());
    }
    //-----------------------------------------
    //get school by id test cases
    @Test
    void getSchoolByIdSuccessTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolService.getSchoolById(1)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = schoolController.getSchoolById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getSchoolByIdResponseTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolService.getSchoolById(1)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolController.getSchoolById(1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("exist school name", data.getSchoolName());
    }
    @Test
    void getSchoolByIdNotFoundTest(){
        when(schoolService.getSchoolById(1)).thenReturn(null);
        ResponseEntity<Object> response = schoolController.getSchoolById(1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
    //--------------------------------
    //delete school test cases
    @Test
    void deleteSchoolSuccessTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolService.deleteSchool(1)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = schoolController.deleteSchool(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deleteSchoolResponseTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolService.deleteSchool(1)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolController.deleteSchool(1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("exist school name", data.getSchoolName());
    }

    @Test
    void deleteSchoolNotFoundTest(){
        when(schoolService.deleteSchool(1)).thenReturn(null);
        ResponseEntity<Object> response = schoolController.deleteSchool(1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
    //---------------------------------------------------------------
    //update school test cases
    @Test
    void updateSchoolSuccessTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "updated school name");
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolService.modifySchool(schoolRequest, 1)).thenReturn(
                ResponseHandler.responseEntity(
                schoolResponse,
                "successful",
                true,
                HttpStatus.OK
            )
        );
        ResponseEntity<Object> response = schoolController.modifyschool(schoolRequest,1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void updateSchoolResponseTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "updated school name");
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolService.modifySchool(schoolRequest,1)).thenReturn(
                ResponseHandler.responseEntity(
                        schoolResponse,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) schoolController.modifyschool(schoolRequest,1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("updated school name", data.getSchoolName());
    }
    @Test
    void updateSchoolNotFound(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolService.modifySchool(schoolRequest,1)).thenReturn(null);
        ResponseEntity<Object> response = schoolController.modifyschool(schoolRequest,1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
}
