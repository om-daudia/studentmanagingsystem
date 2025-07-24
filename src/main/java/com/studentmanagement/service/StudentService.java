package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.common.response.ResponseHandler;
import com.studentmanagement.dto.StudentPageingReportDTO;
import com.studentmanagement.dto.StudentUpdateRequestDTO;
import com.studentmanagement.entity.DivisionEntity;
import com.studentmanagement.entity.StandardEntity;
import com.studentmanagement.entity.StudentEntity;
import com.studentmanagement.entity.SubjectMarkEntity;
import com.studentmanagement.repository.DivisionRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.dto.StudentRequestDTO;
import com.studentmanagement.dto.StudentResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    StandardRepository standardRepository;

    ModelMapper modelMapper = new ModelMapper();
    public ResponseEntity<Object> getAllStudents(){
        List<StudentResponseDTO> list = studentRepository.findAll().stream()
                .map(student -> new StudentResponseDTO(student.getId(),student.getStudentName(),student.getDateOfBirth(),student.getObtainMarks(),student.getPercentage(),student.getResult())).collect(Collectors.toList());
        return ResponseHandler.responseEntity(
                list,
                "all students list",
                true,
                HttpStatus.OK
        );
    }


    public ResponseEntity<Object> addStudent(StudentRequestDTO studentRequest, int divisionId){
        try {
            DivisionEntity division = divisionRepository.findById(divisionId).orElseThrow(() -> new ApplicationException("division not found", HttpStatus.NOT_FOUND));

//                Optional<StudentEntity> findstudent = studentRepository.findByIdAndStudentName(student.getId, divisionId);
//                if(findstudent.isEmpty()) { //student check
            StudentEntity studentEntity = modelMapper.map(studentRequest, StudentEntity.class);
            for (SubjectMarkEntity sm : studentEntity.getSubjectMarksEntityList()) {
                sm.setStudentEntity(studentEntity);
            }
            studentEntity.setDivisionEntity(division);
            studentRepository.save(studentEntity);
            calculateMarks(studentEntity.getId());
            return ResponseHandler.responseEntity(
                    studentEntity,
                    "successful",
                    true,
                    HttpStatus.OK
            );
//                }
//                else {
//                    return "Student Exist";
//                }

        } catch (Exception e) {
            return ResponseHandler.responseEntity(
                    e.getMessage(),
                    "unknown error",
                    false,
                    HttpStatus.OK
            );
        }
    }

    public ResponseEntity<Object> deleteStudent(int studentId){
        StudentEntity findStudent = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));
        studentRepository.deleteById(studentId);
        return ResponseHandler.responseEntity(
                "student deleted",
                "successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> calculateMarks(int studentId) {
        try {
            StudentEntity student = studentRepository.findById(studentId).orElseThrow(()->new ApplicationException("student not found", HttpStatus.NOT_FOUND));
            List<SubjectMarkEntity> markList = student.getSubjectMarksEntityList();
            float obtainMark=0;
            float percentage=0;
            int subjectNumber = 0;
            String result = "Fail";

            for(SubjectMarkEntity subjectMark : markList){
                if(subjectMark.getMarks() > 28) {
                    obtainMark += subjectMark.getMarks();
                    subjectNumber++;
                }else {
                    obtainMark = 0;
                    break;
                }
            }
            if(obtainMark > 0) {
                percentage = obtainMark / subjectNumber;
                result = "Pass";
            }
            student.setObtainMarks(obtainMark);
            student.setPercentage(percentage);
            student.setResult(result);
            student.setStudentName(student.getStudentName());
            studentRepository.save(student);
            return ResponseHandler.responseEntity(
                    "marks added",
                    "successful",
                    true,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("something wrong");
        }
    }

    public ResponseEntity<Object> modifyStudent(StudentUpdateRequestDTO studentUpdateRequestDTO, int studentId) {
        StudentEntity updateStudent = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));

        updateStudent.setStudentName(studentUpdateRequestDTO.getStudentName());
        updateStudent.setDateOfBirth(studentUpdateRequestDTO.getDateOfBirth());
        studentRepository.save(updateStudent);
        return ResponseHandler.responseEntity(
                studentUpdateRequestDTO,
                "modify successful",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> getStudentById(int studentId) {
        StudentEntity student = studentRepository.findById(studentId).orElseThrow(() -> new ApplicationException("student not found", HttpStatus.NOT_FOUND));
        StudentResponseDTO studentResponseDTO = null;
        try {
            studentResponseDTO = modelMapper.map(student, StudentResponseDTO.class);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ResponseHandler.responseEntity(
                studentResponseDTO,
                "modify successfully",
                true,
                HttpStatus.OK
        );
    }

    public ResponseEntity<StudentPageingReportDTO> getStudents(int pageNumber, int pageSize, LocalDate fromDate, LocalDate toDate, int std, String sortBy, String direction) {

        Sort sort = direction.equals("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        if (std > 0 && fromDate == null && toDate == null) {
            StudentEntity studentEntity = studentRepository.findById(std).orElseThrow(()->new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
            return getStudentsByStandardId(std, pageNumber, pageSize, sort);
        }
        if (fromDate == null) {
            throw new ApplicationException("from date required", HttpStatus.BAD_REQUEST);
        }
        if (fromDate == null) {
            throw new ApplicationException("to date required", HttpStatus.BAD_REQUEST);
        }
        if (std == 0 && fromDate != null && toDate != null) {
            if (toDate.isAfter(fromDate)) {
                List<StudentEntity> studentEntityList = studentRepository.findByDateOfBirthBetween(fromDate,toDate);
                if (!studentEntityList.isEmpty()) {
                    return getStudentsByDateOfBirth(fromDate, toDate, pageNumber, pageSize, sort);
                }
            } else {
                throw new ApplicationException("invalid date", HttpStatus.BAD_REQUEST);
            }
        }
        if (std == 0) {
            throw new ApplicationException("standard not valid", HttpStatus.BAD_REQUEST);
        }
        if (fromDate != null && toDate != null && std > 0) {
            StandardEntity standardEntity = standardRepository.findById(std).orElseThrow(() -> new ApplicationException("standard not found", HttpStatus.NOT_FOUND));
            if (toDate.isAfter(fromDate)) {
                List<StudentEntity> studentList = studentRepository.findByDateOfBirthBetweenAndDivisionEntityStandardEntityId(fromDate, toDate, std);
                if (!studentList.isEmpty()) {
                    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
                    Page<StudentEntity> studentEntityPage = studentRepository
                            .findByDateOfBirthBetweenAndDivisionEntityStandardEntityId(fromDate, toDate, std, pageable);
                    Page<StudentResponseDTO> dtoPage = convertToPageList(studentEntityPage, pageable);
                    StudentPageingReportDTO reportDTO = getReport(dtoPage);
                    return new ResponseEntity<>(reportDTO, HttpStatus.OK);
                } else {
                    throw new ApplicationException("students not found", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new ApplicationException("invalid date", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ApplicationException("fill the request", HttpStatus.BAD_REQUEST);
        }
    }

//    public Page<StudentResponseDTO> convertToPage(List<StudentResponseDTO> studentList, Pageable pageable) {
//        int total = studentList.size();
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), total);
//
//        List<StudentResponseDTO> paginatedList = studentList.subList(start, end);
//
//        return new PageImpl<>(paginatedList, pageable, total);
//    }

    private StudentPageingReportDTO getReport(Page<StudentResponseDTO> dtoPage){
        StudentPageingReportDTO reportDTO = new StudentPageingReportDTO();
        reportDTO.setStudentList(dtoPage.getContent());
        reportDTO.setPageSize(dtoPage.getSize());
        reportDTO.setPageNumber(dtoPage.getNumber());
        reportDTO.setTotalRecords(dtoPage.getTotalElements());
        reportDTO.setTotalPages(dtoPage.getTotalPages());
        return reportDTO;
    }

    public ResponseEntity<StudentPageingReportDTO> getStudentsByStandardId(int stdId, int pageNumber, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<StudentEntity> studentEntityPage = studentRepository.findByDivisionEntityStandardEntityId(stdId, pageable);
        Page<StudentResponseDTO> dtoPage = convertToPageList(studentEntityPage, pageable);
        StudentPageingReportDTO reportDTO = getReport(dtoPage);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);

    }

    public ResponseEntity<StudentPageingReportDTO> getStudentsByDateOfBirth(LocalDate fromDate, LocalDate toDate, int pageNumber, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<StudentEntity> studentEntityPage = studentRepository.findByDateOfBirthBetween(fromDate, toDate, pageable);
        Page<StudentResponseDTO> dtoPage = convertToPageList(studentEntityPage, pageable);
        StudentPageingReportDTO reportDTO = getReport(dtoPage);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

    private Page<StudentResponseDTO> convertToPageList(Page<StudentEntity> entityPageList, Pageable pageable){
        List<StudentResponseDTO> studentDTOList = entityPageList.getContent().stream()
                .map(student -> modelMapper.map(student, StudentResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(studentDTOList, pageable, entityPageList.getTotalElements());
    }
}

