package com.students.studmanagement.dto;

public class SchoolRequestDTO {
    private String schoolName;

    public SchoolRequestDTO(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
