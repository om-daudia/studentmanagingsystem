package com.studentmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "division")
public class DivisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private char division;
    @ManyToOne
    @JoinColumn(name = "standard_id")
    private StandardEntity standardEntity;


    @OneToMany(mappedBy = "divisionEntity", cascade = CascadeType.ALL)
    private List<StudentEntity> studentEntityList;

    public List<StudentEntity> getStudentEntityList() {
        return studentEntityList;
    }
    public DivisionEntity() {
    }

    public DivisionEntity(int id, char division) {
        this.id = id;
        this.division = division;
    }

    public DivisionEntity(char division, StandardEntity standardEntity) {
        this.division = division;
        this.standardEntity = standardEntity;
    }

    public int getId() {
        return id;
    }

    public char getDivision() {
        return division;
    }

    public StandardEntity getStandardEntity() {
        return standardEntity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDivision(char division) {
        this.division = division;
    }

     public void setStandardEntity(StandardEntity standardEntity) {
        this.standardEntity = standardEntity;
    }
}

