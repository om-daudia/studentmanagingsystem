package com.students.studmanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "top_student_standard")
public class ScheduleTopThreeStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    int studentId;
    String studentName;
    float percentage;
    int year;

    public ScheduleTopThreeStandard(int studentId, String studentName, float percentage, int year) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.percentage = percentage;
        this.year = year;
    }

    public int getId() {
        return Id;
    }
}
