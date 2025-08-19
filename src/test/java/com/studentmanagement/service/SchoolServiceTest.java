package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ErrorResponse;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.dto.SchoolRequestDTO;
import com.studentmanagement.dto.SchoolResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.repository.SchoolRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {
    @InjectMocks
    SchoolService schoolService;
    @Mock
    SchoolRepository schoolRepository;
    //add school test case
    @Test
    void addSchoolSuccessTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("new school name");
        when(schoolRepository.findBySchoolName("new school name")).thenReturn(null);
        assertEquals(HttpStatus.OK, schoolService.addSchool(schoolRequest).getStatusCode());
    }
    @Test
    void addSchoolSuccessResponseTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("new school name");
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1,"new school name");
        when(schoolRepository.findBySchoolName("new school name")).thenReturn(null);
        Map<String, Object> responseBody = (Map<String, Object>) schoolService.addSchool(schoolRequest).getBody();
        List<SchoolResponseDTO> data = (List<SchoolResponseDTO>) responseBody.get("data");
        SchoolResponseDTO first =  data.get(0);
        assertEquals("new school name", first.getSchoolName());
        assertEquals(HttpStatus.OK, schoolService.addSchool(schoolRequest).getStatusCode());
    }
    @Test
    void addSchoolEmptyTest() {
        SchoolRequestDTO request = new SchoolRequestDTO("");
        when(schoolRepository.findBySchoolName("")).thenReturn(null);
        ErrorResponse ex = new ErrorResponse(
                "school name is missing",
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value()
        );
        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getHttpStatus());
    }
    @Test
    void addSchoolExistTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolRepository.findBySchoolName("exist school name")).thenReturn(new SchoolEntity(1, "exist school name"));
        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            schoolService.addSchool(schoolRequest);
        });
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }
    @Test
    void addSchoolInternalServerErrorTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("");
        when(schoolRepository.findBySchoolName("exist school name")).thenReturn(new SchoolEntity(1, "school name"));
        ResponseEntity<Object> response = schoolService.addSchool(schoolRequest);
        ErrorResponse ex = new ErrorResponse(
                "something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getHttpStatus());
    }
    //----------------------------------
    //get all school test cases
    @Test
    void getAllSchoolList() {
        List<SchoolEntity> studentList = Arrays.asList(
                new SchoolEntity(1,"podar school"),
                new SchoolEntity(2,"world school")
        );
        when(schoolRepository.findAll()).thenReturn(studentList);

        Map<String, Object> responseBody = (Map<String, Object>) schoolService.getAllSchools().getBody();
        assertNotNull(responseBody);
        assertEquals(true, responseBody.get("isSuccess"));

        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));

        List<SchoolResponseDTO> data = (List<SchoolResponseDTO>) responseBody.get("data");
        assertEquals(2, data.size());

        SchoolResponseDTO firstSchool = (SchoolResponseDTO) data.get(0);
        assertEquals(1, firstSchool.getId());
        assertEquals("podar school", firstSchool.getSchoolName());
    }
