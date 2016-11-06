package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.service.DrivingSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author mazhiqiang
 **/

@Controller
@RequestMapping("/drivingSchool")
public class DrivingSchoolController {

    @Autowired
    private DrivingSchoolService drivingSchoolService;

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getDrivingSchoolByCity(HttpServletRequest request) throws BusinessException {
//        String cityName = request.getParameter("cityName");
//        return new ResponseJsonObject(true, drivingSchoolService.getDrivingSchoolsByCityName(cityName));
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        return new ResponseJsonObject(true, drivingSchoolService.getDrivingSchoolsByCityId(cityId));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject addDrivingSchool(HttpServletRequest request) throws BusinessException {
        String schoolName = request.getParameter("schoolName");
        String address = request.getParameter("address");
        String cityName = request.getParameter("cityName");
        drivingSchoolService.addDrivingSchool(cityName, schoolName, address);
        return new ResponseJsonObject(true);
    }

    @RequestMapping(value = "/detailByCityId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getDrivingSchoolDetailByCityId(HttpServletRequest request) {
        String cityIdParameter = request.getParameter("cityId");
        int cityId = cityIdParameter == null ? 2 : Integer.parseInt(cityIdParameter);
        return new ResponseJsonObject(true, drivingSchoolService.getDrivingSchoolDetailByCityId(cityId));
    }

    @RequestMapping(value = "/detailBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getDrivingSchoolDetailBySchoolId(HttpServletRequest request) {
        int schoolId = Integer.parseInt(request.getParameter("schoolId"));
        return new ResponseJsonObject(true, drivingSchoolService.getDrivingSchoolDetailBySchoolId(schoolId));
    }

    @RequestMapping(value = "/getNearestSchoolByCity", method=RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getNearestSchoolByCity(HttpServletRequest request){
        String cityIdParameter = request.getParameter("cityId");
        int cityId = cityIdParameter == null ? 2 : Integer.parseInt(cityIdParameter);
        return new ResponseJsonObject(true, drivingSchoolService.getNearestSchool(cityId));
    }

    /**
     *
     * @param request cityId,latitude,longitude
     * @return
     */
    @RequestMapping(value ="/getNearestSchoolByLatAndLng", method=RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getNearestSchoolByLatAndLng(HttpServletRequest request){
        String cityIdParameter = request.getParameter("cityId");
        String latitude =request.getParameter("latitude");
        String longitude=request.getParameter("longitude");
        double lat=Double.parseDouble(latitude);
        double lng=Double.parseDouble(longitude);
        int cityId = cityIdParameter == null ? 2 : Integer.parseInt(cityIdParameter);
        return new ResponseJsonObject(true, drivingSchoolService.getNearestSchool(cityId,lat,lng));
    }

    @RequestMapping(value = "/getMajorSchool",method=RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getMajorSchool(HttpServletRequest request){
        String cityIdParameter=request.getParameter("cityId");
        int cityId = cityIdParameter == null ? 2 : Integer.parseInt(cityIdParameter);
        return  new ResponseJsonObject(true,drivingSchoolService.getMajorSchool(cityId));
    }


//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject test() throws BusinessException {
////        drivingSchoolService.addDrivingSchool("福州", "吉诺驾校总院训练场", "福州市鼓楼区后县路");
////        drivingSchoolService.addDrivingSchool("福州", "建安驾校", "新店镇森林公园");
//        drivingSchoolService.addDrivingSchool("福州", "海峡驾校", "仓山区城门镇黄山村福泉高速入口处南侧");
//        drivingSchoolService.addDrivingSchool("福州", "可静驾校", "福建省福州市仓山区上三路113之6-8");
//        drivingSchoolService.addDrivingSchool("福州", "康达驾校", "福州仓山区首山路80号");
//        drivingSchoolService.addDrivingSchool("福州", "闽峰驾校", "福州市晋安区前屿东路262");

//    drivingSchoolService.addDrivingSchool("厦门", "露名泉驾校", "湖里，马垅公交站总站旁");
//        drivingSchoolService.addDrivingSchool("厦门", "双寅驾校", "厦门市湖里区湖里大道");
//        drivingSchoolService.addDrivingSchool("厦门", "通凯驾校", "厦门市集美区杏林杏前路114号之三");
//        drivingSchoolService.addDrivingSchool("厦门", "鑫良友驾校", "厦门市同安区阳翟路");
//        drivingSchoolService.addDrivingSchool("厦门", "鑫双榕驾校", "厦门市同安区外较场路");
//        drivingSchoolService.addDrivingSchool("厦门", "阳齐驾校", "厦门市湖里区江头北路148号（天地花园公交车站旁）");
//        return new ResponseJsonObject(true);
//    }


}
