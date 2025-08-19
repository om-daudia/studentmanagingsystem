package com.studentmanagement.controler;
import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.controller.StandardController;
import com.studentmanagement.dto.SchoolResponseDTO;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.service.StandardService;
import org.antlr.v4.runtime.misc.ObjectEqualityComparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StandardControllerTest {
    @InjectMocks
    StandardController standardController;
    @Mock
    StandardService standardService;

    //get al standard
    @Test
    void getAllStandardSuccessTest(){
        List<StandardResponseDTO> standardList =  Arrays.asList(
                new StandardResponseDTO(1, 1),
                new StandardResponseDTO(2,2),
                new StandardResponseDTO(3,3)
        );
        when(standardService.getAllStandards()).thenReturn(
                ResponseHandler.responseEntity(
                        standardList,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = standardController.getAllStandards();
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        List<StandardResponseDTO> data = (List<StandardResponseDTO>) responseBody.get("data");
        assertEquals(1,data.get(0).getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getAllStandardEmptyTest(){
        List<StandardResponseDTO> standardList = Collections.emptyList();
        when(standardService.getAllStandards()).thenReturn(
                ResponseHandler.responseEntity(
                        standardList,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        Map<String, Object> responseBody = (Map<String, Object>) standardController.getAllStandards().getBody();
        List<StandardResponseDTO> data = (List<StandardResponseDTO>) responseBody.get("data");
        assertTrue(data.isEmpty());
    }

    //add standard test cases
    @Test
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
    @Test
    void addStandardSchoolNotFoundTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        when(standardService.addStandard(requestDTO,1)).thenThrow(
                new ApplicationException("school not found", HttpStatus.NOT_FOUND)
        );
        ApplicationException exception = assertThrows(
                    ApplicationException.class,
                    () -> standardService.addStandard(requestDTO,1)
            );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void addStandardStandardAlreadyExistTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        when(standardService.addStandard(requestDTO,1)).thenThrow(
                new ApplicationException("school already exist", HttpStatus.CONFLICT)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.addStandard(requestDTO,1)
        );
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }
    @Test
    void addStandardInvalidInputTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(0);
        when(standardService.addStandard(requestDTO,1)).thenThrow(
                new ApplicationException("standard is missing", HttpStatus.BAD_REQUEST)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.addStandard(requestDTO,1)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    //get standard by id
    @Test
    void getStandardByIdSuccessTest(){
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.getStandardById(1)).thenReturn(
                ResponseHandler.responseEntity(
                        responseDTO,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response =  standardController.getStandardById(1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void getStandardByIdNotFoundTest(){
        when(standardService.getStandardById(99)).thenThrow(
                new ApplicationException("standard not found", HttpStatus.NOT_FOUND)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardController.getStandardById(99));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    //delete standard test case
    @Test
    void deleteStandardSuccessTest(){
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.deleteStandard(1)).thenReturn(
                ResponseHandler.responseEntity(
                        responseDTO,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = standardController.deleteStandard(1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deleteStandardInputMissingTest(){
        when(standardService.deleteStandard(0)).thenThrow(
                new ApplicationException("standard is missing", HttpStatus.BAD_REQUEST)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardController.deleteStandard(0)
                );
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
    }
    @Test
    void deleteStandardNotFoundTest(){
        when(standardService.deleteStandard(99)).thenThrow(
                new ApplicationException("standard not found", HttpStatus.NOT_FOUND)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardController.deleteStandard(99)
        );
        assertEquals(HttpStatus.NOT_FOUND,exception.getStatusCode());
    }

    //update standard
    @Test
    void updateStandardSuccessTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.modifyStandard(requestDTO,1)).thenReturn(
                ResponseHandler.responseEntity(
                        responseDTO,
                        "successful",
                        true,
                        HttpStatus.OK
                )
        );
        ResponseEntity<Object> response = standardController.modifyStandard(requestDTO, 1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void updateStandardNotFoundTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.modifyStandard(requestDTO,99)).thenThrow(
                new ApplicationException("standard not found", HttpStatus.NOT_FOUND)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardController.modifyStandard(requestDTO, 99)
                );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void updateStandardInvalidStandardIdTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        StandardResponseDTO responseDTO = new StandardResponseDTO(1, 1);
        when(standardService.modifyStandard(requestDTO,0)).thenThrow(
                new ApplicationException("standard is missing", HttpStatus.BAD_REQUEST)
        );
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardController.modifyStandard(requestDTO, 0)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}
