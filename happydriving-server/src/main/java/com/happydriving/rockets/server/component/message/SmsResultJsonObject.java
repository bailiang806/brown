package com.happydriving.rockets.server.component.message;

import com.happydriving.rockets.server.common.json.ResponseJsonObject;

/**
 * 在原有ResponseJsonObject中增加了电话号码和验证码的相关属性
 *
 * <p></p>
 *
 * 可重构为ResponseJsonObject中的returnObject
 *
 * @author mazhiqiang
 */
public class SmsResultJsonObject extends ResponseJsonObject {

    private String phoneNumber;
    private String randomCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}
