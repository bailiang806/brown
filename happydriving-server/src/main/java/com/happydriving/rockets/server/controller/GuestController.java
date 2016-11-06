package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.GuestTrack;
import com.happydriving.rockets.server.mapper.GuestTrackMapper;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 游客相关接口
 * <p/>
 * Created by jasonzhu on 10/7/15.
 */
@Controller
@RequestMapping("/guest")
public class GuestController {

    private static final Log LOG = LogFactory.getLog(GuestController.class);

    @Autowired
    private GuestTrackMapper guestTrackMapper;


    /**
     * 游客入口页面接口。
     * 记录游客手机号、通道（查看的用户类型）、和当前时间
     *
     * @param request
     * @return
     * @throws BusinessException
     * @throws IOException
     */
    @RequestMapping(value = "/portal", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseJsonObject oauth_portal(HttpServletRequest request) throws BusinessException, IOException {

        try {
            String phone = request.getParameter("phone");
            String userType = request.getParameter("userType");
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(userType)) {
                LOG.error("parameter error. phone=[" + phone + "], userType=[" + userType + "]");
                throw new BusinessException("parameter error.");
            }
            GuestTrack guestTrack = new GuestTrack();
            guestTrack.setPhone(phone);
            guestTrack.setUserType(userType);
            guestTrack.setCreatedAt(new Date());
            if (guestTrackMapper.insert(guestTrack) <= 0) {
                LOG.info("insert into table guest_track failed. guestTrack=[" + guestTrack + "]");
                throw new BusinessException("write to db error.");
            }

            request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, phone);
            request.getSession().setAttribute(CommonConstants.SESSION_ROLE, CommonConstants.ROLE_GUEST);

            return new ResponseJsonObject(true, "success");
        } catch (Throwable e) {
            LOG.info(
                    "Exception: msg=[" + e.getMessage() + "], stacktrace=[" + ExceptionUtils.getStackTrace(e) + "]");
            return new ResponseJsonObject(false, "internal error.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coach")
    public ModelAndView getCoachEntranceUrl(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("role");
        modelAndView.addObject("userType", "coach");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/student")
    public ModelAndView getStudentEntranceUrl(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("role");
        modelAndView.addObject("userType", "student");
        return modelAndView;
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/zhuditest")
//    public @ResponseBody String zhuditest(HttpServletRequest request) {
//
//        org.slf4j.Logger logger_zhudi = LoggerFactory.getLogger(GuestController.class);
//        logger_zhudi.info("JUDKING_DEBUG. 12333");
//
//
//        return "";
//    }


}