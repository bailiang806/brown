package com.happydriving.rockets.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.component.message.SmsResultJsonObject;
import com.happydriving.rockets.server.entity.StudentInfo;
import com.happydriving.rockets.server.entity.User;
import com.happydriving.rockets.server.model.CoachDetailInfo;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.StudentService;
import com.happydriving.rockets.server.service.UserService;
import com.happydriving.rockets.server.service.WeixinOauthService;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/user")
public class UserController {

    public static final Log LOG = LogFactory.getLog(UserController.class);

    public static final String PHONE_NUMBER = "phone";
    public static final String PASSWORD = "userpassword";
    public static final String INPUT_CODE = "inputcode";
    public static final String OPENID = "openid";
    public static final String REPEAT_PASSWORD = "repeatpassword";
    public static final String USER_NAME = "username";
    public static final String CONTACT_NAME = "contactname";
    public static final String CONTACT_PHONE = "contactphone";

    @Autowired
    private UserService userService;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;

    @Autowired
    private WeixinOauthService weixinOauthService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoachService coachService;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 跳转至登录页面，如果当前session中已经存在user_id，直接跳转至 教练/学员 首页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) {
        if (request.getSession().getAttribute(CommonConstants.SESSION_USER_ID) != null) {
            String roleName = request.getSession().getAttribute(CommonConstants.SESSION_ROLE).toString();
            return String.format("redirect:/%s/show_detail",
                    roleName.equals(CommonConstants.ROLE_COACH) ? "coach" : "student");
        }
        return "login";
    }

    /**
     * 用户登录功能，登录可能会有很多种情况：<P></P>
     * <li>如果request中带有code字段，说明是从微信端进入，需要获取用户的openId，
     * 去user_bridge表中如果能够拿到该openId对应的手机号则自动登录，如果拿不到，以guest身份登录</li>
     * <p/>
     * <p></p>
     * 注意：
     * <li>从微信端oauth过来需要使用open.weixin.qq.com 的链接回调来设置url，回调的url必须要传code参数，此时不用传手机号以及验证码,
     * example:
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1e810861e20c0c30&redirect_uri=hhttps://detail.tmall.com/item.htm?spm=a220m.1000858.0.0.l4LSbx&id=45693662169&rn=3a0702a1f2cc8ba032bb6cc4ad89ed01&skuId=104749328423%3a%2f%2fwww.ejiapei.com%2fhappydriving%2fwxpay%2ftest&response_type=code&scope=snsapi_base&state=123#wechat_redirect
     * <p/>
     * 此URL必须在微信浏览器中打开，redirect_uri设置为当前controller方法对应的restful接口。</li>
     *
     * @param request -
     * @return - modelAndView, 返回的属性需要包含下面的属性 openId phone userType userId
     * @throws BusinessException
     * @throws IOException
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public ModelAndView doLoginAjax(HttpServletRequest request) throws BusinessException, IOException {
        String code = request.getParameter("code");
        if (StringUtils.isNotBlank(code)) {
            LOG.info("通过回调code来进入该url，获取openId来登录... code＝" + code);
            JSONObject userInfoAccessTokenAndOpenId = weixinOauthService.getUserInfoAccessTokenAndOpenId(code);
            String openId = userInfoAccessTokenAndOpenId.getString("openid");
            LOG.info(
                    "根据code拿到对应的openId，code=[" + code + "], userInfoAccessTokenAndOpenId=[" + userInfoAccessTokenAndOpenId + "]");
            if (StringUtils.isNotBlank(openId)) {
                //-- 获取当前openId对应的身份信息 --
                Map<String, String> userIdentity = userService.getIdentity(openId);
                LOG.info("JUDKING_DEBUG. userIdentity=[" + userIdentity + "]");
                //在session中添加用户信息
                request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, userIdentity.get("userId"));
                request.getSession().setAttribute(CommonConstants.SESSION_ROLE, userIdentity.get("userType").equals("guest") ? "" :
                    userIdentity.get("userType"));
                request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, userIdentity.get("phone"));
                request.getSession().setAttribute(CommonConstants.SESSION_OPENID, openId);
                //转到教练或学员首页 coachIndex / studentIndex
                ModelAndView modelAndView =
                        new ModelAndView(CommonConstants.ROLE_COACH.equals(userIdentity.get("userType")) ?
                                "coachIndex" : "studentIndex");
                for (Map.Entry<String, String> entry : userIdentity.entrySet()) {
                    modelAndView.addObject(entry.getKey(), entry.getValue());
                }
                return modelAndView;
            } else {
                LOG.error(
                        "获取openId失败，code=[" + code + "], userInfoAccessTokenAndOpenId=[" + userInfoAccessTokenAndOpenId + "]");
                throw new BusinessException("未获取到openId，");
            }
        } else {
            String phone = request.getParameter("phone");
            String inputCode = request.getParameter("inputcode");

            LOG.info(String.format("直接以手机号: %s 和验证码登录", phone));
            if (smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
                User user = userService.getUserByPhoneNumber(phone);
                if (user == null) {
                    throw new BusinessException(String.format("手机号码: %s 并没有注册，请先注册!", phone));
                }

                request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, phone);
                request.getSession().setAttribute(CommonConstants.SESSION_ROLE, user.getRole());
                request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getId());

                ModelAndView modelAndView = new ModelAndView(String.format("%sIndex", user.getRole()));
                modelAndView.addObject("phone", phone);
                modelAndView.addObject("userType", user.getRole());
                modelAndView.addObject("userId", user.getId());
                return modelAndView;
            } else {
                LOG.error(String.format("手机: %s, 验证码: %s 错误", phone, inputCode));
                throw new BusinessException("手机号和验证码超时或不正确!");
            }
        }
    }

    /**
     * 用户登录功能，
     * <li>如果session中已经存在openId字段，说明是从PC/WAP/APP中补充提交微信openId字段，验证手机号和短信验证码，并关联该openId至user_bridge表中</li>
     * <li>如果不符合以上的要求，则视为从PC/WAP/APP端登录，直接验证用户手机号和验证码</li>
     * <p/>
     * <p></p>
     * 注意如果该用户已经注册为 学员或教练，则返回用户已经注册的错误
     *
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject doLoginPost(HttpServletRequest request) throws BusinessException {
        Object openIdObject = request.getSession().getAttribute(CommonConstants.SESSION_OPENID);
        String phone = request.getParameter("phone");
        String inputCode = request.getParameter("inputcode");

        if (openIdObject != null) {
            LOG.info("会话中已经存在openId, 通过手机号码登录，并绑定微信号");
            String openId = openIdObject.toString();
            if (smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
                User user = userService.getUserByPhoneNumber(phone);
                if (user == null) {
                    throw new BusinessException(String.format("手机号码: %s 并没有注册，请先注册!", phone));
                }
                userService.insertUpdateUserBridge(openId, phone);

                request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, phone);
                request.getSession().setAttribute(CommonConstants.SESSION_ROLE, user.getRole());
                request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getId());
                request.getSession().setAttribute(CommonConstants.SESSION_OPENID, openId);

                String redirectUrl = String.format("/%s/show_detail", user.getRole());
                ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
                responseJsonObject.setRedirectUrl(redirectUrl);
                return responseJsonObject;
            } else {
                LOG.error(String.format("手机: %s, 验证码: %s 错误", phone, inputCode));
                throw new BusinessException("手机号和验证码超时或不正确!");
            }
        } else {
            LOG.info(String.format("直接以手机号: %s 和验证码登录", phone));
            if (smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
                User user = userService.getUserByPhoneNumber(phone);
                if (user == null) {
                    return new ResponseJsonObject(false, String.format("手机号码: %s 并没有注册，请先注册!", phone));
                }

                request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, phone);
                request.getSession().setAttribute(CommonConstants.SESSION_ROLE, user.getRole());
                request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getId());

                String redirectUrl = String.format("/%s/show_detail", user.getRole());
                ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
                responseJsonObject.setRedirectUrl(redirectUrl);
                return responseJsonObject;
            } else {
                LOG.error(String.format("手机: %s, 验证码: %s 错误", phone, inputCode));
                throw new BusinessException("手机号和验证码超时或不正确!");
            }
        }

    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册为教练，是通过手机号码以及手机验证码来注册，成功后会在自动登录并直接跳转至对应教练详细信息页面中
     * <P></P>
     * <li>如果session中存在openId，则将该openId与phone绑定，用于微信直接登录使用;</li>
     * <li>如果session中不存在，不进行绑定操作</li>
     * <li>user, coach表中会插入相应的数据</li>
     * <p/>
     * <p></p>
     * 注意如果该用户已经注册为 学员或教练，则返回用户已经注册的错误
     *
     * @param request - 要求request中存在手机号phone, 验证码inputcode
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/doRegisterCoach", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject doRegisterCoach(HttpServletRequest request) throws BusinessException {
        return doRegisterRoleImpl(request, CommonConstants.ROLE_COACH);
    }

    @RequestMapping(value = "/register_student")
    public String getRegisterStudentUrl() {
        return "register_student";
    }

    /**
     * 注册为学员，是通过手机号码以及手机验证码来注册，成功后会在自动登录并直接跳转至对应学员详细信息页面中
     * <P></P>
     * <li>如果session中存在openId，则将该openId与phone绑定，用于微信直接登录使用;</li>
     * <li>如果session中不存在，不进行绑定操作</li>
     * <li>user, student表中会插入相应的数据</li>
     *
     * @param request - 要求request中存在手机号phone, 验证码inputcode
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/doRegisterStudent", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject doRegisterStudent(HttpServletRequest request) throws BusinessException {
        return doRegisterRoleImpl(request, CommonConstants.ROLE_STUDENT);
    }

    private ResponseJsonObject doRegisterRoleImpl(HttpServletRequest request, String role) throws BusinessException {
        String phone = request.getParameter(PHONE_NUMBER);
        String inputCode = request.getParameter(INPUT_CODE);
        Object openId = request.getSession().getAttribute(CommonConstants.SESSION_OPENID);

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(inputCode)) {
            LOG.error("params error. phone=[" + phone + "], inputCode=[" + inputCode + "]");
            throw new BusinessException("parameters error.");
        }

        boolean authResult = smsAuthorizedTools.validateSmsInfo(phone, inputCode);
        if (!authResult) {
            return new ResponseJsonObject(false, "验证码错误或已经超时!");
        }
        try {
            User user = userService.insertUserAndOpenId(phone, role, openId);

            //用户信息添加到session
            request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getId());
            request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, user.getPhone());
            request.getSession().setAttribute(CommonConstants.SESSION_ROLE, role);
            request.getSession().setAttribute(CommonConstants.SESSION_OPENID, openId);

            ResponseJsonObject responseJsonObject = new ResponseJsonObject(true, user);
            responseJsonObject.setRedirectUrl(CommonConstants.ROLE_COACH.equals(role) ? "/coach/coach_basic"
                    : "/student/show_detail");
            return responseJsonObject;
        } catch (BusinessException e) {
            return new ResponseJsonObject(false, e.getMessage());
        }
    }

    /**
     * 向手机号码发送短信验证码，该短信验证码用户用户注册和登录，该验证码在5分钟内有效
     * <p/>
     * 注意，同一个手机号码在1分钟内只能发送1条短信
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/smsValidate", method = RequestMethod.POST)
    @ResponseBody
    public SmsResultJsonObject sendSmsValidate(HttpServletRequest request) {
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        SmsResultJsonObject resultObject = null;
        try {
//            boolean userRegistered = userService.isUserRegistered(phoneNumber);
//            if (userRegistered) {
//                resultObject = new SmsResultJsonObject();
//                resultObject.setPhoneNumber(phoneNumber);
//                resultObject.setMessage(String.format("手机号: %s 已经被注册!", phoneNumber));
//                resultObject.setResult(false);
//            } else {
//                resultObject = smsAuthorizedTools.sendValidatorMessage(phoneNumber);
//            }
            return smsAuthorizedTools.sendValidatorMessage(phoneNumber);
        } catch (BusinessException e) {
            resultObject = new SmsResultJsonObject();
            resultObject.setMessage(e.getMessage());
            resultObject.setResult(false);
            return resultObject;
        }
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONPObject deleteUserInfo(HttpServletRequest request) {
//        String phoneNumber = request.getParameter(PHONE_NUMBER);
//        int count = userService.deleteUser(phoneNumber);
//
//        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION,
//                new ResponseJsonObject(true, String.format("Delete %s items!", count)));
//    }

    /**
     * 用户登出，会将当前session中所有的属性都删除掉，并删除该session
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(CommonConstants.SESSION_USER_ID);
        request.getSession().removeAttribute(CommonConstants.SESSION_PHONENUMBER);
        request.getSession().removeAttribute(CommonConstants.SESSION_ROLE);
        request.getSession().invalidate();
//        request.getSession().
        return "/login";
    }


    @RequestMapping("aboutus")
    public String getAboutUsUrl() {
        return "aboutus";
    }

    @RequestMapping("coorperation")
    public String getCoorperationUrl() {
        return "coorperation";
    }

    @RequestMapping("trainingfee")
    public String getTrainingFeeUrl() {
        return "trainingfee";
    }

    @RequestMapping("legaldocument")
    public String getLegalDocument() {
        return "legaldocument";
    }

//    @RequestMapping(value = "/personalitytest")
//    @ResponseBody
//    public ResponseJsonObject personalitytest(HttpServletRequest request) throws BusinessException {
//        Object userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        Object role = request.getSession().getAttribute(CommonConstants.SESSION_ROLE);
//        if (userId != null && role != null) {
//            if (CommonConstants.ROLE_STUDENT.equalsIgnoreCase(role.toString())) {
//                Integer studentId = Integer.parseInt(userId.toString());
//                StudentInfo studentInfo = studentService.getStudentInfoByUserId(studentId);
//                String pics = studentInfo.getPersonalitytest();
//                return new ResponseJsonObject(true, "", -1, pics);
//            }
//            if (CommonConstants.ROLE_COACH.equalsIgnoreCase(role.toString())) {
//                Integer studentId = Integer.parseInt(userId.toString());
//                CoachDetailInfo studentInfo = coachService.getCoachDetailInfoByUserId(studentId);
//                String pics = studentInfo.getPersonalitytest();
//                return new ResponseJsonObject(true, "", -1, pics);
//            }
//            return new ResponseJsonObject(false, null);
//        } else {
//            return new ResponseJsonObject(false, null);
//        }
//    }

    @RequestMapping(value = "/doLoginHidden", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject loginHidden(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        User user = userService.getUserByPhoneNumber(phone);
        if (user != null) {
            request.getSession().setAttribute(CommonConstants.SESSION_USER_ID, user.getId());
            request.getSession().setAttribute(CommonConstants.SESSION_PHONENUMBER, user.getPhone());
            request.getSession().setAttribute(CommonConstants.SESSION_ROLE, user.getRole());
            return new ResponseJsonObject(true, String.format("用户: %s 以身份: %s 登录成功!", user.getPhone(),
                    user.getRole()));
        } else {
            return new ResponseJsonObject(false, String.format("该手机号: %s 的用户不存在!", phone));
        }
    }


    @RequestMapping(value = "/jdtest", method = RequestMethod.GET)
    @ResponseBody
    public String jdtest(HttpServletRequest request) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Weixin.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String ip = p.getProperty("ip");
        System.out.println(ip);

        return ip;
    }

}
