package com.studentmanagement.dto;

import com.studentmanagement.entity.SubjectMarkEntity;

import java.util.ArrayList;
import java.util.List;

public class StudentRequestDTO {
    private String studentName;
    private List<SubjectMarkEntity> subjectMarkEntityList = new ArrayList<>();

    public String getStudentName() {
        return studentName;
    }

    public List<SubjectMarkEntity> getSubjectMarkEntityList() {
        return subjectMarkEntityList;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setSubjectMarkEntityList(List<SubjectMarkEntity> subjectMarkEntityList) {
        this.subjectMarkEntityList = subjectMarkEntityList;
    }
}
