package com.studentmanagement.dto;

import com.studentmanagement.entity.SubjectMarkEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentResponseDTO {
    private int id;
    private String studentName;
    private Date dateOfBirth;
    private float obtainMarks;
    private float percentage;
    private String result;
    private List<SubjectMarkDTO> subjectMarkEntityList = new ArrayList<>();

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(int id, String studentName) {
        this.id = id;
        this.studentName = studentName;
    }

    public StudentResponseDTO(int id, String studentName, Date dateOfBirth) {
        this.id = id;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
    }

    public StudentResponseDTO(int id, String studentName, float obtainMarks, float percentage, String result) {
        this.id = id;
        this.studentName = studentName;
        this.obtainMarks = obtainMarks;
        this.percentage = percentage;
        this.result = result;
    }

    public StudentResponseDTO(int id, String studentName, float obtainMarks, float percentage, String result, List<SubjectMarkDTO> subjectMarkEntityList) {
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

    public StudentResponseDTO(int id, String studentName, Date dateOfBirth, float obtainMarks, float percentage, String result) {
        this.id = id;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.obtainMarks = obtainMarks;
        this.percentage = percentage;
        this.result = result;
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

    public List<SubjectMarkDTO> getSubjectMarksEntityList() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSubjectMarksEntityList(List<SubjectMarkDTO> subjectMarkEntityList) {
        this.subjectMarkEntityList = subjectMarkEntityList;
    }
}
