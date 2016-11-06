package com.happydriving.rockets.server.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用来显示驾校详情的相关类，包括了驾校表，驾校详细表的合并数据
 * @author mazhiqiang
 */
public class DrivingSchoolDetailInfo {

    private int cityId;
    private String cityName;

    private int schoolId;
    private String schoolName;
    private String schoolAddress;
    private BigDecimal schoolLatitude;
    private BigDecimal schoolLongtitude;
    private String schoolDetail;
    private String schoolAvator;
    private String schoolImgUrl;
    private String canSelect;
    private BigDecimal price;
    private String schoolHd;
    private String schoolIndex;
    private String schoolLevel;
    private String shortAddress;
    private int registerNumber;
    private String busLine;
    private String practiceArrangement;
    private int carNumber;
    private String area;
    private int locationNumber;
    private int score;
    private String phone;

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(int registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getPracticeArrangement() {
        return practiceArrangement;
    }

    public void setPracticeArrangement(String practiceArrangement) {
        this.practiceArrangement = practiceArrangement;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private List<String> schoolImgUrls;
    private List<String> schoolHdurls;

    public String getSchoolIndex(){return schoolIndex;}

    public void setSchoolIndex(String schoolIndex){this.schoolIndex = schoolIndex;}

    public String getSchoolHd(){return schoolHd;}

    public void setSchoolHd(String schoolHd){this.schoolHd = schoolHd;}

    public List<String> getSchoolHdurls(){return schoolHdurls;}

    public void setSchoolHdurls(List<String> schoolHdurls){this.schoolHdurls = schoolHdurls;}

    public List<String> getSchoolImgUrls() {
        return schoolImgUrls;
    }

    public void setSchoolImgUrls(List<String> schoolImgUrls) {
        this.schoolImgUrls = schoolImgUrls;
    }

    public String getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(String canSelect) {
        this.canSelect = canSelect;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public BigDecimal getSchoolLatitude() {
        return schoolLatitude;
    }

    public void setSchoolLatitude(BigDecimal schoolLatitude) {
        this.schoolLatitude = schoolLatitude;
    }

    public BigDecimal getSchoolLongtitude() {
        return schoolLongtitude;
    }

    public void setSchoolLongtitude(BigDecimal schoolLongtitude) {
        this.schoolLongtitude = schoolLongtitude;
    }

    public String getSchoolDetail() {
        return schoolDetail;
    }

    public void setSchoolDetail(String schoolDetail) {
        this.schoolDetail = schoolDetail;
    }

    public String getSchoolAvator() {
        return schoolAvator;
    }

    public void setSchoolAvator(String schoolAvator) {
        this.schoolAvator = schoolAvator;
    }

    public String getSchoolImgUrl() {
        return schoolImgUrl;
    }

    public void setSchoolImgUrl(String schoolImgUrl) {
        this.schoolImgUrl = schoolImgUrl;
    }
}
