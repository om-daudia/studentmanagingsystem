package com.students.studmanagement.service;

import com.students.studmanagement.dto.DivisionRequestDTO;
import com.students.studmanagement.dto.DivisionResponseDTO;
import com.students.studmanagement.entity.DivisionEntity;
import com.students.studmanagement.entity.StandardEntity;
import com.students.studmanagement.repository.DivisionRepository;
import com.students.studmanagement.repository.SchoolRepository;
import com.students.studmanagement.repository.StandardRepository;
import com.students.studmanagement.response.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
            StandardEntity standard = standardRepository.findById(standardId).orElseThrow(() -> new RuntimeException("standard not found"));

            DivisionEntity findDivision = divisionRepository.findByDivisionAndStandardEntity_Id(divisionRequest.getDivision(), standardId);
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
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new RuntimeException("division not found"));
        return ResponseHandler.responseEntity(
                modelMapper.map(division, DivisionResponseDTO.class),
                "successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteDivisionById(int divisionId){
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new RuntimeException("division not found"));

        divisionRepository.deleteById(divisionId);
        return ResponseHandler.responseEntity(
                "devision deleted",
                "successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> modifyDivision(DivisionResponseDTO divisionResponseDTO, int divisionId) {
        DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new RuntimeException("division not found"));
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
