package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.ClassTypeService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@RequestMapping("/classtype")
public class ClassTypeController {

    private static final Log LOG = LogFactory.getLog(ClassTypeController.class);

    @Autowired
    private ClassTypeService classTypeService;

    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public JSONPObject getAllClassTypesJsonP() {
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION,
                new ResponseJsonObject(true, classTypeService.getAllClassTypes()));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getAllClassTypes() {
        return new ResponseJsonObject(true, classTypeService.getAllClassTypes());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONPObject insertClassType(HttpServletRequest request) {
        String classTypeName = request.getParameter("classTypeName");

        ResponseJsonObject responseJsonObject = new ResponseJsonObject();
        try {
            classTypeService.insertClassType(classTypeName);
            responseJsonObject.setResult(true);
        } catch (BusinessException e) {
            LOG.error(e);
            responseJsonObject.setResult(false);
            responseJsonObject.setMessage(e.getMessage());
        }
        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, responseJsonObject);
    }

}
