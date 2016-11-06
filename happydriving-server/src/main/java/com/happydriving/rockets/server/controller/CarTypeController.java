package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.CarTypeService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/cartype")
public class CarTypeController {

    private static final Log LOG = LogFactory.getLog(CarTypeController.class);

    @Autowired
    private CarTypeService carTypeService;

    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public JSONPObject getAllCarTypesJsonP() {
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION,
                new ResponseJsonObject(true, carTypeService.getAllCarTypes()));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getAllCarTypes() {
        return new ResponseJsonObject(true, carTypeService.getAllCarTypes());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONPObject addCarType(HttpServletRequest request) {
        String carTypeName = request.getParameter("carTypeName");
        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        try {
            carTypeService.insertCarType(carTypeName);
            responseJsonObject.setResult(true);
        } catch (BusinessException e) {
            LOG.error(e);
            responseJsonObject.setResult(false);
            responseJsonObject.setMessage(e.getMessage());
        }
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, responseJsonObject);
    }

}
