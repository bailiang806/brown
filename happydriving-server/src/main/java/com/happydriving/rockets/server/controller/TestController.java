package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.crontask.DailyReportSendTask;
import com.happydriving.rockets.server.component.crontask.PotentialStudentSendTask;
import com.happydriving.rockets.server.service.PaymentInfoService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 这个controller用来测试一些服务的基本使用，在上线时需要将该测试controller屏蔽掉
 * @author mazhiqiang
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private PotentialStudentSendTask potentialStudentSendTask;

//    @RequestMapping("sendTask")
//    @ResponseBody
//    public ResponseJsonObject sendTask() throws BusinessException {
//        potentialStudentSendTask.executeSendPerDayData();
//        return new ResponseJsonObject(true);
//    }

    @Autowired
    private DailyReportSendTask dailyReportSendTask;
    @Autowired
    private PaymentInfoService paymentInfoService;



//    @RequestMapping("dailyReport")
//    @ResponseBody
//    public ResponseJsonObject dailyReport() {
//        dailyReportSendTask.sendDailyReportToday();
//        return new ResponseJsonObject(true);
//    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public @ResponseBody String update(HttpServletRequest request) throws Exception {

        paymentInfoService.updateTradeStateAndThirdPartyTradeNo("success","paymentInfoService","123456789451445653832378978");
        return null;
    }

}
