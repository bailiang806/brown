package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.UserBridge;
import com.happydriving.rockets.server.entity.UserBridgeExample;
import com.happydriving.rockets.server.mapper.UserBridgeMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jasonzhu on 18/6/15.
 */


/**
 * 微信公众号回调URL入口服务类
 */
@Service
public class WeixinAccessService {
    private static final Log logger = LogFactory.getLog(WeixinAccessService.class);

    @Autowired
    PublicService publicService;


    public String genSignature4JSSDK(String noncestr, String jsapi_ticket, String timestamp, String url)   {
        TreeMap<String, String> sortedMap = new TreeMap<String, String>();
        sortedMap.put("noncestr", noncestr);
        sortedMap.put("jsapi_ticket", jsapi_ticket);
        sortedMap.put("timestamp", timestamp);
        sortedMap.put("url", url);

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(StringUtils.isEmpty(value)) continue;
            sb.append(key).append("=").append(value).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return publicService.padStr(DigestUtils.shaHex(sb.toString()), "0", 40);
    }


    /**
     * 自定义菜单项对应的key
     */
    public static enum MENU_KEY    {
        MENU_1001_PORTAL;   //一级菜单项：进入首页
    }

}
