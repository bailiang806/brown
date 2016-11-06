package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.BankInfo;
import com.happydriving.rockets.server.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getAllBankInfos() {
        return new ResponseJsonObject(true, bankService.getBankInfos());
    }

}
