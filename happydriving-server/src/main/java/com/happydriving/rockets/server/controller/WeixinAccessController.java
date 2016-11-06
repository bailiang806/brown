package com.happydriving.rockets.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.expirable.JsApiTicket;
import com.happydriving.rockets.server.entity.UserLocation;
import com.happydriving.rockets.server.service.GeoLocationService;
import com.happydriving.rockets.server.service.PublicService;
import com.happydriving.rockets.server.service.WeixinAccessService;
import com.happydriving.rockets.server.service.WeixinOauthService;
import com.happydriving.rockets.server.utils.WeixinMessageDigestUtils;
import com.happydriving.rockets.server.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 微信接入URL统一入口
 * <p/>
 * 微信公众平台 -> 开发者中心 -> 服务器配置
 * http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html
 * <p/>
 * <p/>
 * Created by jasonzhu on 8/7/15.
 */
@Controller
@RequestMapping("/wxaccess")
public class WeixinAccessController {
    private static final Log logger = LogFactory.getLog(WeixinAccessController.class);

    @Autowired
    HttpUtils httpUtils;
    @Autowired
    WeixinOauthService weixinOauthService;
    @Autowired
    WeixinMessageDigestUtils weixinMessageDigestUtil;
    @Autowired
    GeoLocationService geoLocationService;
    @Autowired
    WeixinAccessService weixinAccessService;
    @Autowired
    PublicService publicService;
    @Autowired
    JsApiTicket jsApiTicket;

    /**
     * 开发者入口URL。
     * 所有事件推送+用户消息均会发送至此接口
     * <p/>
     * 事件推送API：https://mp.weixin.qq.com/wiki/2/5baf56ce4947d35003b86a9805634b1e.html
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @RequestMapping(value = "/portal", method = RequestMethod.POST)
    public @ResponseBody String portal_post(HttpServletRequest request, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException, BusinessException {
        String xml = IOUtils.toString(request.getInputStream(), CharEncoding.UTF_8);
        Map<String, String> xmlParams = httpUtils.xmlToMap(xml);
        logger.info("JUDKING_DEBUG. xmlParams=["+xmlParams+"]");

        String msgType = xmlParams.get("MsgType");
        String event = xmlParams.get("Event");
        String openId="";

        if (StringUtils.equals("event", msgType)) {
            //地理位置推送信息存储到user_location表
            if (StringUtils.equals("LOCATION", event)) {
                openId = xmlParams.get("FromUserName");
                BigDecimal latitude =
                        new BigDecimal(xmlParams.get("Latitude"), new MathContext(9, RoundingMode.HALF_UP));
                BigDecimal longitude =
                        new BigDecimal(xmlParams.get("Longitude"), new MathContext(9, RoundingMode.HALF_UP));
                Date createTime = new Date(Long.valueOf(xmlParams.get("CreateTime") + "000", 10));
                geoLocationService.saveGeoLocation(openId, latitude, longitude, createTime);
            }
        }
        return "";
    }


    /**
     * 开发者接入指南中对应的接入URL验证接口：
     * <p/>
     * http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html
     * http://my.oschina.net/u/859716/blog/122824
     *
     * @param request
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @RequestMapping(value = "/portal", method = RequestMethod.GET)

    @ResponseBody
    public String portal_get(HttpServletRequest request) throws IOException, ParserConfigurationException, SAXException {

        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");

        if (StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce) || StringUtils.isBlank(signature)) {
            logger.error("error_params. timestamp=[" + timestamp + "], nonce=[" + nonce + "], signature=[" + signature + "]");
            return "error params";
        }

        String[] params = {weixinOauthService.token, timestamp, nonce};
        Arrays.sort(params);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i]);
        }

        String pwd = weixinMessageDigestUtil.encipher(sb.toString());
        if (StringUtils.equals(pwd, signature)) {
            return request.getParameter("echostr");
        } else {
            logger.error("error_signature. timestamp=[" + timestamp + "], nonce=[" + nonce + "], signature=[" + signature + "], echostr=[" + request.getParameter("echostr") + "]");
            return "error signature";
        }
    }


    /**
     * 创建自定义菜单
     *
     * (!!!!!轻易不使用，所以comment out!!!!)
     *
     * 微信接口文档:
     * http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html
     *
     * 可以直接用下面的在线调试接口完成自定义菜单的创建、查询、删除操作
     * https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95&form=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3%20/menu/create
     *
     * @param request
     * @return
     * @throws IOException
     */
    //#############
    //
    // 勿删!!!!!!!!
    //
    //#############
//    @RequestMapping(value = "/createMenu", method = RequestMethod.GET)
//    public @ResponseBody String createMenu(HttpServletRequest request) throws IOException {
//        JSONObject credentialAccessTokenRelated = weixinAccessService.getCredentialAccessToken();
//        logger.info("JUDKING_DEBUG. credentialAccessTokenRelated=["+credentialAccessTokenRelated+"]");
//
//        String api = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", credentialAccessTokenRelated.getString("access_token"));
//
//        String strJsonParam = " {\n" +
//                "     \"button\":[\n" +
//                "     {\n" +
//                "          \"type\":\"click\",\n" +
//                "          \"name\":\"进入首页\",\n" +
//                "          \"key\":\"MENU_1001_PORTAL\"\n" +
//                "     }]\n" +
//                " }";
//
//        String response = httpUtils.postJson(api, strJsonParam);
//        logger.info(String.format("createMenu_response=[%s]", response));
//
//        return response;
//    }


