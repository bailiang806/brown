package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.crontask.PotentialStudentSendTask;
import com.happydriving.rockets.server.entity.Coach;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.DrivingSchoolService;
import com.happydriving.rockets.server.service.ImportDataService;
import com.happydriving.rockets.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 本Controller主要用于导入一些数据
 *
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/importData")
public class ImportDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private DrivingSchoolService drivingSchoolService;

    @Autowired
    private PotentialStudentSendTask potentialStudentSendTask;

    @Autowired
    private ImportDataService importDataService;

//    @RequestMapping("/test")
//    @ResponseBody
//    public ResponseJsonObject test() throws BusinessException {
////        importOneUser("18650759122", "杨振先", "康达驾校", "yangzhenxian.jpg");
////        importOneUser("1865915747", "刘乾进", "康达驾校", "liuqianjin.jpg");
//
////        importOneUser("17704614366", "王宜芳", "建安驾校", "20150606130728_54858.jpg");
////        importOneUser("18960852044", "张良才", "吉诺驾校", "zhangliangcai.jpg");
////        importOneUser("17750287105", "沈浒", "吉诺驾校", "902638388.jpg");
////        importOneUser("18305910507", "林木根", "吉诺驾校", "linmugen.jpg");
////        importOneUser("13665028731", "林勋", "闽峰驾校", "linxun.jpg");
////        importOneUser("13906910680", "邓乐乐", "闽峰驾校", "denglele.jpg");
////        importOneUser("13107639678", "张义", "吉诺驾校", "zhangyi.jpg");
////        importOneUser("13859194513", "林冰瑜", "吉诺驾校", "linbingyu.jpg");
////        importOneUser("13655026055", "张伟", "吉诺驾校", "20150602184542_50750.jpg");
//
//        return new ResponseJsonObject(true);
//    }

//    private void importOneUser(String phone, String name, String schoolName, String txImgUrl)
//            throws BusinessException {
//        User user = userService.insertUser(phone, CommonConstants.ROLE_COACH);
//        Coach coach = coachService.getCoachByUserId(user.getId());
//        coach.setUserId(user.getId());
//        coach.setName(name);
//        DrivingSchool drivingSchool = drivingSchoolService.getDrivingSchoolByName(schoolName);
//        coach.setSchoolId(drivingSchool.getId());
//        coach.setSex("男");
//        coach.setCityId(1);
//        coachService.updateCoach(coach);
//
//        coachService.insertUpdateCoachPersonalPhoto(user.getId(), txImgUrl);
//    }
//
//    @RequestMapping("sendTask")
//    @ResponseBody
//    public ResponseJsonObject sendPerdaydata() throws BusinessException {
//        potentialStudentSendTask.executeSendPerDayData();
//        return new ResponseJsonObject(true);
//    }

    @RequestMapping(value = "/fromExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject importDataFromExcel(HttpServletRequest request) throws BusinessException {
        String filePath = request.getParameter("filePath");
//        importDataService
//                .insertInputExcelFile("/Users/mazhiqiang/Documents/百度云同步盘/学习教程/miaozhen/e驾陪/e驾陪导入数据模版/e驾陪导入模版.xlsx");
        importDataService.insertInputExcelFile(filePath);
        return new ResponseJsonObject(true);
    }

}
