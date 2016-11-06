package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.PotentialStudent;
import com.happydriving.rockets.server.service.PotentialStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于记录，展示，收集潜在学员相关功能
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/potentialStudent")
public class PotentialStudentController {

    @Autowired
    private PotentialStudentService potentialStudentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject addPotentialStudent(HttpServletRequest request) {
        PotentialStudent potentialStudent = new PotentialStudent();
        potentialStudent.setName(request.getParameter("name"));
        potentialStudent.setPhone(request.getParameter("phone"));
        potentialStudent.setCity(request.getParameter("city"));
        potentialStudentService.insertPotentialStudent(potentialStudent);
        return new ResponseJsonObject(true);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getPotentialStudents(HttpServletRequest request) {
        return new ResponseJsonObject(true, potentialStudentService.getPotentialStudents());
    }


}
