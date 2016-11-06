package com.happydriving.rockets.server.controller;

import com.alibaba.fastjson.JSON;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.CourseType;
import com.happydriving.rockets.server.entity.CourseTypeExample;
import com.happydriving.rockets.server.mapper.CourseTypeMapper;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jasonzhu on 24/6/15.
 */
@Controller
@RequestMapping("/coursetype")
public class CourseTypeController {


    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public JSONPObject WxPayTest(HttpServletRequest request) {

        try {
            List<CourseType> courseTypes = courseTypeMapper.selectByExample(new CourseTypeExample());
            return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, courseTypes);

        } catch (Exception e) {
            return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION,
                    JSON.parse("{\"result\":\"exception=[" + e.getMessage() + "] thrown.\"}"));
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getAllCourseType() {
        try {
            List<CourseType> courseTypes = courseTypeMapper.selectByExample(new CourseTypeExample());
            return new ResponseJsonObject(true, courseTypes);
        } catch (Exception e) {
            return new ResponseJsonObject(false, e.getMessage());
        }
    }

}
