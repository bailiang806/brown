package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.UserBridge;
import com.happydriving.rockets.server.entity.UserLocation;
import com.happydriving.rockets.server.mapper.UserBridgeMapper;
import com.happydriving.rockets.server.mapper.UserLocationMapper;
import com.happydriving.rockets.server.service.WeixinPaymentService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 用户位置信息获取接口
 *
 * Created by jasonzhu on 8/7/15.
 */
@Controller
@RequestMapping("/geolocation")
public class GeoLocationController {
    private static final Log LOG = LogFactory.getLog(GeoLocationController.class);;


    @Autowired
    UserBridgeMapper userBridgeMapper;
    @Autowired
    UserLocationMapper userLocationMapper;

    /**
     *
     * 获取当前用户最近一次位置信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/lastLoc", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject lastLoc(HttpServletRequest request) throws BusinessException {
        try {
            //--userId--
            int userId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString(), 10);

            UserBridge userBridge = userBridgeMapper.selectByUserId(userId);  //TODO jason4zhu: userId可能不存在, 相关处理逻辑实现
            UserLocation userLocation = userLocationMapper.selectLastLocation(userBridge.getOpenId());

            return new ResponseJsonObject(true, userLocation);

        } catch (Exception e) {
            LOG.error("exception=["+e.getMessage()+"], stacktrace=["+ ExceptionUtils.getStackTrace(e)+"]");
            throw new BusinessException("Exception occurred.");
        }
    }










}