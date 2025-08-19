package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.repository.SchoolRepository;
import com.studentmanagement.dto.SchoolRequestDTO;
import com.studentmanagement.dto.SchoolResponseDTO;
import com.studentmanagement.entity.SchoolEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SchoolService {
    @Autowired
    SchoolRepository schoolRepository;

    ModelMapper modelMapper = new ModelMapper();
    public ResponseEntity<Object> getAllSchools(){

        List<SchoolResponseDTO> schoolList = schoolRepository.findAll().stream()
                .map(school -> new SchoolResponseDTO(school.getId(), school.getSchoolName())).collect(Collectors.toList());

        return ResponseHandler.responseEntity(
                schoolList,
                "getting school list",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> addSchool(SchoolRequestDTO schoolRequest){
//        for (StandardEntity standard : school.getStandardEntityList()) {
//            standard.setSchoolEntity(school);
//        }
        if(schoolRequest.getSchoolName() != null && schoolRequest.getSchoolName().isEmpty()){
            throw new ApplicationException("school name is missing", HttpStatus.BAD_REQUEST);
        }
        log.trace("enter in addSchool method");
        log.info("execute addSchool method with schoolName : {}",schoolRequest.getSchoolName());
        try {
            SchoolEntity findSchool = schoolRepository.findBySchoolName(schoolRequest.getSchoolName());
            if(findSchool == null ) {
                if(!schoolRequest.getSchoolName().isEmpty() && schoolRequest.getSchoolName() != null) {
                    SchoolEntity schoolEntity = modelMapper.map(schoolRequest, SchoolEntity.class);
                    schoolRepository.save(schoolEntity);
                    log.info("school add successful and sending successful responseEntity");
                    log.trace("exit from addSchool method");
                    return ResponseHandler.responseEntity(
                            "new school added",
                            "successful",
                            true,
                            HttpStatus.OK
                    );
                }else {
                    throw new ApplicationException("school name is missing", HttpStatus.BAD_REQUEST);
                }
            }else {
                log.info("school already exist and sending unsuccessful responseEntity");
                log.trace("exit from addSchool method");
                throw new ApplicationException("school already exist", HttpStatus.CONFLICT);
            }
        }
        catch (Exception e){
            log.error("Error in addSchool method : {}",e.getMessage(), e);
            throw new ApplicationException("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getSchoolById(int schoolId) {
        log.info("execute getSchoolById with schoolId : {}",schoolId);
        SchoolEntity findSchool = schoolRepository.findById(schoolId).orElseThrow(() -> {
            log.warn("school not found with schoolId: {}", schoolId);
            throw new ApplicationException("School not found with ID: " + schoolId, HttpStatus.NOT_FOUND);
        });
        SchoolResponseDTO school = modelMapper.map(findSchool, SchoolResponseDTO.class);
//        log.debug("school found {}", school.toString());
        log.info("school found with schoolId {} and return with SchoolResponseDTO",school.getId());
        return ResponseHandler.responseEntity(
                school,
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteSchool(int schoolId) {
        log.info("execute deleteSchool method with schoolId {}",schoolId);
        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(() ->{
            log.warn("school not found with schoolId : {}",schoolId);
            return new ApplicationException("school not found",HttpStatus.NOT_FOUND);
        });
        log.debug("schoolEntity schoolId {}",schoolEntity.getId());
        schoolRepository.deleteById(schoolEntity.getId());
        log.info("school deleted successful with schoolId {}",schoolEntity.getId());
        return ResponseHandler.responseEntity(
                "School Deleted",
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> modifySchool(SchoolRequestDTO schooldto, int schoolId) {
        log.info("execute modifySchool method with schoolDTO : {} and schoolId : {}",schooldto, schoolId);

        SchoolEntity schoolEntity = schoolRepository.findById(schoolId).orElseThrow(() ->{
            log.warn("school not found with schoolId : {}",schoolId);
            throw new ApplicationException("school not found",HttpStatus.NOT_FOUND);
        });
        log.debug("old schoolName : {}",schoolEntity.getSchoolName());
        schoolEntity.setSchoolName(schooldto.getSchoolName());
        log.debug("updated schoolName : {}",schoolEntity.getSchoolName());
        schoolRepository.save(schoolEntity);
        log.info("update successful with schoolId : {}",schoolEntity.getId());
        return ResponseHandler.responseEntity(
                modelMapper.map(schoolEntity, SchoolResponseDTO.class),
                "successful",
                true,
                HttpStatus.OK
        );
    }
}
