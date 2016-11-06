package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.WeixinOauthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * 微信Oauth相关服务入口
 *
 * TODO global: portal-for-test-only
 * TODO zhiqiang: 现在这个入口只是为了测试用，将来需要将WeixinOauthService.userBridge()和注册服务的controller结合
 *
 * Created by jasonzhu on 8/7/15.
 */
@Controller
@RequestMapping("/wxoauth")
public class WeixinOauthController {
    private static final Log logger = LogFactory.getLog(WeixinOauthController.class);


    @Autowired
    private WeixinOauthService weixinOauthService;


    /**
     *
     * 微信OauthAPI的redirect_url入口
     *
     * Oauth授权：     http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/portal", method = RequestMethod.GET)
    public @ResponseBody
    ResponseJsonObject oauth_portal(HttpServletRequest request) throws BusinessException, IOException {

        //将微信用户信息（openId等）与用户手机号关联到user_bridge表
        weixinOauthService.userBridge(request.getParameter("phone"), request.getParameter("code"));

        return new ResponseJsonObject(true, null);
    }

    @RequestMapping(value = "/getAppId", method = RequestMethod.GET)
    public @ResponseBody
    ResponseJsonObject getAppId(HttpServletRequest request) throws BusinessException, IOException {
        return new ResponseJsonObject(true, weixinOauthService.appId);
    }
}