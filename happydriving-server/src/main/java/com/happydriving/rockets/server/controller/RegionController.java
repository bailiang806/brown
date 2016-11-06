package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.CityExample;
import com.happydriving.rockets.server.entity.CountyExample;
import com.happydriving.rockets.server.entity.ProvinceExample;
import com.happydriving.rockets.server.entity.TownExample;
import com.happydriving.rockets.server.mapper.CityMapper;
import com.happydriving.rockets.server.mapper.CountyMapper;
import com.happydriving.rockets.server.mapper.ProvinceMapper;
import com.happydriving.rockets.server.mapper.TownMapper;
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
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountyMapper countyMapper;

    @Autowired
    private TownMapper townMapper;

//    @Autowired
//    private RegionGeneratorTask regionService;

    @RequestMapping(value = "/province", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getProvince() {
        ProvinceExample provinceExample = new ProvinceExample();
        ProvinceExample.Criteria criteria = provinceExample.createCriteria();
        return new ResponseJsonObject(true, provinceMapper.selectByExample(provinceExample));
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCity(HttpServletRequest request) {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();
        criteria.andProvinceidEqualTo(Integer.parseInt(request.getParameter("provinceId")));
        return new ResponseJsonObject(true, cityMapper.selectByExample(cityExample));
    }

    @RequestMapping(value = "/county", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCountry(HttpServletRequest request) {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();
        criteria.andCityidEqualTo(Integer.parseInt(request.getParameter("cityId")));
        return new ResponseJsonObject(true, countyMapper.selectByExample(countyExample));
    }

    @RequestMapping(value = "/town", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getTown(HttpServletRequest request) {
        TownExample townExample = new TownExample();
        TownExample.Criteria criteria = townExample.createCriteria();
        criteria.andCountyidEqualTo(Integer.parseInt(request.getParameter("countyId")));
        return new ResponseJsonObject(true, townMapper.selectByExample(townExample));
    }

//    @RequestMapping(value = "/generate")
//    public void generateRegionFile() throws BusinessException {
//        regionService.execute();
//    }

}

