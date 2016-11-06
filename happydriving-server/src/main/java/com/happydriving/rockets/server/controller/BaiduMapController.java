package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.service.BaiduMapService;
import com.happydriving.rockets.server.service.ServiceCityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有的地图相关服务发布
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/map")
public class BaiduMapController {

    @Autowired
    private BaiduMapService baiduMapService;

    @Autowired
    private ServiceCityService serviceCityService;

    private static final Log logger = LogFactory.getLog(WeixinAccessController.class);


    /**
     * 根据经纬度得到对应的城市名称，由于百度地图中的城市名称为 "福州市" "杭州市" 的格式，
     *
     * 需要将service_city数据表中的city数据调整成相应的格式
     *
     * @param request
     * @return － 如果城市并不在服务城市列表中，将返回null结果，以表明暂时不支持该城市
     * @throws BusinessException
     */
    @RequestMapping(value = "/getCity", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCityNameFromLocation(HttpServletRequest request) throws BusinessException {
        float lag = Float.parseFloat(request.getParameter("lag"));
        float lng = Float.parseFloat(request.getParameter("lng"));

//        float lag = Float.parseFloat("26.041971");
//        float lng = Float.parseFloat("119.322960");

        logger.info("lag is :"+lag+"  lng is "+lng);

        String cityName = baiduMapService.getCityFromLocation(lag, lng);
        ServiceCity city = serviceCityService.getCityByName(cityName);

        logger.info(city.getId()+"    "+city.getName());

        ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
        responseJsonObject.setReturnObject(city);
        return responseJsonObject;
    }
}
