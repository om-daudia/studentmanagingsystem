package com.students.studmanagement.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentName;
    private float obtainMarks;
    private float percentage;
    private String result;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private DivisionEntity divisionEntity;

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL)
    private List<SubjectMarkEntity> subjectMarkEntityList = new ArrayList<>();

    public StudentEntity() {
    }

    public StudentEntity(int id, String studentName) {
        this.id = id;
        this.studentName = studentName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }


    public DivisionEntity getDivisionEntity() {
        return divisionEntity;
    }

    public float getObtainMarks() {
        return obtainMarks;
    }

    public float getPercentage() {
        return percentage;
    }

    public String isResult() {
        return result;
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

    public void setDivisionEntity(DivisionEntity divisionEntity) {
        this.divisionEntity = divisionEntity;
    }

    public String getResult() {
        return result;
    }

    public List<SubjectMarkEntity> getSubjectMarksEntityList() {
        return subjectMarkEntityList;
    }
}

