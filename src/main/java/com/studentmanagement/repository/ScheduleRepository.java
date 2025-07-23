package com.studentmanagement.repository;

import com.studentmanagement.entity.ScheduleTopThreeStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleTopThreeStandard, Integer> {
}
