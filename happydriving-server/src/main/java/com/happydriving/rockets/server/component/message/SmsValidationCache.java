package com.happydriving.rockets.server.component.message;

import com.happydriving.rockets.server.utils.CommonConstants;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 已经由数据库来实现来替换内存级实现
 * @author mazhiqiang
 */
@Component
@Deprecated
public class SmsValidationCache {

    public Map<String, SmsValidationInfo> phoneNumberToValidationMap = new HashMap<>();

    public void addSmsValidationInfo(String phoneNumber, String randomCode) {
        if (phoneNumberToValidationMap.containsKey(phoneNumber)) {
            phoneNumberToValidationMap.remove(phoneNumber);
        }
        phoneNumberToValidationMap.put(phoneNumber, new SmsValidationInfo(randomCode, new DateTime()));
    }

    public boolean validateSmsInfo(String phoneNumber, String inputCode) {
        if (!phoneNumberToValidationMap.containsKey(phoneNumber)) {
            return false;
        }
        return phoneNumberToValidationMap.get(phoneNumber).validateInfo(inputCode);
    }

    public static class SmsValidationInfo {
        private final String randomCode;
        private final DateTime generateTime;

        public SmsValidationInfo(String randomCode, DateTime generateTime) {
            this.randomCode = randomCode;
            this.generateTime = generateTime;
        }

        public boolean validateInfo(String inputCode) {
            DateTime currentTime = new DateTime();
            Duration duration = new Duration(generateTime, currentTime);
            return inputCode.equals(randomCode) && duration.getMillis() < CommonConstants.RANDOM_CODE_DURATION_MILLS;

        }
    }

}
