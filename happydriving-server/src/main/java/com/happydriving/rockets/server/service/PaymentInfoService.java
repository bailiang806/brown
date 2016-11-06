package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.PaymentInfo;
import com.happydriving.rockets.server.entity.PaymentInfoExample;
import com.happydriving.rockets.server.mapper.PaymentInfoMapper;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gaoc10 on 2015/10/23 0023.
 */
@Service
public class PaymentInfoService {
    private static final Log LOG = LogFactory.getLog(PaymentPublicService.class);

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    public void addPaymentInfo(String orderId,String phone,String name,String schoolId,BigDecimal price,String tradeState) throws BusinessException {
        PaymentInfo paymentInfo=new PaymentInfo();
        paymentInfo.setOrderId(orderId);
        paymentInfo.setPhone(phone);
        paymentInfo.setName(name);
        paymentInfo.setSchoolId(schoolId);
        paymentInfo.setPrice(price);
        paymentInfo.setTradeState(tradeState);
        try{
        paymentInfoMapper.insert(paymentInfo);}
        catch(Exception e){
            LOG.error("insert paymentInfo fails. paymentResult=[" + phone+" "+name+" "+schoolId+" " +" "+price.toString()+" "+ "], err.msg=["+ ExceptionUtils.getStackTrace(e)+"]");
            throw new BusinessException("insert paymentInfo fails. paymentResult=[" + phone+" "+name+" "+schoolId+" " +" "+price.toString()+" "+ "], err.msg=["+ ExceptionUtils.getStackTrace(e)+"]");

        }
    }

    public void updateOutTradeNoByOrderId(String orderId,String outTradeNo) throws BusinessException {
        PaymentInfoExample example=new PaymentInfoExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        PaymentInfo paymentInfo=paymentInfoMapper.selectByExample(example).get(0);
        paymentInfo.setOutTradeNo(outTradeNo);
        try{paymentInfoMapper.updateByPrimaryKeySelective(paymentInfo);}catch (Exception e){
            LOG.error("update outTradeNo fails.["+"orderId:"+orderId+"outTradeNo:"+outTradeNo+"],err.msg=["+ExceptionUtils.getStackTrace(e)+"]");
            throw new BusinessException("update outTradeNo fails.["+"orderId:"+orderId+"outTradeNo:"+outTradeNo+"],err.msg=["+ExceptionUtils.getStackTrace(e)+"]");
        }
    }

    public void updateTradeStateAndThirdPartyTradeNo(String tradeState,String thirdPartyTradeNo,String outTradeNo) throws BusinessException {
        PaymentInfoExample paymentInfoExample=new PaymentInfoExample();
        paymentInfoExample.createCriteria().andOutTradeNoEqualTo(outTradeNo);
        PaymentInfo paymentInfo=paymentInfoMapper.selectByExample(paymentInfoExample).get(0);
        LOG.info("paymentInfo outTradeNo"+paymentInfo.getOutTradeNo());
        paymentInfo.setTradeState(tradeState);
        paymentInfo.setThirdPartyTradeNo(thirdPartyTradeNo);
        try{
        paymentInfoMapper.updateByPrimaryKeySelective(paymentInfo);}
        catch (Exception e){
            LOG.error("update tradeState and ThirdePartyTrade error.["+"tradeState:"+tradeState+"thirdPartyTradeNo:"+thirdPartyTradeNo
            +"outTradeNo:"+outTradeNo+"],err.msg=["+ExceptionUtils.getStackTrace(e)+"]");
            throw new BusinessException("update tradeState and ThirdePartyTrade error.["+"tradeState:"+tradeState+"thirdPartyTradeNo:"+thirdPartyTradeNo
                    +"outTradeNo:"+outTradeNo+"],err.msg=["+ExceptionUtils.getStackTrace(e)+"]");
        }
    }

    public PaymentInfo getPaymentInfoByOrderId(String orderId){
        PaymentInfoExample example=new PaymentInfoExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<PaymentInfo> paymentInfos=paymentInfoMapper.selectByExample(example);
        return paymentInfos.isEmpty()?null:paymentInfos.get(0);
    }
}

