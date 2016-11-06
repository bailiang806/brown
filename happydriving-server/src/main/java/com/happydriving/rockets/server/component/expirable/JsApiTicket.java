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
 * JSJDK配置config时生成签名所需的jsapi_ticket
 *
 * API: http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
 *
 *
 * Created by jasonzhu on 21/7/15.
 */
@Component
public class JsApiTicket extends Expirable<String> {


    @Autowired
    private PublicService publicService;
    @Autowired
    private CredentialAccessToken credentialAccessToken;

    @Override
    public String getValue() throws IOException {
        if(deadAlready())   {
            String wxJsApiAPI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+credentialAccessToken.getValue()+"&type=jsapi";
            JSONObject rtn = ((JSONObject) JSON.parse(publicService.simpleGetRequest(wxJsApiAPI)));

            this.value = rtn.getString("ticket");
            this.createTime = System.currentTimeMillis()/1000;
            this.survivalSpan = rtn.getLongValue("expires_in");
        }
        return this.value;
    }
}
