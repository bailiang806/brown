package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.ServiceStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bailiang on 2015/11/12.
 */
@Controller
@RequestMapping("/serviceStore")
public class ServiceStoreController {

    @Autowired
    private ServiceStoreService serviceStoreService;

    @RequestMapping("/cityId")
    @ResponseBody
    public ResponseJsonObject getServiceStoreByCityId(HttpServletRequest request){
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        return new ResponseJsonObject(true,serviceStoreService.getServiceStoreByCityId(cityId));
    }
    //文档描述需要
}
