package com.studentmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "school")
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String schoolName;

    public SchoolEntity(int id, String schoolName) {
        this.id = id;
        this.schoolName = schoolName;
    }
    public SchoolEntity(String schoolName) {
        this.schoolName = schoolName;
    }

    //    @OneToMany(mappedBy = "schoolEntity", cascade = CascadeType.ALL)
//    private List<StandardEntity> standardsEntity;
    public SchoolEntity() {
    }
    public int getId() {
        return id;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

}
