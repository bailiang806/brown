package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.alipay.util.AlipayNotify;
import com.happydriving.rockets.server.component.message.MailSender;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.entity.PaymentInfo;
import com.happydriving.rockets.server.entity.PaymentResult;
import com.happydriving.rockets.server.mapper.CoachProductMapper;
import com.happydriving.rockets.server.mapper.PaymentInfoMapper;
import com.happydriving.rockets.server.mapper.PaymentResultMapper;
import com.happydriving.rockets.server.model.CoachProductInfo;
import com.happydriving.rockets.server.model.DrivingSchoolDetailInfo;
import com.happydriving.rockets.server.service.AliPaymentService;
import com.happydriving.rockets.server.service.DrivingSchoolService;
import com.happydriving.rockets.server.service.PaymentInfoService;
import com.happydriving.rockets.server.service.PaymentPublicService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付宝支付相关
 *
 * 1. 商户登陆入口：https://b.alipay.com/
 * 2. 登陆成功后点“我的商家服务”
 * 3. 弹出框内选自助集成，或者在页面选下载集成文档
 * 4. 选择对应的开发包，里面有demo和接口说明
 *
 *
 * Created by jasonzhu on 30/6/15.
 */

@Controller
@RequestMapping("/alipay")
public class AliPaymentController {
    private static final Logger logger = Logger.getLogger(AliPaymentController.class);

    @Autowired
    private AliPaymentService aliPaymentService;
    @Autowired
    private PaymentPublicService paymentPublicService;
    @Autowired
    PaymentResultMapper paymentResultMapper;
    @Autowired
    CoachProductMapper coachProductMapper;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    DrivingSchoolService drivingSchoolService;

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;


