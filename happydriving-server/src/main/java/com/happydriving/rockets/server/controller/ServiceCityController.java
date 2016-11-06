package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.service.ServiceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 提供所有
 *
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/serviceCity")
public class ServiceCityController {

    @Autowired
    private ServiceCityService serviceCityService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getAllServiceCities() {
        return new ResponseJsonObject(true, serviceCityService.getAllServiceCities());
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getServiceCityById(HttpServletRequest request) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        ServiceCity serviceCity = serviceCityService.getCityById(cityId);
        return new ResponseJsonObject(true, serviceCity);
    }

}
