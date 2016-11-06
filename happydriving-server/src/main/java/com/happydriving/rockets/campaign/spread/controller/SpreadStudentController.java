package com.happydriving.rockets.campaign.spread.controller;

import com.happydriving.rockets.campaign.spread.entity.SpreadStudent;
import com.happydriving.rockets.campaign.spread.model.PotentialStudentDisplayed;
import com.happydriving.rockets.campaign.spread.model.SpreadStudentDetailInfo;
import com.happydriving.rockets.campaign.spread.service.SpreadStudentService;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.CommonUtils;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.MailConfiguration;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.DsPotentialStudent;
import com.happydriving.rockets.server.mapper.DsPotentialStudentMapper;
import com.happydriving.rockets.server.service.DsPotentialStudentService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/campaign/spreadStudent")
public class SpreadStudentController {

//    private static final boolean VALIDATE_SMS_INPUTCODE = true;

    @Autowired
    private SpreadStudentService spreadStudentService;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private DsPotentialStudentService dsPotentialStudentService;

    @Autowired
    private DsPotentialStudentMapper dsPotentialStudentMapper;


    /**
     * 注册学员，如果该学员已经被注册过，提示错误
     *
     * @param request - 需要参数 name，phone, referrerId, cityId, inputCode
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject register(HttpServletRequest request) throws BusinessException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        if (StringUtils.isBlank(name) || StringUtils.isBlank(phone)) {
            return new ResponseJsonObject(false, "姓名，手机号不能为空!");

        }

//        String inputCode = request.getParameter("inputCode");
//        if (!smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
//            return new ResponseJsonObject(false, "手机验证码不正确或已经过期!");
//        }

        SpreadStudent spreadStudentByPhone = spreadStudentService.getSpreadStudentByPhone(phone);
        if (spreadStudentByPhone != null) {
            return new ResponseJsonObject(false, String.format("手机号: %s 的用户已经被推荐过，不能重复推荐!", phone));
        }

        SpreadStudent spreadStudent = new SpreadStudent();
        spreadStudent.setName(name);
        spreadStudent.setPhone(phone);
        int referrerId = Integer.parseInt(request.getParameter("referrerId"));
        if (referrerId == -1) {
            spreadStudent.setReferrerId(null);
        } else {
            spreadStudent.setReferrerId(referrerId);
        }
        spreadStudent.setCityId(cityId);
        spreadStudentService.insertAndGetId(spreadStudent);

        DsPotentialStudent dsPotentialStudent = new DsPotentialStudent();
        dsPotentialStudent.setName(name);
        dsPotentialStudent.setServiceCityId(request.getParameter("cityId"));
        dsPotentialStudent.setPhone(phone);
        dsPotentialStudent.setIsSignup("N");
        if (request.getParameter("referrerId").equals("-1")) {
            dsPotentialStudent.setInfoChannel("INFO_CHANNEL_WEB");
        } else {
            dsPotentialStudent.setInfoChannel("INFO_CHANNEL_REFERRE");
            dsPotentialStudent.setReferrerId(request.getParameter("referrerId"));
        }
        dsPotentialStudent.setStudentFollow("STUDENT_FOLLOW_U");
        dsPotentialStudent.setServiceCityId(request.getParameter("cityId"));
        dsPotentialStudent.setRecordDate(new Date());
        dsPotentialStudent.setNextVisitDate(new Date());
        dsPotentialStudentService.insertDsPotentialStudent(dsPotentialStudent);

        MailConfiguration mailConfiguration = new MailConfiguration();
        String subject =
                String.format("新学员 :%s 推荐！请尽快联系", name);
        mailConfiguration.setSubject(subject);
        mailConfiguration.setMailToReceipients(
                Arrays.asList(new String[]{CommonConstants.CITY_TO_MAILBOX_MAP.get(cityId)}));
//        mailConfiguration.setMailToReceipients(Arrays.asList(new String[]{"mazhiqiang@ejiapei.com"}));
        mailConfiguration.setTemplate("single-student.ftl");

        SpreadStudentDetailInfo spreadStudentById =
                spreadStudentService.getSpreadStudentDetailsById(spreadStudent.getId());

        PotentialStudentDisplayed potentialStudent = new PotentialStudentDisplayed();
        potentialStudent.setName(name);
        potentialStudent.setPhone(phone);
        potentialStudent.setCity(spreadStudentById.getCityName());
        potentialStudent.setReferrerName(StringUtils.isNotBlank(spreadStudentById.getReferrerName())
                ? spreadStudentById.getReferrerName() : "无");
        potentialStudent.setReferrerPhone(StringUtils.isNotBlank(spreadStudentById.getReferrerPhone())
                ? spreadStudentById.getReferrerPhone() : "无");
        potentialStudent.setTimestamp(CommonUtils.transferDateToString(spreadStudentById.getCreatedAt()));

        mailConfiguration.setMailObject(potentialStudent);
        mailSender.sendTemplateMail(mailConfiguration);
        return new ResponseJsonObject(true, spreadStudent);
    }

//    @RequestMapping(value = "/updateStudentStatus", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject updateSpreadStudentStatus(HttpServletRequest request) {
//        int studentId = Integer.parseInt(request.getParameter("studentId"));
//
//        SpreadStudent spreadStudent = spreadStudentService.getSpreadStudentById(studentId);
//        if (spreadStudent == null) {
//            return new ResponseJsonObject(false, String.format("学员id: %s 未找到!", studentId));
//        }
//
//        String comment = request.getParameter("comment");
//        spreadStudent.setComment(comment);
//        boolean isTransfer = Boolean.valueOf(request.getParameter("isTransfer"));
//        spreadStudent.setIsTransfer(isTransfer);
//        if (isTransfer) {
//            spreadStudent.setTransferAt(new Date());
//        }
//        spreadStudentService.updateSpreadStudent(spreadStudent);
//        return new ResponseJsonObject(true, spreadStudent);
//    }
//
//    @RequestMapping(value = "/getAllStudentDetails", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getAllSpreadStudentsByCityId() {
//        return new ResponseJsonObject(true, spreadStudentService.getAllSpreadStudentsByCityId());
//    }


}
