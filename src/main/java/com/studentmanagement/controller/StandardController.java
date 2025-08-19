package com.studentmanagement.controller;

import com.studentmanagement.dto.StandardRequestDTO;
import com.studentmanagement.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools/{schoolId}/standards")
public class StandardController {
    @Autowired
    StandardService standardService;

    @GetMapping()
    public ResponseEntity<Object> getAllStandards(){
        return standardService.getAllStandards();
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<Object> modifyStandard(@RequestBody StandardRequestDTO standardRequestDTO, @PathVariable int standardId){
        return standardService.modifyStandard(standardRequestDTO,standardId);
    }
}
