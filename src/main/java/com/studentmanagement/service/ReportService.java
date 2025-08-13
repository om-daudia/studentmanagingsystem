package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.repository.DivisionRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.dto.DivisionResponseDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import com.studentmanagement.dto.ReportDTO;
import com.studentmanagement.dto.TopThreeResponse;
import com.studentmanagement.dto.TopThreeStdWise;
import com.studentmanagement.entity.DivisionEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.common.enums.ReportTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StandardRepository standardRepository;
    @Autowired
    DivisionService divisionService;
    @Autowired
    StudentService studentService;
    @Autowired
    StandardService standardService;
    @Autowired
    DivisionRepository divisionRepository;
    public List<TopThreeStdWise> getTopThreeStudentStandardWise(int standardId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);

        if(findStandard.isPresent()){
            List<DivisionResponseDTO> studentList = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());

            Stream<TopThreeStdWise> topThreeStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Pass"))
                    .map(pStud -> new TopThreeStdWise(pStud.getId(), pStud.getStudentName(), pStud.getPercentage()))
                    .sorted(Comparator.comparing(TopThreeStdWise::getPercentage).reversed())
                    .collect(Collectors.toList()).stream().limit(3);
            return topThreeStudent.toList();
        }
        else
        {
            return null;
        }

    }

    public List<TopThreeStdWise> getTopThreeStudentDivisionWise(int standardId, int divisionId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);
        Optional<DivisionEntity> findDivision = divisionRepository.findById(divisionId);

        if(findStandard.isPresent() && findDivision.isPresent()){
            List<DivisionResponseDTO> studentList = divisionRepository.findByIdAndStandardEntity_Id(divisionId, standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());

            Stream<TopThreeStdWise> topThreeStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Pass"))
                    .map(pStud -> new TopThreeStdWise(pStud.getId(), pStud.getStudentName(), pStud.getPercentage()))
                    .sorted(Comparator.comparing(TopThreeStdWise::getPercentage).reversed())
                    .collect(Collectors.toList()).stream().limit(3);
            return topThreeStudent.toList();
        }
        else
        {

            return null;
        }
    }

    public float getAveragePassingStandardWise(int standardId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);

        if(findStandard.isPresent()) {
            List<DivisionResponseDTO> studentList = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());
            long studcount = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();


            List<StudentResponseDTO> passedStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Pass"))
                    .map(stud -> new StudentResponseDTO(stud.getId(),stud.getStudentName())).collect(Collectors.toList());

            float avg = ((float) passedStudent.size() / (float) studcount) * (float) 100;
            return (float) avg;

        }
        else {
            return 0;
        }
    }

    public float getAveragePassingDivisionWise(int standardId, int divisionId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);
        Optional<DivisionEntity> findDivision = divisionRepository.findById(divisionId);

        if(findStandard.isPresent() && findDivision.isPresent()) {
            List<DivisionResponseDTO> studentList = divisionRepository.findByIdAndStandardEntity_Id(divisionId,standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());
            long studcount = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();


            List<StudentResponseDTO> passedStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Pass"))
                    .map(stud -> new StudentResponseDTO(stud.getId(),stud.getStudentName())).collect(Collectors.toList());

            float avg = ((float) passedStudent.size() / (float) studcount) * (float) 100;
            return (float) avg;

        }
        else {
            return 0;
        }
    }

    public float getAverageFailStandardWise(int standardId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);

        if(findStandard.isPresent()) {
            List<DivisionResponseDTO> studentList = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());
            long studcount = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();


            List<StudentResponseDTO> failStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Fail"))
                    .map(stud -> new StudentResponseDTO(stud.getId(),stud.getStudentName())).collect(Collectors.toList());

            float avg = ((float) failStudent.size() / (float) studcount) * (float) 100;
            return (float) avg;

        }
        else {
            return 0;
        }
    }

    public float getAverageFailDivisionWise(int standardId, int divisionId) {
        Optional<StandardEntity> findStandard = standardRepository.findById(standardId);
        Optional<DivisionEntity> findDivision = divisionRepository.findById(divisionId);

        if(findStandard.isPresent() && findDivision.isPresent()) {
            List<DivisionResponseDTO> studentList = divisionRepository.findByIdAndStandardEntity_Id(divisionId,standardId)
                    .stream()
                    .map(div -> new DivisionResponseDTO(div.getStudentEntityList())).collect(Collectors.toList());
            long studcount = divisionRepository.findByStandardEntity_Id(standardId)
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();


            List<StudentResponseDTO> failStudent = studentList.stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .filter(stud -> stud.getResult().equals("Fail"))
                    .map(stud -> new StudentResponseDTO(stud.getId(),stud.getStudentName())).collect(Collectors.toList());

            float avg = ((float) failStudent.size() / (float) studcount) * (float) 100;
            return (float) avg;

        }
        else {
            return 0;
        }
    }



    //using postmapping

    public ResponseEntity<Object> getRepost(ReportDTO request){
        if (request.getSearchStatus().equals(ReportTypeEnum.TopThreeStandard)) {
            return getTopThreeStandardWise(request);
        } else if (request.getSearchStatus().equals(ReportTypeEnum.TopThreeDivision)) {
            return getTopThreeDivisionWise(request);
        } else if (request.getSearchStatus().equals(ReportTypeEnum.AvgPassStudentStandard)) {
            return  getAvgPassStatandard(request);
        } else if (request.getSearchStatus().equals(ReportTypeEnum.AvgPassStudentDivision)) {
            return getAvgPassDivision(request);
        }else if (request.getSearchStatus().equals(ReportTypeEnum.AvgFailStudentStandard)) {
            return getAvgFailStatandard(request);
        }else if (request.getSearchStatus().equals(ReportTypeEnum.AvgFailStudentDivision)) {
            return getAvgFailDivision(request);
        }
        else {
            return ResponseHandler.responseEntity(
                    "Unknown Search",
                    "Please Search Valid Report",
                    false,
                    HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> getTopThreeStandardWise(ReportDTO request) {

        try {
            List<TopThreeResponse> topThreeStudent = divisionRepository.findByStandardEntityIdAndStudentEntityListResult(request.getStandardId(),"Pass")
            .stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
            .sorted(Comparator.comparing(StudentEntity::getPercentage).reversed()).limit(3)
            .map(stud -> new TopThreeResponse(stud.getId(),stud.getStudentName(),stud.getPercentage())).collect(Collectors.toList());

            if(topThreeStudent.isEmpty()){
                throw  new ApplicationException("student not found", HttpStatus.NOT_FOUND);
//                return ResponseHandler.responseEntity(
//                        "Student Not Found",
//                        "Data Not Found",
//                        false,
//                        HttpStatus.NOT_FOUND
//                );
            }else {
                return ResponseHandler.responseEntity(
                        topThreeStudent,
                        "Top Three Student Standard Wise Are Available",
                        true,
                        HttpStatus.OK
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }
    }
    public ResponseEntity<Object> getTopThreeDivisionWise(ReportDTO request) {

        try {
//            List<TopThreeResponse> topThreeStudent = divisionRepository.findByIdAndStandardEntityIdAndStudentEntityListResult(request.getDivisionId(),request.getStandardId(),"Pass")
//                    .stream()
//                    .flatMap(div -> div.getStudentEntityList().stream())
//                    .sorted(Comparator.comparing(StudentEntity::getPercentage).reversed()).limit(3)
//                    .map(stud -> new TopThreeResponse(stud.getId(),stud.getStudentName(),stud.getPercentage())).collect(Collectors.toList());
//
//            if(topThreeStudent.isEmpty()){
//                return ResponseHandler.responseEntity(
//                        "Student Not Found",
//                        "Data Not Found",
//                        HttpStatus.OK
//                );
//            }else {
//                return ResponseHandler.responseEntity(
//                        topThreeStudent,
//                        "Top Three Student Division Wise Are Available",
//                        HttpStatus.OK
//                );
//            }
            List<TopThreeResponse> topThreeStudent = studentRepository.findTop3ByDivisionEntityIdAndResultOrderByPercentageDesc(request.getDivisionId(), "Pass").stream()
                    .map(stud -> new TopThreeResponse(stud.getId(),stud.getStudentName(), stud.getPercentage())).collect(Collectors.toList());
            if(topThreeStudent.isEmpty()){
                throw  new ApplicationException("student not found",HttpStatus.NOT_FOUND);
            }
            return ResponseHandler.responseEntity(
                        topThreeStudent,
                        "Top Three Student Division Wise Are Available",
                        true,
                        HttpStatus.OK
                );
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }
    }

    public ResponseEntity<Object> getAvgPassStatandard(ReportDTO request){
        try {
            long passedStudent = divisionRepository.countByStandardEntityIdAndStudentEntityListResult(request.getStandardId(), "Pass");
            long allStudent = divisionRepository.findByStandardEntity_Id(request.getStandardId())
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();

            return ResponseHandler.responseEntity(
                    ((float) passedStudent / (float) allStudent) * 100,
                    "Average Passed Student Standard Wise Are Available",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }

    }

    public ResponseEntity<Object> getAvgPassDivision(ReportDTO request) {
        try {
            long passedStudent = divisionRepository.countByIdAndStandardEntityIdAndStudentEntityListResult(request.getDivisionId(), request.getStandardId(), "Pass");
            long allStudent = divisionRepository.findByStandardEntity_Id(request.getStandardId())
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();

            return ResponseHandler.responseEntity(
                    ((float) passedStudent / (float) allStudent) * 100,
                    "Average Passed Student Division Wise Are Available",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }
    }

    public ResponseEntity<Object> getAvgFailStatandard(ReportDTO request){
        try {
            long passedStudent = divisionRepository.countByStandardEntityIdAndStudentEntityListResult(request.getStandardId(), "Fail");
            long allStudent = divisionRepository.findByStandardEntity_Id(request.getStandardId())
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();

            return ResponseHandler.responseEntity(
                    ((float) passedStudent / (float) allStudent) * 100,
                    "Average Failed Student Standard Wise Are Available",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }
    }

    public ResponseEntity<Object> getAvgFailDivision(ReportDTO request) {
        try {
            long passedStudent = divisionRepository.countByIdAndStandardEntityIdAndStudentEntityListResult(request.getDivisionId(), request.getStandardId(), "Fail");
            long allStudent = divisionRepository.findByStandardEntity_Id(request.getStandardId())
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream()).count();

            return ResponseHandler.responseEntity(
                    ((float) passedStudent / (float) allStudent) * 100,
                    "Average Failed Student Division Wise Are Available",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }
    }

}
