package com.studentmanagement.util.scheduler;

import com.studentmanagement.repository.DivisionRepository;
import com.studentmanagement.repository.ScheduleRepository;
import com.studentmanagement.repository.StandardRepository;
import com.studentmanagement.entity.ScheduleTopThreeStandard;
import com.studentmanagement.entity.StudentEntity;
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
