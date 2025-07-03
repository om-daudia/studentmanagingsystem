package com.students.studmanagement.dto;

import com.students.studmanagement.entity.SubjectMarkEntity;

import java.util.ArrayList;
import java.util.List;

public class StudentResponseDTO {
    private int id;
    private String studentName;
    private float obtainMarks;
    private float percentage;
    private String result;
    private List<SubjectMarkEntity> subjectMarkEntityList = new ArrayList<>();

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(int id, String studentName) {
        this.id = id;
        this.studentName = studentName;
    }

    public StudentResponseDTO(int id, String studentName, float obtainMarks, float percentage, String result) {
        this.id = id;
        this.studentName = studentName;
        this.obtainMarks = obtainMarks;
        this.percentage = percentage;
        this.result = result;
    }

    public StudentResponseDTO(int id, String studentName, float obtainMarks, float percentage, String result, List<SubjectMarkEntity> subjectMarkEntityList) {
        this.id = id;
        this.studentName = studentName;
        this.obtainMarks = obtainMarks;
        this.percentage = percentage;
        this.result = result;
        this.subjectMarkEntityList = subjectMarkEntityList;
    }

    public StudentResponseDTO(int id, String studentName, float percentage) {
        this.id = id;
        this.studentName = studentName;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public float getObtainMarks() {
        return obtainMarks;
    }

    public float getPercentage() {
        return percentage;
    }

    public String getResult() {
        return result;
    }

    public List<SubjectMarkEntity> getSubjectMarksEntityList() {
        return subjectMarkEntityList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setObtainMarks(float obtainMarks) {
        this.obtainMarks = obtainMarks;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSubjectMarksEntityList(List<SubjectMarkEntity> subjectMarkEntityList) {
        this.subjectMarkEntityList = subjectMarkEntityList;
    }
}
