package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.PaymentResult;
import com.happydriving.rockets.server.mapper.PaymentResultMapper;
import com.happydriving.rockets.server.model.CoachProductInfo;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 支付相关公共服务类
 *
 * Created by jasonzhu on 1/7/15.
 */
@Service
public class PaymentPublicService {
    private static final Log LOG = LogFactory.getLog(PaymentPublicService.class);


    @Autowired
    private PublicService publicService;
    @Autowired
    private PaymentResultMapper paymentResultMapper;
    @Autowired
    SmsAuthorizedTools smsAuthorizedTools;





    /**
     * 创建商户订单号（out_trade_no）
     *
     * 目前公式为：8位userId+13位timestamp+3位随机数
     *
     * http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     *
     *
     * @return
     */
    public String createOutTradeNo(String userId)   {
        StringBuilder sb = new StringBuilder();
        sb.append(publicService.padStr(userId, "0", 8))
          .append(publicService.genTimeStampToMilliSec())
          .append(Integer.toString(publicService.genRandomInt(100,999),10));

        return sb.toString();
    }

    /**
     * 统一下单API中签名算法：
     * http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
     *
     * @param items
     * @param APISecret
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public String getSign(Map<String, String> items, String APISecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, String> tmp = new TreeMap<String, String>(items);
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : tmp.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(StringUtils.isEmpty(value)) continue;
            sb.append(key).append("=").append(value).append("&");
        }
        sb.append("key=").append(APISecret);
        LOG.info("String is "+sb.toString());

        return publicService.padStr(new BigInteger(1, MessageDigest.getInstance("MD5").digest(sb.toString().getBytes(CharEncoding.UTF_8))).toString(16).toUpperCase(), "0", 32);
    }



    /**
     * 支付交易单刚生成时，将学员id和购买的产品在数据库中相关联
     *
     * @param xueyuanId
     * @param channel
     * @param coachProductId
     * @param outTradeNo
     */
    public void insertStubToPaymentResultTable(int xueyuanId, PaymentResult.CHANNEL channel, int coachProductId, String outTradeNo) throws BusinessException {
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setXueyuanId(xueyuanId);
        paymentResult.setCoachProductId(coachProductId);
        paymentResult.setChannelEnum(channel);
        paymentResult.setOutTradeNo(outTradeNo);
        paymentResult.setTradeStateEnum(PaymentResult.TRADE_STATE.ONGOING);
        paymentResult.setCreatedAt(new Date());
        paymentResult.setUpdatedAt(new Date());

        try {
            if (paymentResultMapper.insert(paymentResult) <= 0) {
                throw new Exception();
            }
        } catch (Exception e)   {
            LOG.error("insert stub for payment fails. paymentResult=[" + paymentResult + "], err.msg=["+ ExceptionUtils.getStackTrace(e)+"]");
            throw new BusinessException("insert stub for payment fails. paymentResult=[" + paymentResult + "], err.msg=["+ ExceptionUtils.getStackTrace(e)+"]");
        }
    }


    /**
     * 根据教练产品信息，生成支付时展示的名称
     *
     * @param coachProductInfo
     * @return
     */
    public String genPrepayProductName(CoachProductInfo coachProductInfo)   {
        StringBuilder sb = new StringBuilder();
        sb.append(coachProductInfo.getCoachName())
          .append("-")
          .append(coachProductInfo.getClassTypeName());
        return sb.toString();
    }


    /**
     * 发送短信通知教练和学员购买产品成功
     *
     *
     * @param outTradeNo
     * @throws BusinessException
     */
    public void sendSmsToCoachAndXueyuanAfterPayment(String outTradeNo) throws BusinessException {
        LOG.info("Send SMS upon outTradeNo=["+outTradeNo+"]");
        LinkedHashMap<String, Object> phoneInfo = paymentResultMapper.selectUserAndCoachInfoById(outTradeNo);
        smsAuthorizedTools.sendNotifyMessage(phoneInfo.get("coach_phone").toString(), SmsAuthorizedTools.COACH_CHECKIN_NOTIFY);
        smsAuthorizedTools.sendNotifyMessage(phoneInfo.get("xueyuan_phone").toString(), SmsAuthorizedTools.STUDENT_PAY_NOTIFY);
    }



}
