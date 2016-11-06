package com.happydriving.rockets.server.controller;

import com.github.pagehelper.PageInfo;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.export.CoachImportData;
import com.happydriving.rockets.server.component.export.ImportCoachComponent;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.PaymentInfo;
import com.happydriving.rockets.server.entity.UserLocation;
import com.happydriving.rockets.server.model.CoachTopCountQueryInfo;
import com.happydriving.rockets.server.model.PaginationInfo;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.GeoLocationService;
import com.happydriving.rockets.server.service.PaymentInfoService;
import com.happydriving.rockets.server.utils.md5utils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The controller is used for all anonymous service.
 *
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    private static final Log LOG = LogFactory.getLog(GeoLocationService.class);
    SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private CoachService coachService;

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private ImportCoachComponent importCoachComponent;
    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;
    @Autowired
    private PaymentInfoService paymentInfoService;


    @RequestMapping(value = "/getTopCountCoachs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getTopCountCoaches(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        List<CoachTopCountQueryInfo> coachTopCountBasicInfos =
                coachService.getCoachTopCountBasicInfos(new PaginationInfo(pageNum, pageSize));
        PageInfo<CoachTopCountQueryInfo> pageInfo = new PageInfo<>(coachTopCountBasicInfos);
        return new ResponseJsonObject(true, pageInfo);
    }

    @RequestMapping(value = "/showCoachDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCoachDetail(HttpServletRequest request) {
        int coachId = Integer.parseInt(request.getParameter("coachId"));
        CoachTopCountQueryInfo topCountQueryInfo = coachService.getCoachQueryInfoByCoachId(coachId);
        if (topCountQueryInfo != null) {
            return new ResponseJsonObject(true, topCountQueryInfo);
        } else {
            return new ResponseJsonObject(false, String.format("教练:(id=%s0)不存在！", coachId));
        }
    }

    @RequestMapping(value = "/importCoach", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject importCoachs(HttpServletRequest request) {
        InputStream resourceAsStream = CommonController.class.getResourceAsStream("/scripts/initial_coach");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line = null;
            List<CoachImportData> coachImportDatas = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\t");
                String userName = split[0];
                String phone = split[1];
                String qq = split[2];
                String schoolName = split[3];

                CoachImportData coachImportData = new CoachImportData();
                coachImportData.setUserName(userName);
                coachImportData.setPhone(phone);
                coachImportData.setSchoolName(schoolName);
                coachImportData.setQq(qq);
                coachImportDatas.add(coachImportData);
            }

//            importCoachComponent.importCoachFromCoachList(coachImportDatas);

            return new ResponseJsonObject(true);
        } catch (IOException e) {
            return new ResponseJsonObject(false, e.getMessage());
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
    }

    @RequestMapping(value = "/getCoachLocations", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCoachLocations(HttpServletRequest request) {
        return new ResponseJsonObject(true, coachService.getCoachLocations());
    }


    @RequestMapping(value = "/getUserLocations", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getUserLocations(HttpServletRequest request) {
//        String openId=String.valueOf(request.getSession().getAttribute("openId"));
        String openId="";
        LOG.info("openid from session is:"+openId);
        if(openId==""||openId=="null"||openId==null){
            LOG.info("this openid is test id");
            openId="olraRs1gngHT57ED7HlVEati8vtM";
        }
       // String openId="test";
        UserLocation userlocation=geoLocationService.getUserLocationByOpenId(openId);
        if(userlocation==null){
            new ResponseJsonObject(false, "");
        }
        return new ResponseJsonObject(true,userlocation);
    }

    @RequestMapping(value = "/verifyUserPayInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject setUserPayInfo(HttpServletRequest request) throws BusinessException {

        String name=request.getParameter("name");
        String phone=request.getParameter("phone");
        String verifynum=request.getParameter("verifynum");
        String orderid=request.getParameter("orderid");
        String randomnum=request.getParameter("randomnum");
        String schoolid=request.getParameter("schoolid");

        //校验手机号验证码是否正确
        String date=df.format(new Date());
        String verifyorderid= md5utils.MD5(randomnum+date);

        if(smsAuthorizedTools.validateSmsInfo(phone, verifynum)){
//        if(true){
            //校验订单号
            if(verifyorderid.equals(orderid)){
                LOG.info("orderid is"+orderid);
                LOG.info("phone is"+phone);
                LOG.info("name is"+name);
                LOG.info("schoolid is"+schoolid);
                LOG.info("orderid is"+orderid);



               // 将信息插入到支付信息表
                paymentInfoService.addPaymentInfo(orderid,phone,name,schoolid,new BigDecimal("1"),"going");
            }else{
                return new ResponseJsonObject(false,"操作错误,订单信息有误!");
            }
        }else{
            return new ResponseJsonObject(false,"手机号或验证码不正确,请重新填写");
        }
        return new ResponseJsonObject(true,"校验无误");
    }
}
