package com.students.studmanagement.dto;

public class TopThreeStdWise {
    private int id;
    private String name;
    private float percentage;

    public TopThreeStdWise(int id, String name, float percentage) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
