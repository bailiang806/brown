package com.happydriving.rockets.campaign.spread.model;

import com.happydriving.rockets.server.common.CommonUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mazhiqiang
 */
public class SpreadReferrerStatisticInfo {

    private int referrerId;
    private String referrerName;
    private String referrerPhone;
    private Date createAt;

    private int totalReferrerCount;
    private int totalTransferCount;

    public int getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(int referrerId) {
        this.referrerId = referrerId;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getTotalReferrerCount() {
        return totalReferrerCount;
    }

    public void setTotalReferrerCount(int totalReferrerCount) {
        this.totalReferrerCount = totalReferrerCount;
    }

    public int getTotalTransferCount() {
        return totalTransferCount;
    }

    public void setTotalTransferCount(int totalTransferCount) {
        this.totalTransferCount = totalTransferCount;
    }

    public String getCreateAtString() {
        return CommonUtils.transferDateToString(createAt);
    }

    public float getTransferRate() {
        return totalReferrerCount == 0 ? 0 : BigDecimal.valueOf(totalTransferCount).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalReferrerCount), BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public float getDailyAverageReferrerCount() {
        int days = Days.daysBetween(new DateTime(createAt), DateTime.now()).getDays();
        return days == 0 ? 0 : BigDecimal.valueOf(totalReferrerCount)
                .divide(BigDecimal.valueOf(days), BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public float getDailyAverageTransferCount() {
        int days = Days.daysBetween(new DateTime(createAt), DateTime.now()).getDays();
        return days == 0 ? 0 : BigDecimal.valueOf(totalTransferCount)
                .divide(BigDecimal.valueOf(days), BigDecimal.ROUND_HALF_UP).floatValue();
    }

//    public static void main(String[] args) {
//        BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.TEN);
//
//        System.out.println(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(7),
//                BigDecimal.ROUND_HALF_UP).floatValue());
//    }
}
