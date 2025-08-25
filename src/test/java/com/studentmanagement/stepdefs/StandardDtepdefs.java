package com.studentmanagement.stepdefs;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.service.StandardService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.Before;


import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StandardDtepdefs {
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private StandardRepository standardRepository;
    @InjectMocks
    private StandardService standardService;
    SchoolEntity schoolEntity;
    ResponseEntity<Object> response;
    ApplicationException exception;
    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Given("a school exists with ID {int}")
    public void school_Exist_With_Id(int school){
        when(schoolRepository.findById(school)).thenReturn(Optional.of(new SchoolEntity(school, "podar school")));
        schoolEntity = schoolRepository.findById(school).get();
    }

    @And("there is no standard {int} for that school {int}")
    public void there_is_no_standard_for_that_school(int standard, int school){
        when(standardRepository.findByStandardAndSchoolEntityId(standard, school)).thenReturn(null);
        standardRepository.findByStandardAndSchoolEntityId(standard, school);
    }
    @When("I save a standard {int} for school with ID {int}")
    public void save_standard(int standard, int school){
        StandardEntity requestEntity = new StandardEntity(standard);
        StandardRequestDTO requestDTO = new StandardRequestDTO(standard);
        when(standardRepository.save(requestEntity)).thenReturn(new StandardEntity(standard));
        response = standardService.addStandard(requestDTO, school);
    }
    @Then("the standard should be saved successfully")
    public void standard_save_successfully(){
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        StandardResponseDTO data = (StandardResponseDTO) responseBody.get("data");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, data.getStandard());
    }

    public @Given("a school does not exist with ID {int}")
    void school_does_not_exist(int school){
        when(schoolRepository.findById(school))
                .thenThrow(
                       new  ApplicationException("school not found", HttpStatus.NOT_FOUND)
                );
    }

    @When("I try to save a standard {int} for school with ID {int}")
    public void try_to_save_standard_school_not_found(int standard, int school){
        StandardRequestDTO requestDTO = new StandardRequestDTO(standard);
        try{
            response = standardService.addStandard(requestDTO, school);
        }catch (ApplicationException ex){
            exception = ex;
        }
    }
    @Then("a ApplicationException should be thrown for school not found")
    public void school_not_found_exception_thrown(){
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("school not found", exception.getMessage());
    }

    @And("there is standard {int} already exist in given school id {int}")
    public void standard_already_exist_in_school(int standard, int schoolId){
        when(standardRepository.findByStandardAndSchoolEntityId(standard, schoolId))
                .thenThrow(
                        new  ApplicationException("standard already exist", HttpStatus.CONFLICT)
                );
    }

    @When("I try to save a standard {int} already exist in given school {int}")
    public void try_to_save_standard_conflict(int standard, int school){
        StandardRequestDTO requestDTO = new StandardRequestDTO(standard);
        try{
            response = standardService.addStandard(requestDTO, school);
        }catch (ApplicationException ex){
            exception = ex;
        }
    }
    @Then("a ApplicationException should be thrown for standard already exist")
    public void standard_already_exist_exception_thrown(){
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("standard already exist", exception.getMessage());
    }

}
