package com.students.studmanagement.dto;

public class TopThreeResponse {
    private int id;
    private String studentName;
    private float percentage;

    public TopThreeResponse(int id, String studentName, float percentage) {
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

    public float getPercentage() {
        return percentage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
