package com.happydriving.rockets.server.component.message;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.DsTrainingSchedule;
import com.happydriving.rockets.server.entity.SmsValidation;
import com.happydriving.rockets.server.entity.SmsValidationExample;
import com.happydriving.rockets.server.mapper.DrivingSchoolMapper;
import com.happydriving.rockets.server.mapper.PaymentInfoMapper;
import com.happydriving.rockets.server.mapper.SmsValidationMapper;
import com.happydriving.rockets.server.model.DsTrainingScheduleInfo;
import com.happydriving.rockets.server.utils.CommonTools;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Component
public class SmsAuthorizedTools {

    private static final Log LOG = LogFactory.getLog(SmsAuthorizedTools.class);

    public static final int STATUS_OK = 200;

    public static final String API_VALUE = "f82685d1abf88e98ffc991393aaed512";
    public static final String WEB_RESOURCE_JSON_URL = "http://sms-api.luosimao.com/v1/send.json";

    public static final String STUDENT_PAY_NOTIFY = "您可以预约练车了~预约通道：微信\"e驾陪\"公众号，或者登录 www.ejiapei.com【e驾陪 · 报名成功】";
    public static final String COACH_CHECKIN_NOTIFY =
            "您有学员报名了~去发布练车任务吧：请进入微信\"e驾陪\"公众号，或者登录 www.ejiapei.com【e驾陪 · 新生报到】";
    //    public static final String STUDENT_APPOINTMENT_NOTIFY =
//            "您有新的练车任务可以预约了~预约通道：微信\"e驾陪\"公众号，或者登录 www.ejiapei.com【e驾陪 · 新的练车任务】";
    public static final String COACH_APPOINTMENT_NOTIFY = "您有学员预约练车了~【e驾陪 · 新的预约】";

    @Autowired
    private SmsValidationMapper smsValidationMapper;

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private DrivingSchoolMapper drivingSchoolMapper;

    /**
     * Send sms message to exact phone number.
     *
     * @param phoneNumber
     * @return
     * @throws BusinessException - 短信发送失败 或 短信数据插入数据库表失败
     */
    public SmsResultJsonObject sendValidatorMessage(String phoneNumber) throws BusinessException {
        //--发送验证码之前确定当前手机号是否在1分钟内发送过验证码--
        SmsValidationExample smsValidationExample = new SmsValidationExample();
        smsValidationExample.createCriteria().andPhoneEqualTo(phoneNumber);
        List<SmsValidation> smsValidations = smsValidationMapper.selectByExample(smsValidationExample);
        if (smsValidations.size() > 0 &&
                Math.abs((smsValidations.get(0).getCreatedAt().getTime() - (new Date()).getTime()) / 1000) <= 60
                ) {
            SmsResultJsonObject resultObject = new SmsResultJsonObject();
            resultObject.setMessage("发送验证码间隔时间需要大于1分钟");
            resultObject.setResult(false);
            return resultObject;
        }

        //--发送短信验证码--
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", API_VALUE));
        WebResource webResource = client.resource(
                WEB_RESOURCE_JSON_URL);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", phoneNumber);
        String randomCode = CommonTools.generateRandomCode();
        String smsMessage = String.format("验证码：%s，该验证码在5分钟内有效，请及时输入【e驾陪】", randomCode);
        formData.add("message", smsMessage);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        if (status != STATUS_OK) {
            throw new BusinessException(String.format("向用户:%s 发送短信(内容: %s)失败! 返回的文本内容: %s ",
                    phoneNumber, smsMessage, textEntity));
        }

        //缓存验证码 -> sms_validation数据库
        SmsValidation smsValidation = new SmsValidation();
        smsValidation.setPhone(phoneNumber);
        smsValidation.setRandomCode(randomCode);
        smsValidation.setCreatedAt(new Date());
        if (smsValidationMapper.insertOrUpdateIfNotExists(smsValidation) <= 0) {
            LOG.error("insert into sms_validation failed. SmsValidation=[" + smsValidation + "]");
            SmsResultJsonObject resultObject = new SmsResultJsonObject();
            resultObject.setMessage("internal error");
            resultObject.setResult(false);
            return resultObject;
        }

        //DEPRECATED mazhiqiang: memory-cache version dealing with phone & randomCode
//        smsValidationCache.addSmsValidationInfo(phoneNumber, randomCode);

        SmsResultJsonObject result = new SmsResultJsonObject();
        result.setPhoneNumber(phoneNumber);
        result.setRandomCode(randomCode);
        result.setResult(true);
        LOG.info(String.format("向用户:%s 发送短信(内容: %s) 成功!", phoneNumber, smsMessage));
        return result;
    }

