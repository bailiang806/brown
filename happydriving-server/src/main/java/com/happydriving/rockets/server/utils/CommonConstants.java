package com.happydriving.rockets.server.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mazhiqiang
 */
public class CommonConstants {

    public static final Map<Integer, String> CITY_TO_MAILBOX_MAP = new HashMap<>(2);

    static {
        CITY_TO_MAILBOX_MAP.put(2, "info_xiamen@ejiapei.com");
        CITY_TO_MAILBOX_MAP.put(1, "info_fuzhou@ejiapei.com");
    }

    public static final int BCRYPT_SALT_ROUND = 10;

    public static final long RANDOM_CODE_DURATION_MILLS = 5 * 60 * 1000L;

    public static final String SESSION_USER_ID = "sessionId";
    public static final String SESSION_PHONENUMBER = "phoneNumber";
    public static final String SESSION_ROLE = "role";
    public static final String SESSION_OPENID = "openId";


    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_STUDENT = "student";
    public static final String ROLE_COACH = "coach";
    public static final String ROLE_GUEST = "guest";

    public static final String JSONP_CALLBACK_FUNCTION = "callback";

    public static final String DEFALUT_IMG_PLACEHOLDER = "../images/placeHolder.png";

    public static final String AUDIT_UNSUBMIT = "未提交";
    public static final String AUDIT_PROCESSING = "待审核";
    public static final String AUDIT_DENIED = "未通过";
    public static final String AUDIT_SUCCEED = "已通过";

    public static final int DEFAULT_SCALE = 2;
    public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;


}
