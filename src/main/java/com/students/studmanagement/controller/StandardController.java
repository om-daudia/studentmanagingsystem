package com.students.studmanagement.controller;

import com.students.studmanagement.dto.StandardRequestDTO;
import com.students.studmanagement.dto.StandardResponseDTO;
import com.students.studmanagement.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools/{schoolId}/standards")
public class StandardController {
    @Autowired
    StandardService standardService;

    @GetMapping()
    public List<StandardResponseDTO> getAllStandards(){
        return standardService.getAllStandards();
    }

    @PostMapping()
    public ResponseEntity<Object> addStandard(@RequestBody StandardRequestDTO standardRequest, @PathVariable int schoolId){
        return standardService.addStandard(standardRequest,schoolId);
    }

    @DeleteMapping("/{standardId}")
    public ResponseEntity<Object> deleteStandard(@PathVariable int standardId){
        return standardService.deleteStandard(standardId);
    }

    @GetMapping("/{standardId}")
    public ResponseEntity<Object> getStandardById(@PathVariable int standardId){
        return standardService.getStandardById(standardId);
    }

    @PatchMapping("/{standardId}")
    public ResponseEntity<Object> modifyStandard(@RequestBody StandardResponseDTO standardResponseDTO, @PathVariable int standardId, @PathVariable int schoolId){
        return standardService.modifyStandard(standardResponseDTO,standardId, schoolId);
    }
}