    //--发送选课成功短信
    public SmsResultJsonObject sendReserveSuccessMessage(String phoneNumber, DsTrainingScheduleInfo course) throws BusinessException {
        DateTime trainingDate = new DateTime(course.getTrainingDate().getTime());
        int month = trainingDate.getMonthOfYear();
        int day = trainingDate.getDayOfMonth();
        int week = trainingDate.getDayOfWeek();
        String stringweek = weekToString(week);
        String trainingTime = course.getTrainingTime();
        String time = trainingTimeConvert(trainingTime);
        String smsMessage = String.format("您预约成功了！请于%d月%d日(%s)%s到达训练场~【e驾陪·预约成功】", month, day, stringweek, time
        );
        sendNotifyMessage(phoneNumber, smsMessage);
        SmsResultJsonObject result = new SmsResultJsonObject();
        result.setPhoneNumber(phoneNumber);
        result.setResult(true);
        return result;
    }

    public SmsResultJsonObject sendPaymentMessage(String phone,String outTradeNo) throws BusinessException {
      /*  PaymentInfo paymentInfo=paymentInfoMapper.selectByPrimaryKey(orderId);
        DrivingSchool drivingSchool=drivingSchoolMapper.selectByPrimaryKey(Integer.parseInt(paymentInfo.getSchoolId()));*/
        String smsMessage=String.format("订单号%s支付完成【e驾陪】",outTradeNo);
        sendNotifyMessage(phone,smsMessage);
        SmsResultJsonObject result=new SmsResultJsonObject();
        result.setPhoneNumber(phone);
        result.setResult(true);
        return result;
    }


    public void sendNotifyMessage(String phoneNumber, String smsMessage) throws BusinessException {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", API_VALUE));
        WebResource webResource = client.resource(
                WEB_RESOURCE_JSON_URL);

        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", phoneNumber);
        formData.add("message", smsMessage);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        if (status != STATUS_OK) {
            throw new BusinessException(String.format("向用户:%s 发送短信(内容: %s)失败! 返回的文本内容: %s ",
                    phoneNumber, smsMessage, textEntity));
        }
        LOG.info(String.format("向用户:%s 发送短信(内容: %s) 成功!", phoneNumber, smsMessage));
    }

    /**
     * When batch send message, we should consider partical success/fail
     *
     * @param phoneNumberList -
     * @param smsMessage      -
     * @return - failed message ...
     */
    public String batchSendNotifyMessage(List<String> phoneNumberList, String smsMessage) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", API_VALUE));
        WebResource webResource = client.resource(
                WEB_RESOURCE_JSON_URL);

