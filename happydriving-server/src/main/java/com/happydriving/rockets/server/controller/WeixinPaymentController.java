package com.happydriving.rockets.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.PaymentInfo;
import com.happydriving.rockets.server.mapper.CoachProductMapper;
import com.happydriving.rockets.server.mapper.PaymentResultMapper;
import com.happydriving.rockets.server.model.DrivingSchoolDetailInfo;
import com.happydriving.rockets.server.service.*;
import com.happydriving.rockets.server.utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 * 微信支付相关
 *
 * Created by jasonzhu on 17/6/15.
 */

@Controller
@RequestMapping("/wxpay")
public class WeixinPaymentController {
    private static final Log LOG = LogFactory.getLog(WeixinPaymentController.class);

    @Autowired
    private WeixinPaymentService weixinPaymentService;
    @Autowired
    private PublicService publicService;
    @Autowired
    private PaymentPublicService paymentPublicService;
    @Autowired
    PaymentResultMapper paymentResultMapper;
    @Autowired
    CoachProductMapper coachProductMapper;
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    DrivingSchoolService drivingSchoolService;
    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 微信支付入口API中redirect_uri参数指向的后台逻辑portal
     *
     * 发起微信支付步骤：
     * 1. 获取code                         （OAuth授权）
     * 2. 根据code获取openid               （OAuth授权）
     * 3. 使用统一下单接口，获取prepay_id    （统一下单接口）
     * 4. 使用jsapi调起支付                 （公众号支付接口）
     *
     *
     * TODO json4zhu: 将微信支付中获取openId前面一段内容解放出来, 与WeixinOauth结合
     *
     * 相关API：
     * 1. OAuth授权：      http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     * 2. 统一下单接口：    http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     * 3. 公众号支付接口：  http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7
     *
     *
     * @param request
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     */
//    @RequestMapping(value = "/payModelAndView", method = RequestMethod.GET)
//    public ModelAndView wxPayModelAndView(HttpServletRequest request) throws BusinessException, UnsupportedEncodingException {
//        try {
//            //--userId--
//            int userId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString(), 10);
//
//            //--coachProductId--
//            if (StringUtils.isEmpty(request.getParameter("coach_product_id"))) {
//                LOG.error("no coach_product_id retrieved.");
//                throw new BusinessException("no coach_product_id retrieved");
//            }
//            int coachProductId = Integer.parseInt(request.getParameter("coach_product_id"), 10);
//
//            //--生成coach_product详细信息--
//            CoachProductInfo coachProductInfo = coachProductMapper.getCoachProductDetailById(coachProductId);
//
//            /**
//             * Retrieve 'code'
//             *
//             * example:
//             * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1e810861e20c0c30&redirect_uri=http%3a%2f%2ftest.ejiapei.com%2fhappydriving%2fwxpay%2ftest&response_type=code&scope=snsapi_base&state=123#wechat_redirect
//             *
//             * 此URL必须在微信浏览器中打开，redirect_uri设置为当前controller方法对应的restful接口。
//             *
//             */
//            String code = request.getParameter("code");
//            if (StringUtils.isEmpty(code)) {
//                LOG.error("no code retrieved.");
//                throw new BusinessException("no code retrieved");
//            }
//
//            //--openid--
//            String openId = weixinPaymentService.getOpenId(code);
//            if (StringUtils.isEmpty(openId)) {
//                throw new BusinessException("no openId retrieved");
//            }
//
//            //--generate out_trade_no--
//            String outTradeNo = paymentPublicService.createOutTradeNo(Integer.toString(userId, 10));
//
//            //--prepayId--
//            String prepayId = weixinPaymentService.getPrepayId(outTradeNo, openId, publicService.getClientIpAddr(request), publicService.getCurrentDomain(request), coachProductInfo);
//            if (StringUtils.isEmpty(prepayId)) {
//                throw new BusinessException("no prepayId retrieved");
//            }
//
//            //组装返回JSON对象
//            JSONObject rtn = weixinPaymentService.assemblePayRequestParams(prepayId);
//
//            //package jsp页面为保留字会报错，先改为package1
//            String packageVal = rtn.getString("package");
//            rtn.remove("package");
//            rtn.put("package1", packageVal);
//
//            //将userId和out_trade_no等信息写入payment_result表
//            paymentPublicService.insertStubToPaymentResultTable(userId, PaymentResult.CHANNEL.WEIXIN, coachProductId, outTradeNo);
//
//
//            //生成需要返回的ModelAndView
//            ModelAndView modelAndView = new ModelAndView("weixinPay");
//            for (Map.Entry<String, Object> entry : rtn.entrySet()) {
//                modelAndView.addObject(entry.getKey(), entry.getValue());
//            }
//
//            return modelAndView;
//
//        } catch (Exception e) {
//            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
//            throw new BusinessException(e);
//        }
//    }

