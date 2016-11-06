package com.happydriving.rockets.server.service;


import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.mapper.DsSignupStudentMapper;
import com.happydriving.rockets.server.mapper.DsStudentReservationMapper;
import com.happydriving.rockets.server.mapper.DsTrainingScheduleMapper;
import com.happydriving.rockets.server.model.DsTrainingScheduleInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by gaoc10 on 2015/9/26 0026.
 */
@Service
public class DsSignupStudentService {
    @Value("#{drivingConfigProperties.coachImageUrlPrefix}")
    private String coachImageUrlPrefix;
    @Resource
    private DsSignupStudentMapper dsSignupStudentMapper;
    @Resource
    private DsTrainingScheduleMapper dsTrainingScheduleMapper;
    @Resource
    private DsStudentReservationMapper dsStudentReservationMapper;

//    public List<DsSignupStudent> getAllDsSignupStudent() {
//        return dsSignupStudentMapper.selectByExample(new DsSignupStudentExample());
//    }

    public DsSignupStudent getDsSignupStudentDetailByPhone(String phone) {
        List<DsSignupStudent> dsSignupStudents = dsSignupStudentMapper.getDsSignupStudentDetailInfoByPhone(phone);
        if (dsSignupStudents.size() == 0) {
            return null;
        } else {
            return dsSignupStudents.get(0);
        }
    }


    public DsSignupStudent getDsSignupStudentById(String studentId) {
        DsSignupStudentExample example = new DsSignupStudentExample();
        example.createCriteria().andIdEqualTo(studentId);
        List<DsSignupStudent> dsSignupStudents = dsSignupStudentMapper.selectByExample(example);
        return dsSignupStudents.isEmpty() ? null : dsSignupStudents.get(0);
    }

    /**
     * 根据学生得到他可选的课程列表。晚上七点前可以约第二天的，七点后只能约后天的
     *
     * @param dsSignupStudent
     * @return
     */
    public List<DsTrainingScheduleInfo> getAvailableTrainingSchedule(DsSignupStudent dsSignupStudent) {
        List<DsTrainingScheduleInfo> dsTrainingSchedules = new ArrayList<DsTrainingScheduleInfo>();
        DateTime now = new DateTime();
        DateTime tomorrow = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0).plusDays(1);
        Date tomorrowDate = tomorrow.toDate();
        String courseType;
        //当前已经修改为在科目二之前的都可以进行预约，包括录入，科目一挂满，科目一通过等
        List<String> course2TrainingProgresses =
                Arrays.asList("STUDY_PROGRESS_LR", "STUDY_PROGRESS_1", "STUDY_FINISH_1", "STUDY_FINISH_2");
        if (course2TrainingProgresses.contains(dsSignupStudent.getStudyProgress())) {
            courseType = "TRAINING_COURSE2";
        } else {
            courseType = "TRAINING_COURSE3";
        }
        dsTrainingSchedules = dsTrainingScheduleMapper.getCoursesByTypeAndStartDate(courseType, tomorrowDate);
        Iterator<DsTrainingScheduleInfo> iterator = dsTrainingSchedules.iterator();
        while (iterator.hasNext()) {
            DsTrainingScheduleInfo course = iterator.next();
            if (course.getTrainingCount().intValue() <= 0) {
                iterator.remove();
            }
        }
        for (DsTrainingScheduleInfo dsTrainingSchedule : dsTrainingSchedules) {
            transferCoachAvatorImgUrl(dsTrainingSchedule);
        }
        List<DsTrainingScheduleInfo> selectedCourses = getReservedCourse(dsSignupStudent.getId());
        if (!selectedCourses.isEmpty()) {
            DsTrainingScheduleInfo selectedCourse = selectedCourses.get(0);
            selectedCourse.setIsSelected(1);
            String selectedCourseId = selectedCourse.getId();
            Iterator<DsTrainingScheduleInfo> it = dsTrainingSchedules.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(selectedCourseId)) {
                    it.remove();
                }
            }
            dsTrainingSchedules.add(0, selectedCourse);
        }
        return dsTrainingSchedules;
    }

    /**
     * 根据课程id得到课程的剩余容量
     *
     * @param courseId
     * @return
     */
    public Integer getCourseResidualCapacity(String courseId) {
        return dsTrainingScheduleMapper.getCourseResidualCapacity(courseId);
    }

    /**
     * 插入选课记录
     *
     * @param studentId
     * @param courseId
     */
    public void insertDsStudentReservation(String studentId, String courseId) {
        DsStudentReservation dsStudentReservation = new DsStudentReservation();
        dsStudentReservation.setCourseId(courseId);
        dsStudentReservation.setStudentId(studentId);
        dsStudentReservation.setCreatedStamp(new Date());
        dsStudentReservationMapper.insert(dsStudentReservation);
    }

    public void deleteReservedCourse(String studentId, String courseId) {
//        DsTrainingSchedule dsTrainingSchedule = dsTrainingScheduleMapper.getCourseDetailByCourseId(courseId);
        DsStudentReservationExample dsStudentReservationExample = new DsStudentReservationExample();
        dsStudentReservationExample.createCriteria().andStudentIdEqualTo(studentId).andCourseIdEqualTo(courseId);
        dsStudentReservationMapper.deleteByExample(dsStudentReservationExample);
    }

    /**
     * 已预约课程
     *
     * @param studentId
     * @return
     */
    public List<DsTrainingScheduleInfo> getReservedCourse(String studentId) {
        Date today = new Date();
        List<DsTrainingScheduleInfo> dsTrainingSchedules =
                dsTrainingScheduleMapper.getSelectedCourseByStudentId(studentId, today);
        DateTime currentTime = new DateTime();
        Iterator<DsTrainingScheduleInfo> iterator = dsTrainingSchedules.iterator();
        while (iterator.hasNext()) {
            DsTrainingScheduleInfo course = iterator.next();
            Date trainingDate = course.getTrainingDate();
            String endTrainingTime = course.getTrainingTime().split("-")[1];
            DateTime trainingEndDateTime = toDateTime(trainingDate, endTrainingTime);
            if (trainingEndDateTime.isBefore(currentTime)) {
                iterator.remove();
            }
        }
        for(DsTrainingScheduleInfo course:dsTrainingSchedules){
            transferCoachAvatorImgUrl(course);
        }
        return dsTrainingSchedules;
    }

    public DsTrainingScheduleInfo getCourseDetailByCourseId(String courseId) {
        return dsTrainingScheduleMapper.getCourseDetailByCourseId(courseId);

    }

    /**
     * 将Date类型的日期，String类型hh：mm的时间转化为DateTime
     *
     * @param date
     * @param time
     * @return
     */
    private DateTime toDateTime(Date date, String time) {
        DateTime dateTime = new DateTime(date.getTime());
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        dateTime = dateTime.plusHours(hour);
        dateTime = dateTime.plusMinutes(minute);
        return dateTime;
    }

    private void transferCoachAvatorImgUrl(DsTrainingScheduleInfo dsTrainingScheduleInfo){
        String cityId=dsTrainingScheduleInfo.getCityId();
        String schoolId=dsTrainingScheduleInfo.getCoachSchoolId();
        String coachImgUrl=dsTrainingScheduleInfo.getCoachImgUrl();
        dsTrainingScheduleInfo.setCoachImgUrl( coachImageUrlPrefix+cityId+"/"+schoolId+"/coach/avator/"+coachImgUrl);
    }
}
