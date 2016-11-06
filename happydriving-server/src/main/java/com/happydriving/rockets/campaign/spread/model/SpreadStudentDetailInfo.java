package com.happydriving.rockets.campaign.spread.model;

import com.happydriving.rockets.server.common.CommonUtils;

import java.util.Date;

/**
 * @author mazhiqiang
 */
public class SpreadStudentDetailInfo {

    private int studentId;
    private String studentName;
    private String studentPhone;

    private Date createdAt;

    private String studentComment;
    private boolean isTransfer;
    private Date transferAt;

    private int cityId;
    private String cityName;

    private String referrerId;
    private String referrerName;
    private String referrerPhone;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {
        this.studentComment = studentComment;
    }

    public boolean isTransfer() {
        return isTransfer;
    }

    public void setTransfer(boolean isTransfer) {
        this.isTransfer = isTransfer;
    }

    public Date getTransferAt() {
        return transferAt;
    }

    public void setTransferAt(Date transferAt) {
        this.transferAt = transferAt;
    }

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

    public String getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(String referrerId) {
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

    public String getCreateAtString() {
        return CommonUtils.transferDateToString(createdAt);
    }

    public String getTransferAtString() {
        return CommonUtils.transferDateToString(transferAt);
    }
}
