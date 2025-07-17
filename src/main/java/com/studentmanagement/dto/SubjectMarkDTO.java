package com.studentmanagement.dto;
public class SubjectMarkDTO {
    private int id;
    private String subName;
    private int marks;

    public SubjectMarkDTO() {
    }

    public SubjectMarkDTO(int id, String subName, int marks) {
        this.id = id;
        this.subName = subName;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}