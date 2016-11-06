package com.happydriving.rockets.server.service;


/**
 * Created by jasonzhu on 9/7/15.
 */


import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.UserLocation;
import com.happydriving.rockets.server.mapper.UserLocationMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 微信支付服务类
 */
@Service
public class GeoLocationService {
    private static final Log LOG = LogFactory.getLog(GeoLocationService.class);

    @Autowired
    UserLocationMapper userLocationMapper;


    /**
     * 存储当前用户的位置信息
     *
     * @param openId
     * @param latitude
     * @param longitude
     * @param createTime
     * @throws BusinessException
     */
    public void saveGeoLocation(String openId, BigDecimal latitude, BigDecimal longitude, Date createTime) throws BusinessException {

        UserLocation userLocation = new UserLocation();
        userLocation.setOpenId(openId);
        userLocation.setCreatedAt(createTime);
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);

        //判断是否是已存在用户
        UserLocation user=userLocationMapper.getUserLocationByOpenId(openId);
        //不存在则添加
        try{
            if(user==null){
                LOG.info("insert location");
                userLocationMapper.insert(userLocation);}
            else{
                //存在则更新
                LOG.info("update location");
                userLocationMapper.updateUserLocationByOpenId(userLocation);
            }
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    public UserLocation getUserLocationByOpenId(String openId){
        return userLocationMapper.getUserLocationByOpenId(openId);
    }

}
