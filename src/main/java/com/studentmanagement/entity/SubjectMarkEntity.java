package com.studentmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subjectmark")
public class SubjectMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subjectName;
    private int mark;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public SubjectMarkEntity() {
    }

    public SubjectMarkEntity(int id, String subName, int marks) {
        this.id = id;
        this.subjectName = subName;
        this.mark = marks;
    }

    public int getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarks() {
        return mark;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setMarks(int marks) {
        this.mark = marks;
    }
}
