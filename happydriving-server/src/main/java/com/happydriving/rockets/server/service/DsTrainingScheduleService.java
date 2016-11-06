package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.DsTrainingSchedule;
import com.happydriving.rockets.server.entity.DsTrainingScheduleExample;
import com.happydriving.rockets.server.mapper.DsTrainingScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by gaoc10 on 2015/10/15 0015.
 */
@Service
public class DsTrainingScheduleService {
    @Autowired
    private DsTrainingScheduleMapper dsTrainingScheduleMapper;

    public boolean isCourseExit(String coachId, Date date, String trainingTime) {
        DsTrainingScheduleExample example = new DsTrainingScheduleExample();
        example.createCriteria().andCoachIdEqualTo(coachId).andTrainingDateEqualTo(date).andTrainingTimeEqualTo(
                trainingTime);
        List<DsTrainingSchedule> dsTrainingSchedules = dsTrainingScheduleMapper.selectByExample(example);
        if (dsTrainingSchedules.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}
