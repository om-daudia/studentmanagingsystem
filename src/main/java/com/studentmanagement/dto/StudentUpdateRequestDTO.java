package com.studentmanagement.dto;

import java.time.LocalDate;
import java.util.Date;

public class StudentUpdateRequestDTO {
    private String studentName;
    private LocalDate dateOfBirth;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
