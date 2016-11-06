package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.model.CoachProductInfo;
import com.happydriving.rockets.server.model.CoachScheduleDetailInfo;
import com.happydriving.rockets.server.model.CoachSelectCondition;
import com.happydriving.rockets.server.model.StudentScheduleDetailInfo;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.StudentService;
import com.happydriving.rockets.server.service.UserService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/student")
public class StudentController {

//    private static final Log LOG = LogFactory.getLog(StudentController.class);
//
//    private static final String NO_SELECTED = "0";
//
//    @Autowired
//    private StudentService studentService;
//
//    @Autowired
//    private CoachService coachService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SmsAuthorizedTools smsAuthorizedTools;
//
//    @RequestMapping("/show_detail")
//    public ModelAndView showStudentDetail(HttpServletRequest request) {
//        Object userid = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (userid == null) {
//            return new ModelAndView("redirect:/jsp/studentGuide.jsp");
//        }
//        int studentId = Integer.parseInt(userid.toString());
//        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//        LOG.info(String.format("Login as student(id=%s, name=%s)", userid, studentInfo.getName()));
//        if (StringUtils.isBlank(studentInfo.getName())) {
//            return new ModelAndView("student_basic");
//        }
//        boolean payed = studentService.isStudentSelectCoachAndPayed(studentId);
//        if (!payed) {
//            ModelAndView modelAndView = new ModelAndView("student_choosecoach");
//            modelAndView.addObject("studentInfo", studentInfo);
//            return modelAndView;
//        }
//        return new ModelAndView("redirect:/student/student_calendar");
//    }
//
//    @RequestMapping("/updateInfo")
//    public ModelAndView updateStudentInfo(HttpServletRequest request) throws BusinessException {
//        int studentId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString());
//        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//        studentInfo.setName(request.getParameter("username"));
//        studentInfo.setUrgentName(request.getParameter("urgentname"));
//        studentInfo.setUrgentPhone(request.getParameter("urgentphone"));
//        studentInfo.setT1(String.valueOf(request.getParameter("province")));
//        studentInfo.setT2(String.valueOf(request.getParameter("city")));
//        studentInfo.setT3(String.valueOf(request.getParameter("county")));
//        studentInfo.setT4(String.valueOf(request.getParameter("town")));
//        studentInfo.setAddress(request.getParameter("address"));
//        studentInfo.setUpdatedAt(new Date());
//        studentService.updateStudent(studentInfo);
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("student_choosecoach");
//        return modelAndView;
//    }
//
////    @RequestMapping("/searchCoach")
////    public ModelAndView searchCoachByCondition(HttpServletRequest request) {
////        CoachSelectCondition coachSelectCondition = new CoachSelectCondition();
////        if (request.getParameter("carType") != null && !NO_SELECTED.equals(request.getParameter("carType"))) {
////            coachSelectCondition.setCarTypeId(Integer.parseInt(request.getParameter("carType")));
////        }
////        if (request.getParameter("classType") != null && !NO_SELECTED.equals(request.getParameter("classType"))) {
////            coachSelectCondition.setClassTypeId(Integer.parseInt(request.getParameter("classType")));
////        }
////        if (request.getParameter("province") != null && !NO_SELECTED.equals(request.getParameter("province"))) {
////            coachSelectCondition.setProvinceId(Integer.parseInt(request.getParameter("province")));
////        }
////        if (request.getParameter("city") != null && !NO_SELECTED.equals(request.getParameter("city"))) {
////            coachSelectCondition.setCityId(Integer.parseInt(request.getParameter("city")));
////        }
////        if (request.getParameter("county") != null && !NO_SELECTED.equals(request.getParameter("county"))) {
////            coachSelectCondition.setCountyId(Integer.parseInt(request.getParameter("county")));
////        }
////        if (request.getParameter("town") != null && !NO_SELECTED.equals(request.getParameter("town"))) {
////            coachSelectCondition.setTownId(Integer.parseInt(request.getParameter("town")));
////        }
////
////        if (request.getParameter("priceRange") != null && !NO_SELECTED.equals(request.getParameter("priceRange"))) {
////            String priceRange = request.getParameter("priceRange");
////            String[] split = priceRange.split("-");
////            coachSelectCondition.setPriceRangeMin(Integer.parseInt(split[0]));
////            coachSelectCondition.setPriceRangeMax(Integer.parseInt(split[1]));
////        }
////
////        List<CoachProductInfo> coachProductInfoByCondition =
////                coachService.getCoachProductInfoByCondition(coachSelectCondition);
//////        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, coachProductInfoByCondition);
////        ModelAndView modelAndView = new ModelAndView();
////        modelAndView.setViewName("student_coachlist");
////        modelAndView.addObject("coachProductList", coachProductInfoByCondition);
////        return modelAndView;
////    }
//
////    @RequestMapping("/selectCoach")
////    public ModelAndView selectCoach(HttpServletRequest request) {
////        Object id = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
////        if(id == null){
////            return new ModelAndView("redirect:/jsp/studentGuide.jsp");
////        }
////        int studentId = Integer.parseInt(id.toString());
////        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
////        studentInfo.setBanjiId(Integer.parseInt(request.getParameter("classTypeId")));
////        studentInfo.setChexingId(Integer.parseInt(request.getParameter("carTypeId")));
////        int productId = Integer.parseInt(request.getParameter("productId"));
////        studentInfo.setProductId(productId);
////        studentInfo.setUpdatedAt(new Date());
////        studentService.updateStudent(studentInfo);
////
////        CoachProductInfo coachProductInfo = coachService.getCoachProductDetailById(productId);
////
////        ModelAndView modelAndView = new ModelAndView();
////        modelAndView.setViewName("student_pay");
////        modelAndView.addObject("coachInfo", coachProductInfo);
////        return modelAndView;
////    }
//
//    @RequestMapping("/student_booking")
//    public ModelAndView student_booking(HttpServletRequest request) {
//        Object userid = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (userid == null) {
//            return new ModelAndView("redirect:/jsp/studentGuide.jsp");
//        }
//        int studentId = Integer.parseInt(userid.toString());
//        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//        List<Map> CoachDetailInfos = studentService.getCoachByStudentId(studentId);
//        ModelAndView modelAndView = new ModelAndView();
//        if (CoachDetailInfos == null || CoachDetailInfos.size() == 0) {
//            modelAndView.addObject("coachDetailInfo", null);
//        } else {
//            modelAndView.addObject("coachDetailInfo", CoachDetailInfos.get(0));
//        }
//        modelAndView.setViewName("student_booking");
//        modelAndView.addObject("studentInfo", studentInfo);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/getSchedule", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getStudentSchedule(HttpServletRequest request) {
//        if (request.getSession().getAttribute(CommonConstants.SESSION_ROLE) == null) {
//            return new ResponseJsonObject(false, "需要登录才能显示周历！");
//        }
//        if (CommonConstants.ROLE_GUEST.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
//            return new ResponseJsonObject(true, Collections.emptyList());
//        }
//        if (CommonConstants.ROLE_STUDENT.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
//            int studentId = Integer.parseInt(
//                    request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString());
//            List<StudentScheduleDetailInfo> studentScheduleDetails =
//                    studentService.getStudentScheduleDetailById(studentId);
//            for (StudentScheduleDetailInfo studentScheduleDetail : studentScheduleDetails) {
//                studentScheduleDetail.setCurrentDate(String.format("%tF", studentScheduleDetail.getStartTime()));
//                studentScheduleDetail.setStartTimeString(String.format("%tR", studentScheduleDetail.getStartTime()));
//                studentScheduleDetail.setEndTimeString(String.format("%tR", studentScheduleDetail.getEndTime()));
//                studentScheduleDetail
//                        .setDurationHour(Integer.parseInt(String.format("%tH", studentScheduleDetail.getEndTime()))
//                                - Integer.parseInt(String.format("%tH", studentScheduleDetail.getStartTime())));
//
//            }
//            return new ResponseJsonObject(true, studentScheduleDetails);
//        } else {
//            return new ResponseJsonObject(false, String.format("需要身份为 学员 或 游客登录才能查看周历！"));
//        }
//    }
//
//    @RequestMapping(value = "/getCoachCourse", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getStudentCoachCourseList(HttpServletRequest request) {
//        int studentId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString());
////        int studentId = 176;
//        List<CoachScheduleDetailInfo> coachScheduleDetailInfos = studentService.getCoachScheduleByStudentId(studentId);
//        for (CoachScheduleDetailInfo coachScheduleDetailInfo : coachScheduleDetailInfos) {
//            coachScheduleDetailInfo.setCurrentDate(String.format("%tF", coachScheduleDetailInfo.getStartTime()));
//            coachScheduleDetailInfo.setStartTimeString(String.format("%tR", coachScheduleDetailInfo.getStartTime()));
//            coachScheduleDetailInfo.setEndTimeString(String.format("%tR", coachScheduleDetailInfo.getEndTime()));
//            coachScheduleDetailInfo
//                    .setDurationHour(Integer.parseInt(String.format("%tH", coachScheduleDetailInfo.getEndTime()))
//                            - Integer.parseInt(String.format("%tH", coachScheduleDetailInfo.getStartTime())));
//        }
//        return new ResponseJsonObject(true, coachScheduleDetailInfos);
//    }
//
////    @RequestMapping(value = "/addSchedule")
////    @ResponseBody
////    public ResponseJsonObject addStudentSchedule(@RequestParam("coachScheduleId") String id,
////                                                 HttpServletRequest request) {
////        int studentId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString());
//////        int studentId = 176;
////        //String id = request.getParameter("coachScheduleId");
////        int coachScheduleId = Integer.parseInt(id);
////
////        CoachSchedule coachSchedule = coachService.getCoachScheduleById(coachScheduleId);
////        if (coachSchedule == null) {
////            return new ResponseJsonObject(false, String.format("预定的教练日程(id=%s)不存在，不能选择!", coachScheduleId));
////        }
////
////        List<StudentScheduleDetailInfo> studentScheduleDetails =
////                studentService.getStudentScheduleDetailById(studentId);
////        Interval toAddScheduleInterval =
////                new Interval(new DateTime(coachSchedule.getStarttime()), new DateTime(coachSchedule.getEndtime()));
//////        Interval
////        for (StudentScheduleDetailInfo studentScheduleDetail : studentScheduleDetails) {
////            if (studentScheduleDetail.getCoachScheduleId() == coachScheduleId) {
////                return new ResponseJsonObject(false, "学员已经预订过该日程，不能重复预定!");
////            }
////
////            Interval currentScheduleInterval = new Interval(new DateTime(studentScheduleDetail.getStartTime()),
////                    new DateTime(studentScheduleDetail.getEndTime()));
////            if (toAddScheduleInterval.overlaps(currentScheduleInterval)) {
////                return new ResponseJsonObject(false,
////                        String.format("学员选择的日程(%s - %s)与原有日程(%s - %s)冲突!",
////                                new DateTime(coachSchedule.getStarttime()).toString("yyyyMMdd HH:mm"),
////                                new DateTime(coachSchedule.getEndtime()).toString("yyyyMMdd HH:mm"),
////                                new DateTime(studentScheduleDetail.getStartTime()).toString("yyyyMMdd HH:mm"),
////                                new DateTime(studentScheduleDetail.getEndTime()).toString("yyyyMMdd HH:mm")));
////            }
////        }
////
////        StudentSchedule studentSchedule = new StudentSchedule();
////        studentSchedule.setUserId(studentId);
////        studentSchedule.setCoachScheduleId(coachScheduleId);
////        studentSchedule.setCreatedAt(new Date());
////        studentSchedule.setUpdatedAt(new Date());
////
////        studentService.addStudentSchedule(studentSchedule);
////
////        //notify coach
////        try {
////            User user = userService.getUserById(studentSchedule.getUserId());
////            smsAuthorizedTools.sendNotifyMessage(user.getPhone(), SmsAuthorizedTools.COACH_APPOINTMENT_NOTIFY);
////        } catch (BusinessException e) {
////            LOG.error(e);
////        }
////
////        return new ResponseJsonObject(true, null, studentSchedule.getId());
////    }
//
//    @RequestMapping("/student_calendar")
//    public ModelAndView student_calendar(HttpServletRequest request) {
//        Object userid = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (userid == null) {
//            return new ModelAndView("student_calendar");
//        }
//        int studentId = Integer.parseInt(userid.toString());
//        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//        List<Map> CoachDetailInfos = studentService.getCoachByStudentId(studentId);
//        ModelAndView modelAndView = new ModelAndView();
//        if (CoachDetailInfos == null || CoachDetailInfos.size() == 0) {
//            modelAndView.addObject("coachDetailInfo", null);
//        } else {
//            modelAndView.addObject("coachDetailInfo", CoachDetailInfos.get(0));
//        }
//        modelAndView.setViewName("student_calendar");
//        modelAndView.addObject("studentInfo", studentInfo);
//        return modelAndView;
//    }
//
//    @RequestMapping("/index")
//    public ModelAndView index(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("studentIndex");
//        return modelAndView;
//    }
//
//    /**
//     * 根据登录学员，来获取其选择报名的教练<p></p>
//     * <li>如果学员没有选择教练，返回状态false，提示学员没有选择教练</li>
//     * <li>如果学员虽然选择教练，返回状态false，提示学员并没有付款</li>
//     * <li>只有当学员选择教练并付款完成后，才返回状态true，以及教练的详细信息</li>
//     * @param request -
//     * @return -
//     */
//    @RequestMapping(value = "/getCoachDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getCoachDetail(HttpServletRequest request) {
//        Object studentIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (studentIdObject == null) {
//            return new ResponseJsonObject(false, "学员并没有登录!");
//        }
//        int studentId = Integer.parseInt(studentIdObject.toString());
////        int studentId = 176;
//        List<Map> coachByStudentId = studentService.getCoachByStudentId(studentId);
//        if (coachByStudentId == null || coachByStudentId.size() == 0) {
//            return new ResponseJsonObject(false, "该学员并没有选择教练!");
//        } else {
//            boolean payed = studentService.isStudentSelectCoachAndPayed(studentId);
//            if (!payed) {
//                return new ResponseJsonObject(false, "该学员并没有付款!");
//            }
//            int coachId = Integer.parseInt(coachByStudentId.get(0).get("id").toString());
//            return new ResponseJsonObject(true, coachService.getCoachDetailInfoByUserId(coachId));
//        }
//    }
//
//    /**
//     * 获取该学员登录用户是否已经付款
//     * @param request －
//     * @return － json格式结果, 对象中仅包含: true 已付款 / false 未付款
//     */
//    @RequestMapping(value = "/isStudentPayed", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject isStudentPayed(HttpServletRequest request) {
//        Object studentIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (studentIdObject == null) {
//            return new ResponseJsonObject(false, "学员并没有登录!");
//        }
//        int studentId = Integer.parseInt(studentIdObject.toString());
//        return new ResponseJsonObject(true, studentService.isStudentSelectCoachAndPayed(studentId));
//    }
//
//    /**
//     * 判断一个学员的信息是否已经完全<P>
//     * 判断标准为student表中的name字段是否为空<P>
//     * 用手机注册完成后，会在user, student表中都新建一条记录，如果记录中student.name字段为空，认为该学员信息不完整
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/isStudentInfoComplete", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject isStudentInfoComplete(HttpServletRequest request) {
//        Object studentIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (studentIdObject == null) {
//            return new ResponseJsonObject(false, "学员并没有登录!");
//        }
//        int studentId = Integer.parseInt(studentIdObject.toString());
////        int studentId = Integer.parseInt(request.getParameter("studentId"));
//        StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//        return new ResponseJsonObject(true, StringUtils.isNotBlank(studentInfo.getName()));
//    }

}
