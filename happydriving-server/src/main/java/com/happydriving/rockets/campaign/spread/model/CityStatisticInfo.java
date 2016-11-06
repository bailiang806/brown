package com.happydriving.rockets.campaign.spread.model;

import java.math.BigDecimal;

/**
 * @author mazhiqiang
 */
public class CityStatisticInfo {

    private int cityId;
    private String cityName;
    private long todayTransferCount;
    private long yesterdayTransferCount;

    private long transferStudentCount;
    private long totalStudentCount;
    private long referrerCount;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getTodayTransferCount() {
        return todayTransferCount;
    }

    public void setTodayTransferCount(long todayTransferCount) {
        this.todayTransferCount = todayTransferCount;
    }

    public long getYesterdayTransferCount() {
        return yesterdayTransferCount;
    }

    public void setYesterdayTransferCount(long yesterdayTransferCount) {
        this.yesterdayTransferCount = yesterdayTransferCount;
    }

    public long getTransferStudentCount() {
        return transferStudentCount;
    }

    public void setTransferStudentCount(long transferStudentCount) {
        this.transferStudentCount = transferStudentCount;
    }

    public long getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(long totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public long getReferrerCount() {
        return referrerCount;
    }

    public void setReferrerCount(long referrerCount) {
        this.referrerCount = referrerCount;
    }

    public float getTransferRate() {
        return totalStudentCount == 0 ? 0 : BigDecimal.valueOf(transferStudentCount).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalStudentCount), BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