        StringBuilder errorMessage = new StringBuilder();
        for (String phoneNumber : phoneNumberList) {
            MultivaluedMapImpl formData = new MultivaluedMapImpl();
            formData.add("mobile", phoneNumber);
            formData.add("message", smsMessage);
            ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                    post(ClientResponse.class, formData);
            String textEntity = response.getEntity(String.class);
            int status = response.getStatus();
            if (status != STATUS_OK) {
                errorMessage.append((String.format("向用户:%s 发送短信(内容: %s)失败! 返回的文本内容: %s \n",
                        phoneNumber, smsMessage, textEntity)));
            }
            LOG.info(String.format("向用户:%s 发送短信(内容: %s) 成功!", phoneNumber, smsMessage));
        }
        return errorMessage.toString();
    }

    public boolean validateSmsInfo(String phoneNumber, String inputCode) {
        //验证验证码的正确性。查sms_validation表，如果验证码创建时间跟现在比大于5分钟，作为超时处理
        SmsValidationExample smsValidationExample = new SmsValidationExample();
        smsValidationExample.createCriteria().andPhoneEqualTo(phoneNumber);
        List<SmsValidation> smsValidations = smsValidationMapper.selectByExample(smsValidationExample);
        if (smsValidations.size() == 0 ||
                StringUtils.equals(smsValidations.get(0).getRandomCode(), inputCode) == false ||
                Math.abs((smsValidations.get(0).getCreatedAt().getTime() - (new Date()).getTime()) / 1000) >= 5 * 60
                ) {
            return false;
        }
        return true;
    }


    private String testSend() {
        // just replace key here
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", "f82685d1abf88e98ffc991393aaed512"));
        WebResource webResource = client.resource(
                "http://sms-api.luosimao.com/v1/send.json");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", "18511907637");
        formData.add("message", "验证码：123213【易驾陪】");
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        //System.out.print(textEntity);
        //System.out.print(status);
        return textEntity;
    }

    private static String trainingTimeConvert(String trainingTime) {
        String result = "";
        String startTime = trainingTime.split("-")[0];
        int hour = Integer.parseInt(startTime.split(":")[0]);
        String minute = startTime.split(":")[1];
        String a;
        if (hour < 12) {
            a = "上午";
        } else if (hour == 12) {
            a = "中午";
        } else if ((hour > 12) && (hour < 18)) {
            a = "下午";
        } else {
            a = "晚上";
        }
        hour = hour <= 12 ? hour : hour - 12;
        String stringMinute;
        if (minute.equals("00")) {
            stringMinute = "";
        } else if (minute.equals("30")) {
            stringMinute = "半";
        } else {
            stringMinute = minute;
        }
        result = String.format("%s%d点%s", a, hour, stringMinute);
        return result;
    }

    private String weekToString(int week) {
        String result = "";
        switch (week) {
//            case 0:
//                result = "周日";
//                break;
            case 1:
                result = "周一";
                break;
            case 2:
                result = "周二";
                break;
            case 3:
                result = "周三";
                break;
            case 4:
                result = "周四";
                break;
            case 5:
                result = "周五";
                break;
            case 6:
                result = "周六";
                break;
            case 7:
                result="周日";
        }
        return result;
    }

//    private String testStatus() {
//        Client client = Client.create();
//        client.addFilter(new HTTPBasicAuthFilter(
//                "api", "f82685d1abf88e98ffc991393aaed512"));
//        WebResource webResource = client.resource("http://sms-api.luosimao.com/v1/status.json");
//        MultivaluedMapImpl formData = new MultivaluedMapImpl();
//        ClientResponse response = webResource.get(ClientResponse.class);
//        String textEntity = response.getEntity(String.class);
//        int status = response.getStatus();
//        //status: 200
//        System.out.print(status);
//        //ok: {"error":0,"msg":"ok"}
//        System.out.print(textEntity);
//        return textEntity;
//    }
//
//    public static void main(String[] args) throws BusinessException {
//        SmsAuthorizedTools api = new SmsAuthorizedTools();
//
////        api.sendNotifyMessage("18511907637", STUDENT_PAY_NOTIFY);
////        api.sendNotifyMessage("15001257893", STUDENT_PAY_NOTIFY);
//
//        String result = api.batchSendNotifyMessage(Arrays.asList("18511907637", "15001257893"), COACH_CHECKIN_NOTIFY);
//        System.out.println(result);
//
//
//
//        String httpReponse = api.testSend();
////        try {
////            JSONObject jsonObj = new JSONObject( httpReponse );
////            int error_code = jsonObj.getInt("error");
////            String error_msg = jsonObj.getString("msg");
////            if(error_code==0){
////                System.out.println("Send message success.");
////            }else{
////                System.out.println("Send message failed,code is "+error_code+",msg is "+error_msg);
////            }
////        } catch (JSONException ex) {
////            LOG.error(ex);
////        }
//        System.out.println(httpReponse);
//
//        httpReponse = api.testStatus();
//        try {
//            JSONObject jsonObj = new JSONObject(httpReponse);
////            {"error":0,"deposit":"9837"}
//            int error_code = jsonObj.getInt("error");
//            if (error_code == 0) {
//                int deposit = jsonObj.getInt("deposit");
//                System.out.println("Fetch deposit success :" + deposit);
//            } else {
//                String error_msg = jsonObj.getString("msg");
//                System.out.println("Fetch deposit failed,code is " + error_code + ",msg is " + error_msg);
//            }
//        } catch (JSONException ex) {
//            LOG.error(ex);
//        }
//
//
//    }

}
