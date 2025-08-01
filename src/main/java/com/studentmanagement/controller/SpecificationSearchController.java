package com.studentmanagement.controller;

import com.studentmanagement.dto.SpecificationFilterDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.StudentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SpecificationSearchController {
    @Autowired
    StudentService studentService;
    @GetMapping()
    public List<StudentResponseDTO> search(@RequestParam() String query) {
        return studentService.searchByKeyword(query);
    }
    @PostMapping()
    public List<StudentResponseDTO> search(@RequestBody SpecificationFilterDTO requestDTO) {
        return studentService.searchByFilter(requestDTO);
    }
}