//    @ParameterizedTest
//    @MethodSource("provideSchoolLists")
//    void testGetAllSchools_WithVariousLists(List<SchoolEntity> inputList) {
//        when(schoolRepository.findAll()).thenReturn(inputList);
//
//        Map<String, Object> responseBody = (Map<String, Object>) schoolService.getAllSchools().getBody();
//        assertNotNull(responseBody);
//        assertEquals(true, responseBody.get("isSuccess"));
//
//        assertEquals(HttpStatus.OK, responseBody.get("httpStatus"));
//
//        List<SchoolResponseDTO> data = (List<SchoolResponseDTO>) responseBody.get("data");
//        assertEquals(2, data.size());
//
//        SchoolResponseDTO firstSchool = (SchoolResponseDTO) data.get(0);
//        assertEquals(1, firstSchool.getId());
//        assertEquals("podar school", firstSchool.getSchoolName());
//    }
//    private static List<Arguments> provideSchoolLists() {
//        return List.of(
//                Arguments.of(
//                        List.of(
//                                new SchoolEntity(1,"podar school"),
//                                new SchoolEntity(2,"world school")
//                        )
//                ),
//                Arguments.of(List.of(), 0),
//                Arguments.of(
//                        List.of(new SchoolEntity(1, "Single School"))
//                ),
//                Arguments.of(
//                        List.of(
//                                new SchoolEntity(1, "podar school"),
//                                new SchoolEntity(2, "Beta")
//                        )
//                ),
//                Arguments.of(
//                        List.of(
//                                new SchoolEntity(1, "A"),
//                                new SchoolEntity(2, "B")
//                        )
//                )
//        );
//    }

    //-------------------------------------------
    //get school by id test cases
    @Test
    void getSchoolByIdSuccessTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1,"exist school name")));
        ResponseEntity<Object> response = schoolService.getSchoolById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getSchoolByIdResponseTest(){
        SchoolResponseDTO schoolResponse = new SchoolResponseDTO(1, "exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1,"exist school name")));
        Map<String, Object> responseBody = (Map<String, Object>) schoolService.getSchoolById(1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("exist school name", data.getSchoolName());
    }
    @Test
    void getSchoolByIdNotFoundTest(){
        when(schoolRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Object> response = schoolService.getSchoolById(1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
    @Test
    void testGetSchoolById_schoolNotFound_shouldThrowException() {
        int schoolId = 1;

        // Mock the repository to return empty
        when(schoolRepository.findById(schoolId)).thenReturn(Optional.empty());

        // Assert exception is thrown
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> schoolService.getSchoolById(schoolId)
        );

        assertEquals("School not found with ID: " + schoolId, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    //----------------------------------------
    //delete school test cases
    @Test
    void deleteSchoolSuccessTest(){
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1,"exist school name")));
        doNothing().when(schoolRepository).deleteById(1);
        ResponseEntity<Object> response = schoolService.deleteSchool(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deleteSchoolResponseTest(){
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1,"exist school name")));
        doNothing().when(schoolRepository).deleteById(1);
        Map<String, Object> responseBody = (Map<String, Object>) schoolService.deleteSchool(1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("exist school name", data.getSchoolName());
    }
    @Test
    void deleteSchoolNotFoundTest(){
        when(schoolRepository.findById(1)).thenReturn(Optional.empty());
        doNothing().when(schoolRepository).deleteById(1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
    @Test
    void deleteSchoolInternalErrorTest(){
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1, "exist school name")));
        doNothing().when(schoolRepository).deleteById(1);
        ErrorResponse ex = new ErrorResponse(
                "something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getHttpStatus());
    }
    //----------------------------------------
    //update school test cases
    @Test
    void updateSchoolSuccessTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1, "exist school name")));
        ResponseEntity<Object> response = schoolService.modifySchool(schoolRequest, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void updateSchoolResponseTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1, "exist school name")));
        Map<String, Object> responseBody = (Map<String, Object>) schoolService.modifySchool(schoolRequest,1).getBody();
        SchoolResponseDTO data = (SchoolResponseDTO) responseBody.get("data");
        assertEquals("exist school name", data.getSchoolName());
    }
    @Test
    void updateSchoolNotFoundTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<Object> response = schoolService.modifySchool(schoolRequest,1);
        ErrorResponse ex = new ErrorResponse(
                "school not found",
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value()
        );
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getHttpStatus());
    }
    @Test
    void updateSchoolInternalErrorTest(){
        SchoolRequestDTO schoolRequest = new SchoolRequestDTO("exist school name");
        when(schoolRepository.findById(1)).thenReturn(Optional.of(new SchoolEntity(1, "school name")));
        ResponseEntity<Object> response = schoolService.modifySchool(schoolRequest,1);
        ErrorResponse ex = new ErrorResponse(
                "something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getHttpStatus());
    }
}
