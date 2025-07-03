package com.students.studmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subject_marks")
public class SubjectMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subjectName;
    private int marks;

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
        this.marks = marks;
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
        return marks;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
