package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.alipay.config.AlipayConfig;
import com.happydriving.rockets.server.component.alipay.util.AlipaySubmit;
import com.happydriving.rockets.server.entity.DrivingSchoolDetail;
import com.happydriving.rockets.server.entity.PaymentResult;
import com.happydriving.rockets.server.model.CoachProductInfo;
import com.happydriving.rockets.server.model.DrivingSchoolDetailInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jasonzhu on 30/6/15.
 */



@Service
public class AliPaymentService {
    private static final Log LOG = LogFactory.getLog(AliPaymentService.class);;

    @Autowired
    private PublicService publicService;
    @Autowired
    private PaymentPublicService paymentPublicService;

    /**
     * 填充测试参数，生成付款链接URL
     * @param request
     * @return
     */
    public String getPayRequestUrl(HttpServletRequest request, String outTradeNo,DrivingSchoolDetailInfo schooldetail) throws BusinessException {
        //--assemble parameters--
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", "1"); //参考：即时到账交易接口(create_direct_pay_by_user) -> 11.6 收款类型
        sParaTemp.put("notify_url", publicService.getCurrentDomain(request)+"/alipay/notify"); //付款成功后alipay主动发起的通知，一定会调用
        //sParaTemp.put("return_url", publicService.getCurrentDomain(request)+"/alipay/return"); //付款成功后redirect到的页面，可能被用户强行关闭，不保证一定会调用到
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("subject", "test_product"); //商品名称
        sParaTemp.put("total_fee", schooldetail.getPrice().divide(new BigDecimal(100)).toString()); //交易金额, 单位：元
//        sParaTemp.put("total_fee","0.01"); //交易金额, 单位：元
        //sParaTemp.put("body", "测试描述咯test"); //商品描述
        sParaTemp.put("exter_invoke_ip", publicService.getClientIpAddr(request)); //客户端 IP


        //--建立请求--
        return AlipaySubmit.buildRequestUrl(sParaTemp);
    }





}
