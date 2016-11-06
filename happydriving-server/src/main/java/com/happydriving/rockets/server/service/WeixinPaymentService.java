package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.mapper.PaymentInfoMapper;
import com.happydriving.rockets.server.mapper.PaymentResultMapper;
import com.happydriving.rockets.server.model.CoachProductInfo;
import com.happydriving.rockets.server.model.DrivingSchoolDetailInfo;
import com.happydriving.rockets.server.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by jasonzhu on 18/6/15.
 */


/**
 * 微信支付服务类
 */
@Service
public class WeixinPaymentService {
    private static final Log logger = LogFactory.getLog(WeixinPaymentService.class);

    @Autowired
    private PublicService publicService;
    @Autowired
    private PaymentPublicService paymentPublicService;
    @Autowired
    private PaymentResultMapper paymentResultMapper;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private WeixinOauthService weixinOauthService;
    @Autowired
    private PaymentInfoService paymentInfoService;
    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private MailSender mailSender;




    /**
     *
     * 获取openid
     *
     * 直接在后台请求对应的API即可，返回的json串中为类似如下格式的内容：
     *
     * {
     *   "access_token": "OezXcEiiBSKSxW0eoylIeGhaJjUxzVpRR4o6hX-jAhOn160_GRNWPwzcWR_QSO4gbjzWHPV6zuNazuJp3spc2gptHLcR-g2QetMKeDGZ3IJD6PbJCf2YKyw6k4aeiFbdJgfJgNBXKfZ0dPb98IKR_w",
     *   "expires_in": 7200,
     *   "refresh_token": "OezXcEiiBSKSxW0eoylIeGhaJjUxzVpRR4o6hX-jAhOn160_GRNWPwzcWR_QSO4g7r7Y2BQy_p7bmrjxH8YN3scFXn7C4fUnNn9AFDcz_qW5ErAi4Lp9p18PcLv60yUtOBSwd8MfDIKap12lVExOAg",
     *   "openid": "ogGCluNRaxBTNFWZzS_kH-rRez_Q",
     *   "scope": "snsapi_base"
     * }
     *
     * 其中包含openid。
     *
     *
     * @param code
     * @return
     * @throws IOException
     */
    public String getOpenId(String code) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+weixinOauthService.appId+"&secret="+weixinOauthService.AppSecret+"&code="+code+"&grant_type=authorization_code").openConnection();
        con.setRequestMethod("GET");
        String jsonStrWithOpenId = IOUtils.toString(con.getInputStream());
        //logger.info("JUDKING_DEBUG. access_token_related_info=["+jsonStrWithOpenId+"]");
        String openId = ((JSONObject) JSON.parse(jsonStrWithOpenId)).getString("openid");
        if(StringUtils.isEmpty(openId)) {
            logger.error("no openid retrieved. json=[" + jsonStrWithOpenId + "]");
        }
        return openId;
    }


    /**
     * 获取prepayId
     *
     * Example:
     *
     * 请求参数：
     * <xml>
     *     <appid><![CDATA[wx1e810861e20c0c30]]></appid>
     *     <attach><![CDATA[payment_test]]></attach>
     *     <body><![CDATA[JSAPI_payment_test]]></body>
     *     <mch_id>1242315302</mch_id>
     *     <nonce_str><![CDATA[6aghlqz18duhfebole531dce0r7bw0td]]></nonce_str>
     *     <notify_url><![CDATA[http://test.ejiapei.com/happydriving/wxpay/test1]]></notify_url>
     *     <openid><![CDATA[ogGCluNRaxBTNFWZzS_kH-rRez_Q]]></openid>
     *     <out_trade_no><![CDATA[nraxbtnfwzzskhrrezq1434590817259]]></out_trade_no>
     *     <spbill_create_ip><![CDATA[119.161.230.131]]></spbill_create_ip>
     *     <total_fee>1</total_fee>
     *     <trade_type><![CDATA[JSAPI]]></trade_type>
     *     <sign><![CDATA[F415B11A1C1B4894085FD703CBD14B71]]></sign>
     *     </xml>
     *
     * 返回值：
     * <xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx1e810861e20c0c30]]></appid><mch_id><![CDATA[1242315302]]></mch_id><nonce_str><![CDATA[amxU3MOLatSWVzua]]></nonce_str><sign><![CDATA[E458BE2C4F23C6F22B7561E74F41DEEF]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201506180927207ee0b107300739613144]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>
     *
     * @return
     * @throws IOException
     */
    public String getPrepayId(String outTradeNo, String openId, String remoteIp, String currentDomain,DrivingSchoolDetailInfo schooldetail) throws IOException, ParserConfigurationException, SAXException {
        //--generate xml request data.--
        String xmlRequestData = assemblePrepayXmlRequest(outTradeNo, weixinOauthService.appId, weixinOauthService.mchId, openId, remoteIp, currentDomain,schooldetail);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String xmlResult = httpUtils.postXml(url, xmlRequestData);

        logger.info("xmlResult is "+xmlResult);

        //--extract prepayId from xml result--
        String prepayId = httpUtils.xmlToMap(xmlResult).get("prepay_id");
        if(StringUtils.isEmpty(prepayId))   {
            logger.error("no prepayId retrieved. xmlRequestData=[" + xmlRequestData + "], xmlResult=[" + xmlResult + "]");
        }

        return prepayId;
    }

    /**
     * 生成公众号支付接口所使用的jsapi调起支付的所有参数，返回给前端。
     * http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7
     *
     * @param prepayId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public JSONObject assemblePayRequestParams(String prepayId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> sortedByKeyMaps = new TreeMap<>();
        sortedByKeyMaps.put("appId", weixinOauthService.appId);
        sortedByKeyMaps.put("timeStamp", publicService.genTimeStampToSec());
        sortedByKeyMaps.put("nonceStr", publicService.createNoncestr());
        sortedByKeyMaps.put("package", "prepay_id="+prepayId);
        sortedByKeyMaps.put("signType", "MD5");

        String sign = paymentPublicService.getSign(sortedByKeyMaps, weixinOauthService.APISecret);
        sortedByKeyMaps.put("paySign", sign);

        JSONObject rtn = (JSONObject) JSON.toJSON(sortedByKeyMaps);
        return rtn;

    }


    /**
     * 微信支付成功后回调逻辑
     *
     * REFERENCE:
     * 1. 支付结果回调通知API： https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     * 2. 查询订单状态API：     https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     *
     * @param xmlParam
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws NoSuchAlgorithmException
     */
    public void updatewxpaynotify(String xmlParam) throws IOException, SAXException, ParserConfigurationException, NoSuchAlgorithmException, BusinessException {
        Map<String, String> xmlItems = httpUtils.xmlToMap(xmlParam);


        for(String items :xmlItems.keySet()){
            logger.info("xmlItemskey id "+items +"  xmlItemsvalue is "+xmlItems.get(items));
        }

        /////查询订单////////
        //--generate xml request data.--
        Map<String, String> requestItems = new HashMap<>(4);
        requestItems.put("appid", xmlItems.get("appid"));
        requestItems.put("mch_id", xmlItems.get("mch_id"));
        requestItems.put("nonce_str", xmlItems.get("nonce_str"));
        requestItems.put("transaction_id", xmlItems.get("transaction_id"));
//        requestItems.put("out_trade_no", xmlItems.get("out_trade_no"));


        String xmlRequestData = genSignAndReturnXmlString(requestItems);
        logger.info("xmlRequestData is "+xmlRequestData);

        //--post xml request data to correspoinding API via post request.--
        String orderQueryApi = "https://api.mch.weixin.qq.com/pay/orderquery";
        String xmlResult = httpUtils.postXml(orderQueryApi, xmlRequestData);
        logger.info("callback xmlResult is "+xmlResult);

        Map<String, String> returnItems = httpUtils.xmlToMap(xmlResult);

        //如果订单支付成功
        if(StringUtils.equals(returnItems.get("return_code"), "SUCCESS") &&
           StringUtils.equals(returnItems.get("result_code"), "SUCCESS") &&
           StringUtils.equals(returnItems.get("trade_state"), "SUCCESS"))   {

//         if(xmlItems.get("return_code").equals("SUCCESS")&&){
            String out_trade_no = returnItems.get("out_trade_no");

            logger.info("callback out_trade_no is" + returnItems.get("out_trade_no"));

            //更新预订单状态信息

            paymentInfoMapper.updatePaymentByOutTradeNo(out_trade_no,returnItems.get("transaction_id"));


//           // paymentInfoService.updateTradeStateAndThirdPartyTradeNo("success",returnItems.get("transaction_id"),out_trade_no);
//            PaymentInfoExample paymentInfoExample=new PaymentInfoExample();
//            paymentInfoExample.createCriteria().andOutTradeNoEqualTo(out_trade_no);
//            PaymentInfo paymentInfo=paymentInfoMapper.selectByExample(paymentInfoExample).get(0);
//            logger.info("paymentInfo outTradeNo" + paymentInfo.getOutTradeNo());
//            paymentInfo.setTradeState("success");
//            paymentInfo.setThirdPartyTradeNo(returnItems.get("transaction_id"));
//            try{
//                paymentInfoMapper.updateByPrimaryKeySelective(paymentInfo);}
//            catch (Exception e){
//                logger.error("update tradeState and ThirdePartyTrade error.[" + "tradeState:" + "success" + "thirdPartyTradeNo:" + returnItems.get("transaction_id")
//                        + "outTradeNo:" + out_trade_no + "],err.msg=[" + ExceptionUtils.getStackTrace(e) + "]");
//                throw new BusinessException("update tradeState and ThirdePartyTrade error.["+"tradeState:"+"success"+"thirdPartyTradeNo:"+returnItems.get("transaction_id")
//                        +"outTradeNo:"+out_trade_no+"],err.msg=["+ExceptionUtils.getStackTrace(e)+"]");
//            }



            //给管理者发短信

            logger.info("update status is ok");
            mailSender.sendMail("订单号:" + out_trade_no+" 的订单 付款成功","success");
            smsAuthorizedTools.sendPaymentMessage("18612031554", out_trade_no);
//            //更新payment_result表的交易状态为交易成功
//            PaymentResult paymentResult = new PaymentResult();
//            paymentResult.setOutTradeNo(out_trade_no);

//            paymentResult = paymentResultMapper.getTradeState(paymentResult);
//            if(paymentResult == null)   {
//                throw new BusinessException("No out_trade_no found. returnItems=["+returnItems+"]");
//            }
//
//            //----如果当前数据库中交易状态为ongoing，才notify----
//            if (paymentResult.getTradeState() == PaymentResult.TRADE_STATE.ONGOING.getValue()) {
//                //更新交易状态为success
//                paymentResult.setThirdPartyTradeNo(xmlItems.get("transaction_id"));
//                paymentResult.setTradeStateEnum(PaymentResult.TRADE_STATE.PAYED_SUCCESS);
//                if (paymentResultMapper.updateTradeState(paymentResult) <= 0) {
//                    throw new BusinessException("update trade_state fails. paymentResult=[" + paymentResult + "]");
//                }
//
//                //给教练和学员发送短信
//                paymentPublicService.sendSmsToCoachAndXueyuanAfterPayment(out_trade_no);
//            }
        }

    }



    /**
     * 组装统一下单API所需的XML请求信息
     * http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * http://pay.weixin.qq.com/wiki/doc/api/index.php?chapter=7_7
     *
     * @return
     */
    private String assemblePrepayXmlRequest(String outTradeNo, String appId, String mchId, String openId, String remoteIp, String currentDomain,DrivingSchoolDetailInfo schooldetail)  {
        try {
            Map<String, String> items = new LinkedHashMap<String, String>();

            // append child elements to root element
            items.put("appid", appId);
//          items.put("body", paymentPublicService.genPrepayProductName(coachProductInfo));
            items.put("body", "报名驾校支付金额");
            items.put("mch_id", mchId);
            items.put("nonce_str", publicService.createNoncestr());
            items.put("notify_url", currentDomain+"/wxpay/notify");
            items.put("openid", openId);
            items.put("out_trade_no", outTradeNo);
            items.put("spbill_create_ip", remoteIp);
            items.put("total_fee",schooldetail.getPrice().toString()); //单位：分
            items.put("trade_type", "JSAPI");

            // output DOM XML to console
            return genSignAndReturnXmlString(items);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 根据xml中的项，按照微信签名算法，生成对应的请求xml字符串. (非幂等。会多一项: sign)
     * 签名算法API：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
     *
     * @param items
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String genSignAndReturnXmlString(Map<String, String> items) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String sign = paymentPublicService.getSign(items, weixinOauthService.APISecret);
        items.put("sign", sign);
        return httpUtils.mapToXml(items);
    }


//    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        WeixinPaymentService weixinPaymentService = new WeixinPaymentService();
//        weixinPaymentService.publicService = new PublicService();
//        System.out.println(weixinPaymentService.assemblePayRequestParams("prepayId").toJSONString());
//    }

}
