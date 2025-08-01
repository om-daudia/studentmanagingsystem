package com.studentmanagement.dto;

import java.time.LocalDate;
import java.util.List;

public class SpecificationFilterDTO {
    private String studentName;
    private float fromPercentage;
    private float toPercentage;
    private String result;
    private List<Integer> divisionList;
    private List<Integer> standardList;
    private LocalDate fromDOB;
    private LocalDate toDOB;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getFromPercentage() {
        return fromPercentage;
    }

    public void setFromPercentage(float fromPercentage) {
        this.fromPercentage = fromPercentage;
    }

    public float getToPercentage() {
        return toPercentage;
    }

    public void setToPercentage(float toPercentage) {
        this.toPercentage = toPercentage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Integer> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Integer> divisionList) {
        this.divisionList = divisionList;
    }

    public LocalDate getFromDOB() {
        return fromDOB;
    }

    public void setFromDOB(LocalDate fromDOB) {
        this.fromDOB = fromDOB;
    }

    public LocalDate getToDOB() {
        return toDOB;
    }

    public void setToDOB(LocalDate toDOB) {
        this.toDOB = toDOB;
    }

    public List<Integer> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Integer> standardList) {
        this.standardList = standardList;
    }
}