    /**
     *
     * 支付宝付款链接生成逻辑
     *
     *
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPayUrl", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getPayUrl(HttpServletRequest request) {

        try {
            //userId
//            String xueyuanId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
            String orderid=request.getParameter("orderid");

            PaymentInfo paymentInfo=paymentInfoService.getPaymentInfoByOrderId(orderid);

            //通过schoolid获取schoolinfo
            int schoolid=Integer.parseInt(paymentInfo.getSchoolId());
            DrivingSchoolDetailInfo schooldetail=drivingSchoolService.getDrivingSchoolDetailBySchoolId(schoolid);
            if(schooldetail==null){
                throw new BusinessException("订单信息有误,请重新下单");
            }

            String userId;
            if(paymentInfo!=null){
                userId=paymentInfo.getPhone();
                logger.info("userid is" + userId);
            }else{
                throw new BusinessException("订单不存在!");
            }

            //--out_trade_no--
            String outTradeNo = paymentPublicService.createOutTradeNo(userId);

            //插入微信订单号
            paymentInfoService.updateOutTradeNoByOrderId(orderid, outTradeNo);

            //generate alipay url
            return new ResponseJsonObject(true, aliPaymentService.getPayRequestUrl(request, outTradeNo,schooldetail));

        } catch (Exception e) {
            logger.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
            return new ResponseJsonObject(false, e.getMessage(), -1);
        }
    }


    /**
     *
     * 支付宝支付后回调逻辑
     *
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
//    @RequestMapping(value = "/notify", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject notify(HttpServletRequest request) throws UnsupportedEncodingException, BusinessException {
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            params.put(name, valueStr);
//        }
//
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//
//        //商户订单号
//        String out_trade_no = request.getParameter("out_trade_no");
//
//        //支付宝交易号
//        String trade_no = request.getParameter("trade_no");
//
//        //交易状态
//        String trade_status = request.getParameter("trade_status");
//
//        //j交易金额
//        String total_fee = request.getParameter("total_fee");
//
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//
//        if(AlipayNotify.verify(params)){//验证成功
//            //////////////////////////////////////////////////////////////////////////////////////////
//            //请在这里加上商户的业务逻辑程序代码
//
//            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//
//            if(trade_status.equals("TRADE_FINISHED")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//                return new ResponseJsonObject(true, "finished");
//            } else if (trade_status.equals("TRADE_SUCCESS")){
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//
//
//
//                //----获取当前payment_result表中对应的记录----
//                PaymentResult paymentResult = new PaymentResult();
//                paymentResult.setOutTradeNo(out_trade_no);
//
//                paymentResult = paymentResultMapper.getTradeState(paymentResult);
//                if(paymentResult == null)   {
//                    throw new BusinessException("No out_trade_no found. out_trade_no=["+out_trade_no+"], trade_no=["+trade_no+"]");
//                }
//
//                //----检查付款金额与教练产品是否相符（由于付款链接为GET方式请求，可能被用户自行修改付款金额）----
//                LinkedHashMap<String, Object> coachProductInfo= paymentResultMapper.selectCoachProductInfoById(out_trade_no);
//                BigDecimal coachProductPrice = ((BigDecimal)(coachProductInfo.get("coach_product_price"))).setScale(2, BigDecimal.ROUND_HALF_UP);
//                int flag = coachProductPrice.compareTo(new BigDecimal(total_fee, new MathContext(2, RoundingMode.HALF_UP)));
//                if(flag != 0)   {
//                    //如果价格与教练产品有出入，更新数据库中交易状态为fee_inconsistent, 打日志
//                    paymentResult.setThirdPartyTradeNo(trade_no);
//                    paymentResult.setTradeStateEnum(PaymentResult.TRADE_STATE.FEE_INCONSISTENT);
//                    logger.fatal("inconsistent payment. out_trade_no=["+out_trade_no+"], coachProductPrice=["+coachProductPrice.floatValue()+"], alipay_total_fee=["+total_fee+"]");
//                    if (paymentResultMapper.updateTradeState(paymentResult) <= 0) {
//                        throw new BusinessException("update trade_state fails. paymentResult=[" + paymentResult + "]");
//                    }
//                }
//                else {
//                    //----如果当前数据库中交易状态为ongoing，才notify----
//                    if (paymentResult.getTradeState() == PaymentResult.TRADE_STATE.ONGOING.getValue()) {
//                        //----更新payment_result表的交易状态为交易成功----
//                        paymentResult.setThirdPartyTradeNo(trade_no);
//                        paymentResult.setTradeStateEnum(PaymentResult.TRADE_STATE.PAYED_SUCCESS);
//                        if (paymentResultMapper.updateTradeState(paymentResult) <= 0) {
//                            throw new BusinessException("update trade_state fails. paymentResult=[" + paymentResult + "]");
//                        }
//
//                        //----给教练和学员发送短信----
//                        paymentPublicService.sendSmsToCoachAndXueyuanAfterPayment(out_trade_no);
//                    }
//                }
//
//                return new ResponseJsonObject(true, "success");
//            }
//
//            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//
//            return new ResponseJsonObject(true, "unknown");
//
//            //////////////////////////////////////////////////////////////////////////////////////////
//        }else{//验证失败
//            return new ResponseJsonObject(true, "fail");
//        }
//    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject notify(HttpServletRequest request) throws UnsupportedEncodingException, BusinessException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

        //商户订单号
        String out_trade_no = request.getParameter("out_trade_no");

        //支付宝交易号
        String trade_no = request.getParameter("trade_no");

        //交易状态
        String trade_status = request.getParameter("trade_status");

        //j交易金额
        String total_fee = request.getParameter("total_fee");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            if(trade_status.equals("TRADE_FINISHED")){
                return new ResponseJsonObject(true, "finished");
            } else if (trade_status.equals("TRADE_SUCCESS")){
//               更新订单状态信息
                paymentInfoMapper.updatePaymentByOutTradeNo(out_trade_no,trade_no);
                logger.info("update status is ok");
                mailSender.sendMail("订单号:" + out_trade_no+" 的订单 付款成功","success");
                smsAuthorizedTools.sendPaymentMessage("18612031554", out_trade_no);

                return new ResponseJsonObject(true, "success");
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            return new ResponseJsonObject(true, "unknown");

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            return new ResponseJsonObject(true, "fail");
        }
    }




    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject test(HttpServletRequest request) throws UnsupportedEncodingException, BusinessException {
        logger.info("JUDKING_TESTTTTT");

        return null;
    }




}
