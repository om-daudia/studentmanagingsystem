package com.studentmanagement.dto;

import com.studentmanagement.entity.StudentEntity;

import java.util.List;

public class DivisionRequestDTO {
    private char division;
    private List<StudentEntity> studentEntityList;

    public DivisionRequestDTO(char division, List<StudentEntity> studentEntityList) {
        this.division = division;
        this.studentEntityList = studentEntityList;
    }

    public char getDivision() {
        return division;
    }

    public List<StudentEntity> getStudentEntityList() {
        return studentEntityList;
    }

    public void setDivision(char division) {
        this.division = division;
    }

    public void setStudentEntityList(List<StudentEntity> studentEntityList) {
        this.studentEntityList = studentEntityList;
    }
}
