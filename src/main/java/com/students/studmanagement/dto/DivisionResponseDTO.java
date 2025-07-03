package com.students.studmanagement.dto;

import com.students.studmanagement.entity.StudentEntity;

import java.util.List;

public class DivisionResponseDTO {
    private int id;
    private char division;
    private List<StudentEntity> studentEntityList;

    public List<StudentEntity> getStudentEntityList() {
        return studentEntityList;
    }

    public DivisionResponseDTO(List<StudentEntity> studentEntityList) {
        this.studentEntityList = studentEntityList;
    }

    public void setStudentEntityList(List<StudentEntity> studentEntityList) {
        this.studentEntityList = studentEntityList;
    }

    public DivisionResponseDTO() {
    }

    public DivisionResponseDTO(int id, char division) {
        this.id = id;
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public char getDivision() {
        return division;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDivision(char division) {
        this.division = division;
    }
}
