package com.studentmanagement.controller;

import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.dto.StandardResponseDTO;
import com.studentmanagement.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{standardId}")
    public ResponseEntity<Object> deleteStandard(@PathVariable int standardId){
        return standardService.deleteStandard(standardId);
    }

    @GetMapping("/{standardId}")
    public ResponseEntity<Object> getStandardById(@PathVariable int standardId){
        return standardService.getStandardById(standardId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{standardId}")
    public ResponseEntity<Object> modifyStandard(@RequestBody StandardResponseDTO standardResponseDTO, @PathVariable int standardId, @PathVariable int schoolId){
        return standardService.modifyStandard(standardResponseDTO,standardId, schoolId);
    }
}
