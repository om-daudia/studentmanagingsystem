package com.students.studmanagement.repository;

import com.students.studmanagement.entity.ScheduleTopThreeStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleTopThreeStandard, Integer> {
}
