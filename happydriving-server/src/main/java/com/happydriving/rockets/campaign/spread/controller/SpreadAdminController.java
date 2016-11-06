package com.happydriving.rockets.campaign.spread.controller;

import com.happydriving.rockets.campaign.spread.entity.SpreadAdmin;
import com.happydriving.rockets.campaign.spread.entity.SpreadStudent;
import com.happydriving.rockets.campaign.spread.model.CityStatisticInfo;
import com.happydriving.rockets.campaign.spread.model.SpreadReferrerStatisticInfo;
import com.happydriving.rockets.campaign.spread.service.SpreadAdminService;
import com.happydriving.rockets.campaign.spread.service.SpreadReferrerService;
import com.happydriving.rockets.campaign.spread.service.SpreadStudentService;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/campaign/spreadAdmin")
public class SpreadAdminController {

    @Autowired
    private SpreadAdminService spreadAdminService;

    @Autowired
    private SpreadStudentService spreadStudentService;

    @Autowired
    private SpreadReferrerService spreadReferrerService;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseJsonObject login(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String inputCode = request.getParameter("inputCode");

        if (!smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
            return new ResponseJsonObject(false, "手机验证码不正确或已经过期!");
        }

        SpreadAdmin spreadAdmin = spreadAdminService.getSpreadAdminByPhone(phone);
        if (spreadAdmin == null) {
            return new ResponseJsonObject(false, "非管理员，不能登录!");
        }

        return new ResponseJsonObject(true, spreadAdmin);
    }

    @RequestMapping(value = "/getStudentsByCity", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject getAllSpreadStudentDetails(HttpServletRequest request) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        return new ResponseJsonObject(true, spreadStudentService.getAllSpreadStudentsByCityId(cityId));
    }

    @RequestMapping(value = "/updateStudentStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject updateSpreadStudentStatus(HttpServletRequest request) {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        SpreadStudent spreadStudent = spreadStudentService.getSpreadStudentById(studentId);
        if (spreadStudent == null) {
            return new ResponseJsonObject(false, String.format("学员id: %s 未找到!", studentId));
        }

        String comment = request.getParameter("comment");
        spreadStudent.setComment(comment);
        boolean isTransfer = Boolean.valueOf(request.getParameter("isTransfer"));
        spreadStudent.setIsTransfer(isTransfer);
        if (isTransfer) {
            spreadStudent.setTransferAt(new Date());
        }
        spreadStudentService.updateSpreadStudent(spreadStudent);
        return new ResponseJsonObject(true, spreadStudent);
    }

    @RequestMapping(value = "getReferrerStatisticByCity", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getReferrerStatisticByCity(HttpServletRequest request) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        List<SpreadReferrerStatisticInfo> spreadReferrerStatisticInfos =
                spreadReferrerService.getSpreadReferrerStatisticInfoByCityId(cityId);
        return new ResponseJsonObject(true, spreadReferrerStatisticInfos);
    }

    @RequestMapping(value = "getCityStatisticReferrerCount", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCityStatisticReferrerCount(HttpServletRequest request) {
        List<CityStatisticInfo> spreadCityStatisticInfos = spreadReferrerService.getSpreadCityStatisticInfos();
        return new ResponseJsonObject(true, spreadCityStatisticInfos);
    }

}
