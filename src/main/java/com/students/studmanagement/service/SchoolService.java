package com.students.studmanagement.service;

import com.students.studmanagement.dto.SchoolRequestDTO;
import com.students.studmanagement.dto.SchoolResponseDTO;
import com.students.studmanagement.entity.SchoolEntity;
import com.students.studmanagement.exeptionhandling.DataNotFoundException;
import com.students.studmanagement.repository.SchoolRepository;
import com.students.studmanagement.response.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchoolService {
    @Autowired
    SchoolRepository schoolRepository;

    ModelMapper modelMapper = new ModelMapper();
    public ResponseEntity<Object> getAllSchools(){

        List<SchoolResponseDTO> schoolList = schoolRepository.findAll().stream()
                .map(school -> new SchoolResponseDTO(school.getId(), school.getSchoolName())).collect(Collectors.toList());

        return ResponseHandler.responseEntity(
                schoolList,
                "all school List available",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> addSchool(SchoolRequestDTO schoolRequest){
//        for (StandardEntity standard : school.getStandardEntityList()) {
//            standard.setSchoolEntity(school);
//        }
        try {
            SchoolEntity findSchool = schoolRepository.findBySchoolName(schoolRequest.getSchoolName());
            if(findSchool == null) {
                SchoolEntity schoolEntity = modelMapper.map(schoolRequest, SchoolEntity.class);
                schoolRepository.save(schoolEntity);
                return ResponseHandler.responseEntity(
                        "new school added",
                        "successful",
                        true,
                        HttpStatus.OK
                );
            }else {
                return ResponseHandler.responseEntity(
                        "this school already exist",
                        "unsuccessfully",
                        false,
                        HttpStatus.OK
                );
            }
        }
        catch (Exception e){
            return ResponseHandler.responseEntity(
                    e.getMessage(),
                    "unknown error",
                    false,
                    HttpStatus.OK
            );
        }

    }

    public ResponseEntity<Object> getSchoolById(int schoolId) {
        SchoolEntity findSchool = schoolRepository.findById(schoolId).orElseThrow(() -> new DataNotFoundException("school not found"));
        SchoolResponseDTO school = modelMapper.map(findSchool, SchoolResponseDTO.class);
        return ResponseHandler.responseEntity(
                school,
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteSchool(int schoolId) {
        SchoolEntity schoolOpt = schoolRepository.findById(schoolId).orElseThrow(() -> new DataNotFoundException("school not found"));
        schoolRepository.deleteById(schoolId);
        return ResponseHandler.responseEntity(
                "School Deleted",
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> modifySchool(SchoolResponseDTO schooldto, int schoolId) {
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(() -> new DataNotFoundException("school not found"));
        schoolEntity.setSchoolName(schooldto.getSchoolName());
        schoolRepository.save(schoolEntity);
        return ResponseHandler.responseEntity(
                modelMapper.map(schoolEntity, SchoolResponseDTO.class),
                "successful",
                true,
                HttpStatus.OK
        );
    }
}
