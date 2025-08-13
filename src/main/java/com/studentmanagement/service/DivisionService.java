package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.repository.DivisionRepository;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.dto.DivisionRequestDTO;
import com.studentmanagement.dto.DivisionResponseDTO;
import com.studentmanagement.entity.DivisionEntity;
import com.studentmanagement.entity.StandardEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DivisionService {
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    StandardService standardService;
    @Autowired
    StandardRepository standardRepository;
    @Autowired
    SchoolRepository schoolRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<Object> getAllDivisions(){
        List<DivisionResponseDTO> list = divisionRepository.findAll().stream()
                .map(div -> new DivisionResponseDTO(div.getId(), div.getDivision())).collect(Collectors.toList());

        return ResponseHandler.responseEntity(
                list,
                "all diviision list",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> addDivision(DivisionRequestDTO divisionRequest, int standardId){
        try {
            StandardEntity standard = standardRepository.findById(standardId).orElseThrow(() -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));

            DivisionEntity findDivision = divisionRepository.findByDivisionAndStandardEntityId(divisionRequest.getDivision(), standardId);
            if (findDivision == null) {
                DivisionEntity division = modelMapper.map(divisionRequest, DivisionEntity.class);
                divisionRepository.save(division);
                return ResponseHandler.responseEntity(
                        modelMapper.map(division, DivisionResponseDTO.class),
                        "successful",
                        true,
                        HttpStatus.OK
                );
            } else {
                return ResponseHandler.responseEntity(
                        "division already exist",
                        "unsuccessful",
                        false,
                        HttpStatus.OK
                );
            }

        } catch (Exception e) {
            return ResponseHandler.responseEntity(
                    e.getMessage(),
                    "unknown error",
                    false,
                    HttpStatus.OK
            );
        }
    }

    public ResponseEntity<Object> getDivisionById(int divisionId){
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new ApplicationException("division not found", HttpStatus.NOT_FOUND));
        return ResponseHandler.responseEntity(
                modelMapper.map(division, DivisionResponseDTO.class),
                "successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteDivisionById(int divisionId){
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new ApplicationException("division not found",HttpStatus.NOT_FOUND));

        divisionRepository.deleteById(divisionId);
        return ResponseHandler.responseEntity(
                "devision deleted",
                "successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> modifyDivision(DivisionResponseDTO divisionResponseDTO, int divisionId) {
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new ApplicationException("division not found",HttpStatus.NOT_FOUND));
        division.setDivision(divisionResponseDTO.getDivision());
        divisionRepository.save(division);
        return ResponseHandler.responseEntity(
                division,
                "successfully",
                true,
                HttpStatus.OK
        );
    }
}
