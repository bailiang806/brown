package com.happydriving.rockets.server.component.crontask;

import com.happydriving.rockets.server.entity.Coach;
import com.happydriving.rockets.server.entity.CoachExample;
import com.happydriving.rockets.server.entity.DsTrainingSchedule;
import com.happydriving.rockets.server.mapper.CoachMapper;
import com.happydriving.rockets.server.mapper.DsTrainingScheduleMapper;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.DsTrainingScheduleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 每天自动给教练安排日程
 * Created by gaoc10 on 2015/10/15 0015.
 */
@Component
public class AutoCoachSchedule {
    private static final Log LOG = LogFactory.getLog(AutoCoachSchedule.class);

    @Autowired
    private CoachService coachService;

    @Autowired
    private CoachMapper coachMapper;

    @Autowired
    private DsTrainingScheduleService dsTrainingScheduleService;


    @Autowired
    private DsTrainingScheduleMapper dsTrainingScheduleMapper;

    public void arrangeCoachSchedule() {
        CoachExample example = new CoachExample();
        example.createCriteria().andIsHideEqualTo("Y");
        List<Coach> coaches = coachMapper.selectByExample(example);
        List<String> trainingTimeList = new ArrayList<>();
        trainingTimeList.add("TRAINING_TIME1");
        trainingTimeList.add("TRAINING_TIME2");
        trainingTimeList.add("TRAINING_TIME3");
        trainingTimeList.add("TRAINING_TIME4");
        DateTime date = new DateTime().plusDays(2);
        String da = date.toString("yyyyMMdd");
        int i = 1;
        for (Coach coach : coaches) {
            for (String trainingTime : trainingTimeList) {
                if (!dsTrainingScheduleService
                        .isCourseExit(String.valueOf(coach.getId()), date.toDate(), trainingTime)) {
                    int random = (int) (Math.random() * 8999) + 1000;
                    String key = da + String.valueOf(i) + String.valueOf(random);
                    DsTrainingSchedule dsTrainingSchedule = new DsTrainingSchedule();
                    dsTrainingSchedule.setId(key);
                    dsTrainingSchedule.setTrainingCity(String.valueOf(coach.getCityId()));
                    dsTrainingSchedule.setTrainingDate(new Date(date.getMillis()));
                    dsTrainingSchedule.setTrainingTime(trainingTime);
                    dsTrainingSchedule.setTrainingCourse(coach.getTrainingCourse());
                    dsTrainingSchedule.setCoachId(String.valueOf(coach.getId()));
                    dsTrainingSchedule.setTrainingCount(new BigDecimal(4));
                    dsTrainingSchedule.setTrainingSiteId(coach.getTrainingSiteId());
                    dsTrainingScheduleMapper.insert(dsTrainingSchedule);
                    i++;
                }
            }
        }
        LOG.info("给所有教练安排了后天一天四节课的日程");
    }
}
