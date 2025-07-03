package com.students.studmanagement.controller;

import com.students.studmanagement.dto.SearchDTO;
import com.students.studmanagement.dto.TopThreeStdWise;
import com.students.studmanagement.enums.SearchStatus;
import com.students.studmanagement.response.ResponseHandler;
import com.students.studmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schools/reports")
public class ReportContoller {
    @Autowired
    ReportService reportService;

    @GetMapping("/standards/{standardId}/top-three-standard")
    public List<TopThreeStdWise> getTopThreeStudentStandardWise(@PathVariable int standardId) {
        return reportService.getTopThreeStudentStandardWise(standardId);
    }

    @GetMapping("/standards/{standardId}/divisions/{divisionId}/top-three-division")
    public List<TopThreeStdWise> getTopThreeStudentDivisiondWise(@PathVariable int standardId, @PathVariable int divisionId) {
        return reportService.getTopThreeStudentDivisionWise(standardId, divisionId);
    }

    @GetMapping("/standards/{standardId}/average-pass-standard")
    public float getAveragePassingStandardWise(@PathVariable int standardId) {
        return reportService.getAveragePassingStandardWise(standardId);
    }

    @GetMapping("/standards/{standardId}/divisions/{divisionId}/average-pass-division")
    public float getAveragePassingDivisionWise(@PathVariable int standardId, @PathVariable int divisionId) {
        return reportService.getAveragePassingDivisionWise(standardId, divisionId);
    }

    @GetMapping("/standards/{standardId}/average-fail-standard")
    public float getAverageFailStandardWise(@PathVariable int standardId) {
        return reportService.getAverageFailStandardWise(standardId);
    }

    @GetMapping("/standards/{standardId}/divisions/{divisionId}/average-fail-division")
    public float getAverageFailDivisionWise(@PathVariable int standardId, @PathVariable int divisionId) {
        return reportService.getAverageFailDivisionWise(standardId, divisionId);
    }

    @PostMapping()
    public ResponseEntity<Object> getReports(@RequestBody SearchDTO request) {
        return reportService.getRepost(request);
    }
}
