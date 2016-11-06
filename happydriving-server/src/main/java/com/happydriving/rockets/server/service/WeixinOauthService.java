package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.BusinessRuntimeException;
import com.happydriving.rockets.server.entity.UserBridge;
import com.happydriving.rockets.server.entity.UserBridgeExample;
import com.happydriving.rockets.server.mapper.UserBridgeMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by jasonzhu on 18/6/15.
 */


/**
 * 微信支付服务类
 */
@Service
public class WeixinOauthService {
    private static final Log logger = LogFactory.getLog(WeixinOauthService.class);

    public final String appId;      //公众号APPID  (微信·公众平台 -> 开发者中心 -> 配置项 -> 开发者ID)
    public final String mchId;      //微信支付商户号
    public final String AppSecret;  //公众平台的密钥 (微信·公众平台 -> 开发者中心 -> 配置项 -> 开发者ID)
    public final String APISecret;  //商户号的密钥 (微信支付商户平台 -> 账户设置 -> API安全)
    public final String token;
            //开发者中心接入URL中需要的token (微信·公众平台 -> 开发者中心 -> 配置项 -> 服务器配置) (http://mp.weixin.qq.com/wiki/17/2d4265491f12608cd170a95559800f2d.html)


    @Autowired
    private PublicService publicService;
    @Autowired
    private UserBridgeMapper userBridgeMapper;


    public WeixinOauthService() {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("Weixin.properties");
            Properties p = new Properties();
            p.load(inputStream);

            appId = p.getProperty("appId");
            mchId = p.getProperty("mchId");
            AppSecret = p.getProperty("AppSecret");
            APISecret = p.getProperty("APISecret");
            token = p.getProperty("token");
        } catch (IOException e) {
            throw new BusinessRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 通过微信OauthAPI，将用户手机号和openId等微信用户信息关联在user_bridge表中
     *
     * @param phone
     * @param code
     * @throws BusinessException
     * @throws IOException
     */
    public void userBridge(String phone, String code) throws BusinessException, IOException {
        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            throw new BusinessException("parameter is blank. phone=[" + phone + "], code=[" + code + "]");
        }

        UserBridge userBridge = new UserBridge();
        userBridge.setPhone(phone);

        //检查phone是不是已经在user_bridge表中
        UserBridgeExample userBridgeExample = new UserBridgeExample();
        userBridgeExample.createCriteria().andPhoneEqualTo(phone);
        List<UserBridge> userBridges = userBridgeMapper.selectByExample(userBridgeExample);
        if (userBridges.size() > 0) {
            logger.warn("phone=[" + phone + "] exists in table=[user_bridge].");
            throw new BusinessException("phone exists in table.");
        }


        //--openid--
        JSONObject returnInfos = getUserInfoAccessTokenAndOpenId(code);
        String userAccessToken = returnInfos.getString("access_token");
        String openId = returnInfos.getString("openid");
        String scope = returnInfos.getString("scope");

        userBridge.setOpenId(openId);

        //如果范围是userinfo, 获取用户详细信息
        if (StringUtils.equals("snsapi_userinfo", scope)) {
            JSONObject snsapiUserinfo = getSnsapiUserinfo(userAccessToken, openId);

            userBridge.setNickname(snsapiUserinfo.getString("nickname"));
            userBridge.setSex(snsapiUserinfo.getString("sex"));
            userBridge.setProvince(snsapiUserinfo.getString("province"));
            userBridge.setCity(snsapiUserinfo.getString("city"));
            userBridge.setCountry(snsapiUserinfo.getString("country"));
            userBridge.setUnionId(snsapiUserinfo.getString("unionid"));
        }

        userBridge.setCreatedAt(new Date());
        userBridge.setUpdatedAt(new Date());

        //insert userBridge to table user_bridge
        if (userBridgeMapper.insert(userBridge) <= 0) {
            logger.error("insert into user_bridge error. userBridge=[" + userBridge + "]");
            throw new BusinessException("insert into table error.");
        }

    }


    /**
     * 获取用户access_token & openid
     * <p/>
     * 直接在后台请求对应的API即可，返回的json串中为类似如下格式的内容：
     * <p/>
     * {
     * "access_token": "OezXcEiiBSKSxW0eoylIeGhaJjUxzVpRR4o6hX-jAhOn160_GRNWPwzcWR_QSO4gbjzWHPV6zuNazuJp3spc2gptHLcR-g2QetMKeDGZ3IJD6PbJCf2YKyw6k4aeiFbdJgfJgNBXKfZ0dPb98IKR_w",
     * "expires_in": 7200,
     * "refresh_token": "OezXcEiiBSKSxW0eoylIeGhaJjUxzVpRR4o6hX-jAhOn160_GRNWPwzcWR_QSO4g7r7Y2BQy_p7bmrjxH8YN3scFXn7C4fUnNn9AFDcz_qW5ErAi4Lp9p18PcLv60yUtOBSwd8MfDIKap12lVExOAg",
     * "openid": "ogGCluNRaxBTNFWZzS_kH-rRez_Q",
     * "scope": "snsapi_base"
     * }
     * <p/>
     * <p/>
     * 参考API第二步[通过code换取网页授权access_token]：
     * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     *
     * @param code
     * @return
     * @throws IOException
     */
    public JSONObject getUserInfoAccessTokenAndOpenId(String code) throws IOException {
        String wxuserInfoAccessTokenApi =
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + AppSecret + "&code=" + code + "&grant_type=authorization_code";
        JSONObject rtn = ((JSONObject) JSON.parse(publicService.simpleGetRequest(wxuserInfoAccessTokenApi)));
        return rtn;
    }


    /**
     * 获取微信用户详细信息
     * <p/>
     * 参考API第四步[拉取用户信息(需scope为 snsapi_userinfo)]：
     * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     *
     * @param accessToken 用户access_token
     * @param openId
     * @return
     */
    public JSONObject getSnsapiUserinfo(String accessToken, String openId) throws IOException {
        String wxSnsapiUserinfoApi =
                "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        JSONObject rtn = ((JSONObject) JSON.parse(publicService.simpleGetRequest(wxSnsapiUserinfoApi)));
        return rtn;
    }


}
