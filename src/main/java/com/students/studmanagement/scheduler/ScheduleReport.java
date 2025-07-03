package com.students.studmanagement.scheduler;

import com.students.studmanagement.dto.StandardResponseDTO;
import com.students.studmanagement.dto.TopThreeResponse;
import com.students.studmanagement.entity.ScheduleTopThreeStandard;
import com.students.studmanagement.entity.StandardEntity;
import com.students.studmanagement.entity.StudentEntity;
import com.students.studmanagement.exeptionhandling.DataNotFoundException;
import com.students.studmanagement.repository.DivisionRepository;
import com.students.studmanagement.repository.ScheduleRepository;
import com.students.studmanagement.repository.StandardRepository;
import com.students.studmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class ScheduleReport {
    @Autowired
    StandardRepository standardRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Scheduled(cron = "0 0 0 1 1 ?")
    @Transactional
//    @Scheduled(cron = "*/5 * * * * *")
    public void storeTopThree(){
        int schoolId = 1;
        List<Integer> standardList =  standardRepository.findBySchoolEntityId(schoolId)
                .stream()
                .map(standard -> standard.getId()).collect(Collectors.toList());
        List<Integer> divisionList = new ArrayList<>();

        for(int standard : standardList) {
            divisionList.addAll(divisionRepository.findByStandardEntity_Id(standard)
                    .stream()
                    .map(div -> div.getId()).collect(Collectors.toList()));
        }

        List<ScheduleTopThreeStandard> studentList = new ArrayList<>();
        for(int standard : standardList) {
            studentList.addAll(divisionRepository.findByStandardEntityIdAndStudentEntityListResult(standard,"Pass")
                    .stream()
                    .flatMap(div -> div.getStudentEntityList().stream())
                    .sorted(Comparator.comparing(StudentEntity::getPercentage).reversed()).limit(3)
                    .map(stud -> new ScheduleTopThreeStandard(stud.getId(),stud.getStudentName(),stud.getPercentage(), LocalDateTime.now().getYear())).collect(Collectors.toList()));
        }

        for (ScheduleTopThreeStandard student : studentList){
            scheduleRepository.save(student);
        }
    }
}
