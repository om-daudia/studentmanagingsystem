package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class StandardServiceTest {
    @InjectMocks
    StandardService standardService;
    @Mock
    StandardRepository standardRepository;
    @Mock
    SchoolRepository schoolRepository;

    //get standard list test cases
    @Test
    void getStandardListSuccessTest(){
        List<StandardEntity> standardList =  Arrays.asList(
                new StandardEntity(1, 1),
                new StandardEntity(2,2),
                new StandardEntity(3,3)
        );
        when(standardRepository.findAll()).thenReturn(standardList);
        ResponseEntity<Object> response = standardService.getAllStandards();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getStandardListResponse(){
        List<StandardEntity> standardList =  Arrays.asList(
                new StandardEntity(1, 1),
                new StandardEntity(2,2),
                new StandardEntity(3,3)
        );
        when(standardRepository.findAll()).thenReturn(standardList);
        ResponseEntity<Object> response = standardService.getAllStandards();
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        List<StandardResponseDTO> data = (List<StandardResponseDTO>) responseBody.get("data");
        assertEquals(1, data.get(0).getStandard());
        assertTrue(data.size() == 3);
    }
    @Test
    void getStandardListEmpty(){
        when(standardRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<Object> response = standardService.getAllStandards();
        Map<String, Object> responseBody = (Map<String, Object>) standardService.getAllStandards().getBody();
        List<StandardResponseDTO> data = (List<StandardResponseDTO>) responseBody.get("data");
        assertTrue(data.isEmpty());
    }

    //add standard test cases
    @Test
    void addStandardSuccessTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1,"school name")));
        when(standardRepository.findByStandardAndSchoolEntityId(1,1)).thenReturn(null);
        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);
        ResponseEntity<Object> response = standardService.addStandard(requestDTO, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void addStandardResponseTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);

        when(schoolRepository.findById(1)).thenReturn((Optional.of(new SchoolEntity(1,"school name"))));
        when(standardRepository.findByStandardAndSchoolEntityId(1,1)).thenReturn(null);
        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);

        Map<String, Object> responseBody = (Map<String, Object>) standardService.addStandard(requestDTO, 1).getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
    }
    @Test
    void addStandardAlreadyExistTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);

        when(schoolRepository.findById(1)).thenReturn((Optional.of(new SchoolEntity(1,"school name"))));
        when(standardRepository.findByStandardAndSchoolEntityId(1,1)).thenReturn(new StandardEntity(1,1));
//        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.addStandard(requestDTO, 1)
        );
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }
    @Test
    void addStandardSchoolNotFoundTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);

        when(schoolRepository.findById(99)).thenReturn(Optional.empty());
//        when(standardRepository.findByStandardAndSchoolEntityId(1,1)).thenReturn(new StandardEntity(1,1));
//        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.addStandard(requestDTO, 99)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void addStandardInvalidInputTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(0);

//        when(schoolRepository.findById(99)).thenReturn(Optional.empty());
//        when(standardRepository.findByStandardAndSchoolEntityId(1,1)).thenReturn(new StandardEntity(1,1));
//        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.addStandard(requestDTO, 1)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    //get standard by id test cases
    @Test
    void getStandardByIdSuccessTest(){
        when(standardRepository.findById(1)).thenReturn(Optional.of(new StandardEntity(1,1)));
        ResponseEntity<Object> response = standardService.getStandardById(1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    @Test
    void getStandardByIdNotFoundTest(){
        when(standardRepository.findById(99)).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.getStandardById(99)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void getStandardByIdInvalidInputTest(){
//        when(standardRepository.findById(0)).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.getStandardById(0)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    //delete standard test cases
    @Test
    void deleteStandardSuccessTest(){
        when(standardRepository.findById(1)).thenReturn(Optional.of(new StandardEntity(1,1)));
        doNothing().when(standardRepository).deleteById(1);
        ResponseEntity<Object> response = standardService.deleteStandard(1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deleteStandardNotFoundTest(){
        when(standardRepository.findById(99)).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.deleteStandard(99)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void deleteStandardInvalidInputTest(){
//        when(standardRepository.findById(0)).thenReturn(Optional.empty());
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.deleteStandard(0)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    //update standard test cases
    @Test
    void updateStandardSuccessTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        when(standardRepository.findById(1)).thenReturn(Optional.of(new StandardEntity(1,1)));
        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);
        ResponseEntity<Object> response = standardService.modifyStandard(requestDTO, 1);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(1,data.getStandard());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void updateStandardNotFoundTest(){
        when(standardRepository.findById(99)).thenReturn(Optional.empty());
//        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.deleteStandard(99)
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void updateStandardInputMissingTest() {
//        when(standardRepository.findById(99)).thenReturn(Optional.empty());
//        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> standardService.deleteStandard(0)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    //ArgumentCaptor example
    @Test
    void addStandardSuccessArgTest(){
        StandardRequestDTO requestDTO = new StandardRequestDTO(1);
        SchoolEntity schoolEntity = new SchoolEntity(1, "school name");

        when(schoolRepository.findById(eq(1))).thenReturn(Optional.of(schoolEntity));
        when(standardRepository.findByStandardAndSchoolEntityId(eq(1),eq(1))).thenReturn(null);
        when(standardRepository.save(any(StandardEntity.class))).thenReturn(null);
        ResponseEntity<Object> response = standardService.addStandard(requestDTO, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ArgumentCaptor<Integer> standardArgCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> schoolIdCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(standardRepository).findByStandardAndSchoolEntityId(standardArgCaptor.capture(), schoolIdCaptor.capture());
        assertEquals(1, standardArgCaptor.getValue());
        assertEquals(1, schoolIdCaptor.getValue());

        ArgumentCaptor<Integer> schoolCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(schoolRepository).findById(schoolCaptor.capture());

        Integer schoolId = schoolCaptor.getValue();
        assertEquals(1, schoolId);

        ArgumentCaptor<StandardEntity> standardCaptor = ArgumentCaptor.forClass(StandardEntity.class);
        verify(standardRepository).save(standardCaptor.capture());

        StandardEntity savedEntity = standardCaptor.getValue();
        assertEquals(1, savedEntity.getStandard());
        assertEquals(schoolEntity, savedEntity.getSchool());
    }
}
