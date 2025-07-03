package com.students.studmanagement.dto;

public class StandardResponseDTO {
    private int id;
    private int standard;

    public StandardResponseDTO() {
    }

    public StandardResponseDTO(int id, int standard) {
        this.id = id;
        this.standard = standard;
    }

    public int getId() {
        return id;
    }

    public int getStandard() {
        return standard;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
}
