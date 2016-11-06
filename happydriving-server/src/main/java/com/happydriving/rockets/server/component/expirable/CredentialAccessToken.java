package com.happydriving.rockets.server.component.expirable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.service.PublicService;
import com.happydriving.rockets.server.service.WeixinOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * 获取公众号调用各接口时都需要使用的普通access_token（区别于WeixinOauthService中获取用户信息时使用的oauth access_token）
 *
 * 接口文档：
 * http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html
 *
 * Created by jasonzhu on 21/7/15.
 */
@Component
public class CredentialAccessToken extends Expirable<String> {


    @Autowired
    private PublicService publicService;
    @Autowired
    private WeixinOauthService weixinOauthService;

    @Override
    public String getValue() throws IOException {
        if(deadAlready())   {
            String wxCredentialAccessTokenAPI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weixinOauthService.appId + "&secret=" + weixinOauthService.AppSecret;
            JSONObject rtn = ((JSONObject) JSON.parse(publicService.simpleGetRequest(wxCredentialAccessTokenAPI)));

            this.value = rtn.getString("access_token");
            this.createTime = System.currentTimeMillis()/1000;
            this.survivalSpan = rtn.getLongValue("expires_in");
        }
        return this.value;
    }
}
