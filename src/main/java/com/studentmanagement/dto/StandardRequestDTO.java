package com.studentmanagement.dto;

public class StandardRequestDTO {
    private int standard;

    public StandardRequestDTO(int standard) {
        this.standard = standard;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }
}
