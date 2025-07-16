package com.students.studmanagement.service;

import com.students.studmanagement.dto.DivisionResponseDTO;
import com.students.studmanagement.dto.StudentResponseDTO;
import com.students.studmanagement.dto.SearchDTO;
import com.students.studmanagement.dto.TopThreeResponse;
import com.students.studmanagement.dto.TopThreeStdWise;
import com.students.studmanagement.entity.DivisionEntity;
import com.students.studmanagement.entity.StandardEntity;
import com.students.studmanagement.entity.StudentEntity;
import com.students.studmanagement.enums.SearchStatus;
import com.students.studmanagement.exeptionhandling.ApplicationException;
import com.students.studmanagement.repository.DivisionRepository;
import com.students.studmanagement.repository.StandardRepository;
import com.students.studmanagement.repository.StudentRepository;
import com.students.studmanagement.response.ResponseHandler;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
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

    public ResponseEntity<Object> getRepost(SearchDTO request){
        if (request.getSearchStatus().equals(SearchStatus.TopThreeStandard)) {
            return getTopThreeStandardWise(request);
        } else if (request.getSearchStatus().equals(SearchStatus.TopThreeDivision)) {
            return getTopThreeDivisionWise(request);
        } else if (request.getSearchStatus().equals(SearchStatus.AvgPassStudentStandard)) {
            return  getAvgPassStatandard(request);
        } else if (request.getSearchStatus().equals(SearchStatus.AvgPassStudentDivision)) {
            return getAvgPassDivision(request);
        }else if (request.getSearchStatus().equals(SearchStatus.AvgFailStudentStandard)) {
            return getAvgFailStatandard(request);
        }else if (request.getSearchStatus().equals(SearchStatus.AvgFailStudentDivision)) {
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

    public ResponseEntity<Object> getTopThreeStandardWise(SearchDTO request) {

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
            throw new RuntimeException("somthing wron");
        }
    }
    public ResponseEntity<Object> getTopThreeDivisionWise(SearchDTO request) {

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
            throw new RuntimeException("somthing wron");
        }
    }

    public ResponseEntity<Object> getAvgPassStatandard(SearchDTO request){
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
            throw new RuntimeException("somthing wron");
        }

    }

    public ResponseEntity<Object> getAvgPassDivision(SearchDTO request) {
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
            throw new RuntimeException("somthing wron");
        }
    }

    public ResponseEntity<Object> getAvgFailStatandard(SearchDTO request){
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
            throw new RuntimeException("somthing wron");
        }
    }

    public ResponseEntity<Object> getAvgFailDivision(SearchDTO request) {
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
            throw new RuntimeException("somthing wron");
        }
    }

}
