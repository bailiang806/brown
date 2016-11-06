package com.happydriving.rockets.server.model;

import java.util.List;

/**
 * 用于教练展示信息的数据对象
 * @author mazhiqiang
 */
public class CoachDisplayDetailInfo {

    private int coachId;
    private String coachName;
    private String coachSex;

    private int cityId;
    private String cityName;

    private int schoolId;
    private String schoolName;

    private String phoneNumber;

    private int studentCount;
    private int passingRate;

    private String coachAvator;
    private String coachUploadPhoto;
    private List<String> coachImgUrl;

    private String shenfenzhengPositiveImgUrl;
    private String shenfenzhengNegativeImgUrl;
    private String jiashizhengImgUrl;
    private String xingshizhengImgUrl;
    private String jiaolianzhengImgUrl;
    private String yunyingzhengImgUrl;

    private String coachHd;
    private List<String> coachHdUrls;

    public String getCoachHd() {
        return coachHd;
    }

    public void setCoachHd(String coachHd) {
        this.coachHd = coachHd;
    }

    public List<String> getCoachHdUrls() {
        return coachHdUrls;
    }

    public void setCoachHdUrls(List<String> coachHdUrls) {
        this.coachHdUrls = coachHdUrls;
    }

    public String getShenfenzhengPositiveImgUrl() {
        return shenfenzhengPositiveImgUrl;
    }

    public void setShenfenzhengPositiveImgUrl(String shenfenzhengPositiveImgUrl) {
        this.shenfenzhengPositiveImgUrl = shenfenzhengPositiveImgUrl;
    }

    public String getShenfenzhengNegativeImgUrl() {
        return shenfenzhengNegativeImgUrl;
    }

    public void setShenfenzhengNegativeImgUrl(String shenfenzhengNegativeImgUrl) {
        this.shenfenzhengNegativeImgUrl = shenfenzhengNegativeImgUrl;
    }

    public String getJiashizhengImgUrl() {
        return jiashizhengImgUrl;
    }

    public void setJiashizhengImgUrl(String jiashizhengImgUrl) {
        this.jiashizhengImgUrl = jiashizhengImgUrl;
    }

    public String getXingshizhengImgUrl() {
        return xingshizhengImgUrl;
    }

    public void setXingshizhengImgUrl(String xingshizhengImgUrl) {
        this.xingshizhengImgUrl = xingshizhengImgUrl;
    }

    public String getJiaolianzhengImgUrl() {
        return jiaolianzhengImgUrl;
    }

    public void setJiaolianzhengImgUrl(String jiaolianzhengImgUrl) {
        this.jiaolianzhengImgUrl = jiaolianzhengImgUrl;
    }

    public String getYunyingzhengImgUrl() {
        return yunyingzhengImgUrl;
    }

    public void setYunyingzhengImgUrl(String yunyingzhengImgUrl) {
        this.yunyingzhengImgUrl = yunyingzhengImgUrl;
    }

    public String getCoachUploadPhoto() {
        return coachUploadPhoto;
    }

    public void setCoachUploadPhoto(String coachUploadPhoto) {
        this.coachUploadPhoto = coachUploadPhoto;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachSex() {
        return coachSex;
    }

    public void setCoachSex(String coachSex) {
        this.coachSex = coachSex;
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

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getPassingRate() {
        return passingRate;
    }

    public void setPassingRate(int passingRate) {
        this.passingRate = passingRate;
    }

    public String getCoachAvator() {
        return coachAvator;
    }

    public void setCoachAvator(String coachAvator) {
        this.coachAvator = coachAvator;
    }

    public List<String> getCoachImgUrl() {
        return coachImgUrl;
    }

    public void setCoachImgUrl(List<String> coachImgUrl) {
        this.coachImgUrl = coachImgUrl;
    }


}
