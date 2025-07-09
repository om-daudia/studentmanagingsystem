package com.students.studmanagement.dto;

public class SchoolResponseDTO {
    private int id;
    private String schoolName;

    public SchoolResponseDTO() {
    }

    public SchoolResponseDTO(int id, String schoolName) {
        this.id = id;
        this.schoolName = schoolName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    @Override
    public String toString() {
        return "SchoolResponseDTO{" +
                "id=" + id +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
