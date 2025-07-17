package com.studentmanagement.controller;

import com.studentmanagement.dto.DivisionRequestDTO;
import com.studentmanagement.dto.DivisionResponseDTO;
import com.studentmanagement.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("standards/{standardId}/divisions")
public class DivisionController {
    @Autowired
    DivisionService divisionService;
    @GetMapping()
    public ResponseEntity<Object> getAllDivisions(){
        return divisionService.getAllDivisions();
    }

    @PostMapping()
    public ResponseEntity<Object> addDivision(@RequestBody DivisionRequestDTO divisionRequest, @PathVariable int standardId){
        return divisionService.addDivision(divisionRequest, standardId);
    }

    @GetMapping("/{divisionId}")
    public ResponseEntity<Object> getDivisionById(@PathVariable int divisionId){
        return divisionService.getDivisionById(divisionId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{divisionId}")
    public ResponseEntity<Object> deleteDivision(@PathVariable int divisionId){
        return divisionService.deleteDivisionById(divisionId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{divisionId}")
    public ResponseEntity<Object> modifyDivision(@RequestBody DivisionResponseDTO divisionResponseDTO, @PathVariable int divisionId){
        return divisionService.modifyDivision(divisionResponseDTO, divisionId);
    }

}