    @RequestMapping(value = "/payModelAndView", method = RequestMethod.GET)
    public ModelAndView wxPayModelAndView(HttpServletRequest request) throws BusinessException, UnsupportedEncodingException {
        try {

//          int userId=Integer.parseInt(request.getSession().getAttribute("phone").toString());
            String orderid=request.getParameter("orderid");
            PaymentInfo paymentInfo=paymentInfoService.getPaymentInfoByOrderId(orderid);

            LOG.info("payModelAndView orderid is" + orderid);

            int schoolid=Integer.parseInt(paymentInfo.getSchoolId());
            LOG.info("payModelAndView schoolid is"+schoolid);

            //通过订单号获取预报名人信息
            String userId;
            if(paymentInfo!=null){
                 userId=paymentInfo.getPhone();
                 LOG.info("userid is"+userId);
            }else{
                throw new BusinessException("订单不存在!");
            }


            //通过schoolid获取schoolinfo
            DrivingSchoolDetailInfo schooldetail=drivingSchoolService.getDrivingSchoolDetailBySchoolId(schoolid);
            if(schooldetail==null){
                throw new BusinessException("订单信息有误,请重新下单");
            }

            /**
             * Retrieve 'code'
             *
             * example:
             * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1e810861e20c0c30&redirect_uri=http%3a%2f%2ftest.ejiapei.com%2fhappydriving%2fwxpay%2ftest&response_type=code&scope=snsapi_base&state=123#wechat_redirect
             *
             * 此URL必须在微信浏览器中打开，redirect_uri设置为当前controller方法对应的restful接口。
             *
             */
            String code = request.getParameter("code");
            LOG.error("code is "+code);

            if (StringUtils.isEmpty(code)) {
                LOG.error("no code retrieved.");
                throw new BusinessException("no code retrieved");
            }

            //--openid--
            String openId = weixinPaymentService.getOpenId(code);
            LOG.info("openId is " + openId);
            if (StringUtils.isEmpty(openId)) {
                throw new BusinessException("no openId retrieved");
            }

            //--generate out_trade_no--
            String outTradeNo = paymentPublicService.createOutTradeNo(userId);
            LOG.info("outTradeNo is "+outTradeNo);

            //--prepayId--
            String prepayId = weixinPaymentService.getPrepayId(outTradeNo, openId, publicService.getClientIpAddr(request), publicService.getCurrentDomain(request), schooldetail);
            LOG.info("prepayId is "+prepayId);

            //插入微信订单号
            paymentInfoService.updateOutTradeNoByOrderId(orderid, outTradeNo);
            LOG.info("update OutTradeNo is ok ");


            //组装返回JSON对象
            JSONObject rtn = weixinPaymentService.assemblePayRequestParams(prepayId);

            LOG.info("rtn package is" + rtn.getString("package"));
          //  package jsp页面为保留字会报错，先改为package1
            String packageVal = rtn.getString("package");
            rtn.remove("package");
            rtn.put("package1", packageVal);


            //生成需要返回的ModelAndView
            ModelAndView modelAndView = new ModelAndView("weixinPay");
            for (Map.Entry<String, Object> entry : rtn.entrySet()) {
                LOG.info(entry.getKey()+"   "+entry.getValue());
                modelAndView.addObject(entry.getKey(), entry.getValue());
            }

            return modelAndView;

        } catch (Exception e) {
            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
            throw new BusinessException(e);
        }
    }

    /**
     * 在创建prepayId时，指定的notify_url, 在下单操作完成(成功/失败/取消)时回调的逻辑portal
     *
     *
     * @param request
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public @ResponseBody String notify(HttpServletRequest request) throws Exception {


        String xml = IOUtils.toString(request.getInputStream(), CharEncoding.UTF_8);
        weixinPaymentService.updatewxpaynotify(xml);

        //根据回调通知API，需要返回如下xml，才能让微信服务器确认已经接受到notify消息，否则微信服务器会多次retry调用我们的接口
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

}