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
    public List<StandardResponseDTO> getAllStandards(){
        return standardRepository.findAll().stream()
                .map(student -> new StandardResponseDTO(student.getId(),student.getStandard())).collect(Collectors.toList());
    }

    public ResponseEntity<Object> addStandard(StandardRequestDTO standardRequest, int schoolId) {
        try {
            SchoolEntity findSchool = schoolRepository.findById(schoolId).orElseThrow(() -> new ApplicationException("school not found", HttpStatus.NOT_FOUND));

            StandardEntity standardEntity = standardRepository.findByStandardAndSchoolEntityId(standardRequest.getStandard(), schoolId);
            if (standardEntity == null) {
                StandardEntity std = modelMapper.map(standardRequest, StandardEntity.class);
                std.setSchoolEntity(findSchool);
                standardRepository.save(std);
                return ResponseHandler.responseEntity(
                        "new standard added",
                        "successful",
                        true,
                        HttpStatus.OK
                );
            } else {
                return ResponseHandler.responseEntity(
                        "standard already exist",
                        "unsuccessful",
                        false,
                        HttpStatus.OK
                );
            }

        } catch (Exception e) {
            return ResponseHandler.responseEntity(
                    e.getMessage(),
                    "unsuccessful",
                    false,
                    HttpStatus.OK
            );
        }
    }

    public ResponseEntity<Object> deleteStandard(int standardId){
        StandardEntity findStandard = standardRepository.findById(standardId).orElseThrow(() -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        standardRepository.deleteById(standardId);
        return ResponseHandler.responseEntity(
                findStandard,
                "delete successfully",
                true,
                HttpStatus.OK
        );

    }

    public ResponseEntity<Object> getStandardById(int standardId){
        StandardEntity standard = standardRepository.findById(standardId).orElseThrow(() -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        StandardResponseDTO std = modelMapper.map(standard, StandardResponseDTO.class);
        return ResponseHandler.responseEntity(
                std,
                "standard found",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> modifyStandard(StandardResponseDTO standardResponseDTO, int standardId, int schoolId) {
        StandardEntity standard = standardRepository.findById(standardId).orElseThrow(() -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
        standard.setStandard(standardResponseDTO.getStandard());
        standardRepository.save(standard);
        return ResponseHandler.responseEntity(
                modelMapper.map(standard, StandardResponseDTO.class),
                "standard found",
                true,
                HttpStatus.OK
        );
    }
}
