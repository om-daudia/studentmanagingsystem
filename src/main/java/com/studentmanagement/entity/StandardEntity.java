package com.studentmanagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "standard")
public class StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int standard;
    @ManyToOne()
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolEntity;

    @OneToMany(mappedBy = "standardEntity", cascade = CascadeType.ALL)
    private List<DivisionEntity> divisionEntityList;

    public StandardEntity() {
    }
    public StandardEntity(int id, int standard) {
        this.id = id;
        this.standard = standard;
    }
    public StandardEntity(int standard, SchoolEntity schoolEntity) {
        this.standard = standard;
        this.schoolEntity = schoolEntity;
    }
    public StandardEntity(int standard) {
        this.standard = standard;
    }

    public int getId() {
        return id;
    }

    public int getStandard() {
        return standard;
    }

    public SchoolEntity getSchool() {
        return schoolEntity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public void setSchool(SchoolEntity schoolEntity) {
        this.schoolEntity = schoolEntity;
    }


    public void setSchoolEntity(SchoolEntity schoolEntity) {
        this.schoolEntity = schoolEntity;
    }
}

