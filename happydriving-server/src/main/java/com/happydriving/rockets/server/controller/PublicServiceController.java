package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.PublicService;
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
 * 公共服务入口
 *
 *
 * Created by jasonzhu on 3/7/15.
 */

@Controller
@RequestMapping("/pubsrv")
public class PublicServiceController {
    private static final Log LOG = LogFactory.getLog(PublicServiceController.class);;

    @Autowired
    private PublicService publicService;


    /**
     *
     * 获取当前服务器域名（不带最后的反斜杠）
     * e.g.
     * http://www.ejiapei.com
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCurrentDomain", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCurrentDomain(HttpServletRequest request) {

        try {
            return new ResponseJsonObject(true, publicService.getCurrentDomain(request));

        } catch (Exception e) {
            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
            return new ResponseJsonObject(false, e.getMessage(), -1);
        }
    }

}