    /**
     * 获取JSJDK.config所需参数
     *
     * http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E6.AD.A5.E9.AA.A4.E4.B8.89.EF.BC.9A.E9.80.9A.E8.BF.87config.E6.8E.A5.E5.8F.A3.E6.B3.A8.E5.85.A5.E6.9D.83.E9.99.90.E9.AA.8C.E8.AF.81.E9.85.8D.E7.BD.AE
     *
     * @param request
     * @return
     * @throws IOException
     */
//    @RequestMapping(value = "/JSJDKCfg", method = RequestMethod.GET)
//    public ModelAndView jsjdkCfg(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setHeader("Cache-Control", "no-cache");
//
//        String appId        = weixinOauthService.appId;
//        String timestamp    = publicService.genTimeStampToSec();
//        String noncestr     = publicService.createNoncestr();
//        String jsapi_ticket = jsApiTicket.getValue();
//        String url          = publicService.getCurrentDomain(request)+"/wxaccess/JSJDKCfg"+"?nouse="+request.getParameter("nouse"); //nouse参数为随机数，保证页面不被缓存
////      String url          = publicService.getCurrentDomain(request)+"/wxaccess/JSJDKCfg?nouse="+Math.random()*100; //nouse参数为随机数，保证页面不被缓存
//
//
//        String signature    = weixinAccessService.genSignature4JSSDK(noncestr, jsapi_ticket, timestamp, url);
//
//        JSONObject rtn = new JSONObject();
//        rtn.put("appId", appId);
//        rtn.put("timestamp", timestamp);
//        rtn.put("nonceStr", noncestr);
//        rtn.put("signature", signature);
//
//
//        logger.info("JUDKING_DEBUG. ticket=["+jsapi_ticket+"], uri=["+url+"] rtn=["+rtn+"]");
//
//        ModelAndView modelAndView = new ModelAndView("drivingMap");
//        modelAndView.addObject("appId", appId);
//        modelAndView.addObject("timestamp", timestamp);
//        modelAndView.addObject("nonceStr", noncestr);
//        modelAndView.addObject("signature", signature);
//
//
//
//        return modelAndView;
//    }


    @RequestMapping(value = "/JSJDKCfg", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject jsjdkCfg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");

        String appId        = weixinOauthService.appId;
        String timestamp    = publicService.genTimeStampToSec();
        String noncestr     = publicService.createNoncestr();
        String jsapi_ticket = jsApiTicket.getValue();
//        String url          = publicService.getCurrentDomain(request)+"/wxaccess/JSJDKCfg"+"?nouse="+request.getParameter("nouse"); //nouse参数为随机数，保证页面不被缓存
        String url          = publicService.getCurrentDomain(request)+"/index_wechat.html";
        logger.info("url is "+url);

        String signature    = weixinAccessService.genSignature4JSSDK(noncestr, jsapi_ticket, timestamp, url);

        JSONObject rtn = new JSONObject();
        rtn.put("appId", appId);
        rtn.put("timestamp", timestamp);
        rtn.put("nonceStr", noncestr);
        rtn.put("signature", signature);

        return rtn;
    }



//
//    @RequestMapping(value = "/getopenID", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject getopenID(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String openId=String.valueOf(request.getSession().getAttribute("openId")==null?"":request.getSession().getAttribute("openId"));
//        logger.info("网站open id is from session  "+openId);
//        JSONObject returnobj=new JSONObject();
//        returnobj.put("openId",openId);
//        return returnobj;
//    }

    @RequestMapping(value = "/indexpage", method = RequestMethod.GET)
    public ModelAndView indextest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        ModelAndView modelAndView = new ModelAndView("indexpage");

        return modelAndView;
    }

}