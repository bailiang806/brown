package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Created by jasonzhu on 18/6/15.
 */


/**
 * 公共服务类
 */
@Service
public class PublicService {

    /**
     * 获取终端用户IP
     *
     * @param request
     * @return
     */
    public String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前网站的域名
     *
     * @param request
     * @return
     */
    public String getCurrentDomain(HttpServletRequest request)  {
        if(request.getServerPort() == 80)   {
            return String.format("%s://%s",request.getScheme(),  request.getServerName());
        }
        return String.format("%s://%s:%d",request.getScheme(),  request.getServerName(), request.getServerPort());
    }

    /**
     * 判断当前str是否为一个数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * 时间戳（精确到秒）
     * @return
     */
    public String genTimeStampToSec()   {
        return Long.toString(System.currentTimeMillis()/1000L, 10);
    }

    /**
     * 时间戳（精确到毫秒）
     * @return
     */
    public String genTimeStampToMilliSec()   {
        return Long.toString(System.currentTimeMillis(), 10);
    }

    /**
     * 创建int随机数
     *
     * @param from inclusive
     * @param to exclusive
     * @return
     */
    public int genRandomInt(int from, int to)   {
        Random r = new Random();
        return r.nextInt(to-from) + from;
    }


    /**
     * 字符串前补位
     *
     * @param str 字符串
     * @param pad 补充字符
     * @param len 总长
     * @return
     */
    public String padStr(String str, String pad, int len) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len-str.length(); ++i)   {
            sb.append(pad);
        }
        sb.append(str);
        return sb.toString();
    }


    /**
     * 简单的GET请求
     *
     * @param url
     * @return
     */
    public String simpleGetRequest(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        return IOUtils.toString(con.getInputStream());
    }


    /**
     *
     * 创建指定长度的随机数
     *
     * @return
     */
    public String createNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)  {
            sb.append(chars.charAt((int)(Math.random()*chars.length())));
        }
        return sb.toString();
    }

    public String createNoncestr()  {
        return createNoncestr(32);
    }







}
