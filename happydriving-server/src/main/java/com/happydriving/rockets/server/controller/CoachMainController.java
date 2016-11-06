package com.happydriving.rockets.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gaoying on 15/8/16.
 */
@Controller
@RequestMapping("/coachmain")
public class CoachMainController {
//跳转到coach入口jsp
    @RequestMapping("/tocoach")
    public String toCoachmainjsp(){
        return "coach";
    }
}
