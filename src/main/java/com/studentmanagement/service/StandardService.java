package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import com.studentmanagement.entity.StandardEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StandardService {
    @Autowired
    StandardRepository standardRepository;
    @Autowired
    SchoolRepository schoolRepository;

    ModelMapper modelMapper = new ModelMapper();
    public ResponseEntity<Object> getAllStandards(){
        List<StandardResponseDTO> standardList = standardRepository.findAll().stream()
                .map(student -> new StandardResponseDTO(student.getId(),student.getStandard())).collect(Collectors.toList());
        return ResponseHandler.responseEntity(
                standardList,
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> addStandard(StandardRequestDTO standardRequest, int schoolId) {

        if(standardRequest.getStandard() <= 0){
            throw new ApplicationException("standard is missing", HttpStatus.BAD_REQUEST);
        }
        SchoolEntity findSchool = schoolRepository.findById(schoolId).orElseThrow(
                () -> new ApplicationException("school not found", HttpStatus.NOT_FOUND));
        StandardEntity standardEntity = standardRepository.findByStandardAndSchoolEntityId(standardRequest.getStandard(), schoolId);
        if (standardEntity != null) {
            throw new ApplicationException("standard already exist", HttpStatus.CONFLICT);
        }
        StandardEntity std = modelMapper.map(standardRequest, StandardEntity.class);
        std.setSchoolEntity(findSchool);
        standardRepository.save(std);
        return ResponseHandler.responseEntity(
                modelMapper.map(std, StandardResponseDTO.class),
                "successful",
                true,
                HttpStatus.OK
        );
    }
    public ResponseEntity<Object> getStandardById(int standardId){
        if(standardId <= 0){
            throw new ApplicationException("standard id missing", HttpStatus.BAD_REQUEST);
        }
        StandardEntity standard = standardRepository.findById(standardId).orElseThrow(
                () -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        StandardResponseDTO std = modelMapper.map(standard, StandardResponseDTO.class);
        return ResponseHandler.responseEntity(
                std,
                "successful",
                true,
                HttpStatus.OK
        );
    }
    public ResponseEntity<Object> deleteStandard(int standardId){
        if(standardId <= 0){
            throw new ApplicationException("standard id missing", HttpStatus.BAD_REQUEST);
        }
        StandardEntity findStandard = standardRepository.findById(standardId).orElseThrow(
                () -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        standardRepository.deleteById(standardId);
        return ResponseHandler.responseEntity(
                modelMapper.map(findStandard, StandardResponseDTO.class),
                "successful",
                true,
                HttpStatus.OK
        );
    }
    public ResponseEntity<Object> modifyStandard(StandardRequestDTO standardRequestDTO, int standardId) {
        if(standardId <= 0 || standardRequestDTO.getStandard() <= 0){
            throw new ApplicationException("invalid input", HttpStatus.BAD_REQUEST);
        }
        StandardEntity standard = standardRepository.findById(standardId).orElseThrow(
                () -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        standard.setStandard(standardRequestDTO.getStandard());
        standardRepository.save(standard);
        return ResponseHandler.responseEntity(
                modelMapper.map(standard, StandardResponseDTO.class),
                "successful",
                true,
                HttpStatus.OK
        );
    }
}
