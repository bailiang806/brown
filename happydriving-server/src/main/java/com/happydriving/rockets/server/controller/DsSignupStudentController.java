package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.component.message.SmsResultJsonObject;
import com.happydriving.rockets.server.entity.DsSignupStudent;
import com.happydriving.rockets.server.entity.DsTrainingSchedule;
import com.happydriving.rockets.server.model.DsTrainingScheduleInfo;
import com.happydriving.rockets.server.service.DsSignupStudentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoc10 on 2015/9/26 0026.
 */
@Controller
@RequestMapping("/dsSignupStudent")
public class DsSignupStudentController {

    @Autowired
    private DsSignupStudentService dsSignupStudentService;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;

//    @RequestMapping(value = "/getDsSignupStudents", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getAllDsSignupStudentsDetails(HttpServletRequest request) {
//        return new ResponseJsonObject(true, dsSignupStudentService.getAllDsSignupStudent());
//    }

    /**
     * 学员手机号登录
     *
     * @param request-需要参数phone,inputCode
     * @return
     */
    @RequestMapping(value = "/studentLoginByPhone", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject studentLoginByPhone(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String inputCode = request.getParameter("inputCode");

        if (!smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
            return new ResponseJsonObject(false, "手机验证码不正确或已经过期!");
        }
        DsSignupStudent dsSignupStudent = dsSignupStudentService.getDsSignupStudentDetailByPhone(phone);
        if (dsSignupStudent == null) {
            return new ResponseJsonObject(false, "您不是报名学员，无法登陆！");
        }
        request.getSession().setAttribute("studentId", dsSignupStudent.getId());
        request.getSession().setAttribute("phone", phone);
        return new ResponseJsonObject(true, dsSignupStudent);
    }

    /**
     * 学员得到可选课程
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAvailableCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject studentReservation(HttpServletRequest request) {
        String studentId = request.getParameter("studentId") == null ? "" : request.getParameter("studentId");
        DsSignupStudent dsSignupStudent = dsSignupStudentService.getDsSignupStudentById(studentId);

        String type = dsSignupStudent == null ? "" : dsSignupStudent.getTrainType();
        if (!type.equals("TRAIN_TYPE_ZX")) {
            return new ResponseJsonObject(false, "您不是自训学员，没有可选课程");
        } else {
            String studyProgress = dsSignupStudent.getStudyProgress();
            List<String> validStudentProgresses = Arrays.asList("STUDY_PROGRESS_LR", "STUDY_PROGRESS_1", "STUDY_FINISH_1", "STUDY_FINISH_2", "STUDY_PROGRESS_2", "STUDY_FINISH_3");
//            if (studyProgress.equals("STUDY_PROGRESS_1") || studyProgress.equals("STUDY_PROGRESS_2")) {
            if (validStudentProgresses.contains(studyProgress)) {
                List<DsTrainingScheduleInfo> availableSchedules =
                        dsSignupStudentService.getAvailableTrainingSchedule(dsSignupStudent);
                dsSignupStudent.setStudyProgress(Arrays.asList("STUDY_PROGRESS_2", "STUDY_FINISH_3").contains(studyProgress) ?
                        "3" : "2");
                return new ResponseJsonObject(true, new DsSignupStudentWrapper(dsSignupStudent, availableSchedules));
            } else {
                return new ResponseJsonObject(false, "您当前不适合学习科目二或者科目三");
            }
        }
    }

    public static class DsSignupStudentWrapper {
        private DsSignupStudent dsSignupStudent;
        private List<DsTrainingScheduleInfo> dsTrainingSchedules;

        public DsSignupStudentWrapper(DsSignupStudent dsSignupStudent,
                                      List<DsTrainingScheduleInfo> dsTrainingSchedules) {
            this.dsSignupStudent = dsSignupStudent;
            this.dsTrainingSchedules = dsTrainingSchedules;
        }

        public DsSignupStudent getDsSignupStudent() {
            return dsSignupStudent;
        }

        public List<DsTrainingScheduleInfo> getDsTrainingSchedules() {
            return dsTrainingSchedules;
        }
    }

    /**
     * @param request-需要参数courseId
     * @return
     */
    @RequestMapping(value = "/studentReserveCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject studentReserveCourse(HttpServletRequest request) {
        ResponseJsonObject resultObject = null;
        String courseId = request.getParameter("courseId");
        String studentId =
                request.getParameter("studentId") == null ? "" : request.getParameter("studentId").toString();
        DsSignupStudent dsSignupStudent = dsSignupStudentService.getDsSignupStudentById(studentId);
        String phone = dsSignupStudent.getPhone();
        int courseResidualCapacity = dsSignupStudentService.getCourseResidualCapacity(courseId);
        if (courseResidualCapacity == 0) {
            return new ResponseJsonObject(false, "该课程已选满");
        }
        List<DsTrainingScheduleInfo> selectedCourses = dsSignupStudentService.getReservedCourse(studentId);
        if (selectedCourses.size() > 0) {
            return new ResponseJsonObject(false, "您已有预约课程，不可再预约!");
        }
        dsSignupStudentService.insertDsStudentReservation(studentId, courseId);
        try {
            smsAuthorizedTools
                    .sendReserveSuccessMessage(phone, dsSignupStudentService.getCourseDetailByCourseId(courseId));
        } catch (BusinessException e) {
            return new ResponseJsonObject(true,"选课成功");
        }
        return new ResponseJsonObject(true, "选课成功");
    }

    /**
     * 学员查看已预约课程
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getReservedCourse", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getReserveCourse(HttpServletRequest request) {
        String studentId =
                request.getParameter("studentId") == null ? "" : request.getParameter("studentId").toString();
        List<DsTrainingScheduleInfo> dsTrainingSchedules = dsSignupStudentService.getReservedCourse(studentId);
        return new ResponseJsonObject(true, dsTrainingSchedules);
    }

    /**
     * 删除选课
     *
     * @param request-需要参数courseId
     * @return
     */
    @RequestMapping(value = "/deleteReservedCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject deleteCourse(HttpServletRequest request) {
        String studentId =
                request.getParameter("studentId") == null ? "" : request.getParameter("studentId").toString();
        String courseId = request.getParameter("courseId");
        DsTrainingScheduleInfo course = dsSignupStudentService.getCourseDetailByCourseId(courseId);
        DateTime trainingDate = new DateTime(course.getTrainingDate().getTime());
        DateTime now = new DateTime(new Date().getTime());
        int hour = now.getHourOfDay();
        DateTime dateTimeAdd1 =
                new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0).plusDays(1);
        DateTime dateTimeAdd2 =
                new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0).plusDays(2);
        if (hour < 19) {
            if (trainingDate.isBefore(dateTimeAdd1)) {
                return new ResponseJsonObject(false, "您现在不能取消该课程的预约!");
            }
        } else {
            if (trainingDate.isBefore(dateTimeAdd2)) {
                return new ResponseJsonObject(false, "您现在不能取消该课程的预约!");
            }
        }
        dsSignupStudentService.deleteReservedCourse(studentId, courseId);
        return new ResponseJsonObject(true, "删除成功");
    }
}
