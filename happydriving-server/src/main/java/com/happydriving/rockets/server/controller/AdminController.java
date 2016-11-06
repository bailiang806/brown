package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.crontask.PotentialStudentSendTask;
import com.happydriving.rockets.server.entity.CoachAuditInfo;
import com.happydriving.rockets.server.model.CoachDetailInfo;
import com.happydriving.rockets.server.service.CoachAdminService;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Log LOG = LogFactory.getLog(AdminController.class);

    @Autowired
    private CoachService coachService;

    @Autowired
    private CoachAdminService coachAdminService;

    @RequestMapping("/showAllCoachs")
    @ResponseBody
    public JSONPObject getAllCoachInfo(HttpServletRequest request) {
        List<CoachDetailInfo> allCoachDetailInfos = coachService.getAllCoachDetailInfos();
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, allCoachDetailInfos);
    }

//    @RequestMapping("/showAllSubmitCoachs")
//    @ResponseBody
//    public JSONPObject getAllSubmitCoachInfos(HttpServletRequest request) {
//        List<CoachDetailInfo> allSubmitCoachDetailInfos = coachService.getAllSubmitCoachDetailInfos();
//        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, allSubmitCoachDetailInfos);
//    }

//    @RequestMapping("/coachDetail")
//    @ResponseBody
//    public JSONPObject getCoachDetailById(HttpServletRequest request) {
//        CoachDetailInfo coachDetailInfo =
//                coachService.getCoachDetailInfoByCoachId(Integer.parseInt(request.getParameter("coachId")));
//        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, coachDetailInfo);
//    }

    @RequestMapping("/coachAuditInfos")
    @ResponseBody
    public JSONPObject getCoachAuditInfos(HttpServletRequest request) {
        String coachId = request.getParameter("coachId");
        List<CoachAuditInfo> coachAuditInfos =
                coachAdminService.getCoachAuditInfosByCoachId(Integer.parseInt(coachId));
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, coachAuditInfos);
    }

//    @RequestMapping("/auditCoach")
//    @ResponseBody
//    public JSONPObject auditCoachInfo(HttpServletRequest request) {
//        CoachAuditInfo coachAuditInfo = new CoachAuditInfo();
//        coachAuditInfo.setUserId(Integer.parseInt(request.getParameter("coachId")));
//        coachAuditInfo.setAuditAdmin(request.getParameter("auditAdmin"));
//        coachAuditInfo.setAuditStatus(request.getParameter("auditStatus"));
//        coachAuditInfo.setAuditMessage(request.getParameter("auditMessage"));
//        coachAuditInfo.setAuditAt(new Date());
//        try {
//            coachAdminService.addCoachAuditInfo(coachAuditInfo);
//            return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, new ResponseJsonObject(true));
//        } catch (BusinessException e) {
//            LOG.error(e);
//            return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION,
//                    new ResponseJsonObject(false, e.getMessage()));
//        }
//    }

//    @Autowired
//    private PotentialStudentSendTask potentialStudentSendTask;

//    @RequestMapping("/test")
//    @ResponseBody
//    public ResponseJsonObject testSendMail() throws BusinessException {
//        potentialStudentSendTask.executeSendPerDayData();
//        return new ResponseJsonObject(true);
//    }

}

