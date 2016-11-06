package com.happydriving.rockets.server.utils;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * 微信接入指南中，配置接入URL时微信验证签名的工具类
 *
 * REFERENCE:
 * 1. http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html
 * 2. http://my.oschina.net/u/859716/blog/122824
 *
 * Created by jasonzhu on 9/7/15.
 */

@Service
public class WeixinMessageDigestUtils {
    private MessageDigest alga;

    public WeixinMessageDigestUtils() throws NoSuchAlgorithmException {
        alga = MessageDigest.getInstance("SHA-1");
    }

    public String byte2hex(byte[] b) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public String encipher(String strSrc) {
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        alga.update(bt);
        strDes = byte2hex(alga.digest()); //to HexString
        return strDes;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String signature="b7982f21e7f18f640149be5784df8d377877ebf9";
        String timestamp="1365760417";
        String nonce="1365691777";

        String[] ArrTmp = { "token", timestamp, nonce };
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String pwd =new WeixinMessageDigestUtils().encipher(sb.toString());

        if (signature.equals(pwd)) {
            System.out.println("token 验证成功~!");
        }else {
            System.out.println("token 验证失败~!");
        }
    }

}
