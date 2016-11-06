package com.happydriving.rockets.server.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DrivingSchool {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.service_city_id
     *
     * @mbggenerated
     */
    private Integer serviceCityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_name
     *
     * @mbggenerated
     */
    private String schoolName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.level
     *
     * @mbggenerated
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.latitude
     *
     * @mbggenerated
     */
    private BigDecimal latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.longitude
     *
     * @mbggenerated
     */
    private BigDecimal longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    private Date lastUpdatedTxStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    private Date lastUpdatedStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.CREATED_STAMP
     *
     * @mbggenerated
     */
    private Date createdStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.LONGTITUDE
     *
     * @mbggenerated
     */
    private BigDecimal longtitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    private Date createdTxStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.is_hide
     *
     * @mbggenerated
     */
    private String isHide;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.can_select
     *
     * @mbggenerated
     */
    private String canSelect;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_detail
     *
     * @mbggenerated
     */
    private String schoolDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_imgurl
     *
     * @mbggenerated
     */
    private String schoolImgurl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_avator
     *
     * @mbggenerated
     */
    private String schoolAvator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_hd
     *
     * @mbggenerated
     */
    private String schoolHd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.school_index
     *
     * @mbggenerated
     */
    private String schoolIndex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.short_address
     *
     * @mbggenerated
     */
    private String shortAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.register_number
     *
     * @mbggenerated
     */
    private Integer registerNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.bus_line
     *
     * @mbggenerated
     */
    private String busLine;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.practice_arrangement
     *
     * @mbggenerated
     */
    private String practiceArrangement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.car_number
     *
     * @mbggenerated
     */
    private Integer carNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.area
     *
     * @mbggenerated
     */
    private String area;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.location_number
     *
     * @mbggenerated
     */
    private Integer locationNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.score
     *
     * @mbggenerated
     */
    private Integer score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column driving_school.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.id
     *
     * @return the value of driving_school.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.id
     *
     * @param id the value for driving_school.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.service_city_id
     *
     * @return the value of driving_school.service_city_id
     *
     * @mbggenerated
     */
    public Integer getServiceCityId() {
        return serviceCityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.service_city_id
     *
     * @param serviceCityId the value for driving_school.service_city_id
     *
     * @mbggenerated
     */
    public void setServiceCityId(Integer serviceCityId) {
        this.serviceCityId = serviceCityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_name
     *
     * @return the value of driving_school.school_name
     *
     * @mbggenerated
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_name
     *
     * @param schoolName the value for driving_school.school_name
     *
     * @mbggenerated
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.level
     *
     * @return the value of driving_school.level
     *
     * @mbggenerated
     */
    public String getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.level
     *
     * @param level the value for driving_school.level
     *
     * @mbggenerated
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.address
     *
     * @return the value of driving_school.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.address
     *
     * @param address the value for driving_school.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.latitude
     *
     * @return the value of driving_school.latitude
     *
     * @mbggenerated
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.latitude
     *
     * @param latitude the value for driving_school.latitude
     *
     * @mbggenerated
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.longitude
     *
     * @return the value of driving_school.longitude
     *
     * @mbggenerated
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.longitude
     *
     * @param longitude the value for driving_school.longitude
     *
     * @mbggenerated
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.LAST_UPDATED_TX_STAMP
     *
     * @return the value of driving_school.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.LAST_UPDATED_TX_STAMP
     *
     * @param lastUpdatedTxStamp the value for driving_school.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.LAST_UPDATED_STAMP
     *
     * @return the value of driving_school.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.LAST_UPDATED_STAMP
     *
     * @param lastUpdatedStamp the value for driving_school.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.CREATED_STAMP
     *
     * @return the value of driving_school.CREATED_STAMP
     *
     * @mbggenerated
     */
    public Date getCreatedStamp() {
        return createdStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.CREATED_STAMP
     *
     * @param createdStamp the value for driving_school.CREATED_STAMP
     *
     * @mbggenerated
     */
    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.LONGTITUDE
     *
     * @return the value of driving_school.LONGTITUDE
     *
     * @mbggenerated
     */
    public BigDecimal getLongtitude() {
        return longtitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.LONGTITUDE
     *
     * @param longtitude the value for driving_school.LONGTITUDE
     *
     * @mbggenerated
     */
    public void setLongtitude(BigDecimal longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.CREATED_TX_STAMP
     *
     * @return the value of driving_school.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.CREATED_TX_STAMP
     *
     * @param createdTxStamp the value for driving_school.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.is_hide
     *
     * @return the value of driving_school.is_hide
     *
     * @mbggenerated
     */
    public String getIsHide() {
        return isHide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.is_hide
     *
     * @param isHide the value for driving_school.is_hide
     *
     * @mbggenerated
     */
    public void setIsHide(String isHide) {
        this.isHide = isHide == null ? null : isHide.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.can_select
     *
     * @return the value of driving_school.can_select
     *
     * @mbggenerated
     */
    public String getCanSelect() {
        return canSelect;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.can_select
     *
     * @param canSelect the value for driving_school.can_select
     *
     * @mbggenerated
     */
    public void setCanSelect(String canSelect) {
        this.canSelect = canSelect == null ? null : canSelect.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.price
     *
     * @return the value of driving_school.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.price
     *
     * @param price the value for driving_school.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_detail
     *
     * @return the value of driving_school.school_detail
     *
     * @mbggenerated
     */
    public String getSchoolDetail() {
        return schoolDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_detail
     *
     * @param schoolDetail the value for driving_school.school_detail
     *
     * @mbggenerated
     */
    public void setSchoolDetail(String schoolDetail) {
        this.schoolDetail = schoolDetail == null ? null : schoolDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_imgurl
     *
     * @return the value of driving_school.school_imgurl
     *
     * @mbggenerated
     */
    public String getSchoolImgurl() {
        return schoolImgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_imgurl
     *
     * @param schoolImgurl the value for driving_school.school_imgurl
     *
     * @mbggenerated
     */
    public void setSchoolImgurl(String schoolImgurl) {
        this.schoolImgurl = schoolImgurl == null ? null : schoolImgurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_avator
     *
     * @return the value of driving_school.school_avator
     *
     * @mbggenerated
     */
    public String getSchoolAvator() {
        return schoolAvator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_avator
     *
     * @param schoolAvator the value for driving_school.school_avator
     *
     * @mbggenerated
     */
    public void setSchoolAvator(String schoolAvator) {
        this.schoolAvator = schoolAvator == null ? null : schoolAvator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_hd
     *
     * @return the value of driving_school.school_hd
     *
     * @mbggenerated
     */
    public String getSchoolHd() {
        return schoolHd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_hd
     *
     * @param schoolHd the value for driving_school.school_hd
     *
     * @mbggenerated
     */
    public void setSchoolHd(String schoolHd) {
        this.schoolHd = schoolHd == null ? null : schoolHd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.school_index
     *
     * @return the value of driving_school.school_index
     *
     * @mbggenerated
     */
    public String getSchoolIndex() {
        return schoolIndex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.school_index
     *
     * @param schoolIndex the value for driving_school.school_index
     *
     * @mbggenerated
     */
    public void setSchoolIndex(String schoolIndex) {
        this.schoolIndex = schoolIndex == null ? null : schoolIndex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.short_address
     *
     * @return the value of driving_school.short_address
     *
     * @mbggenerated
     */
    public String getShortAddress() {
        return shortAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.short_address
     *
     * @param shortAddress the value for driving_school.short_address
     *
     * @mbggenerated
     */
    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress == null ? null : shortAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.register_number
     *
     * @return the value of driving_school.register_number
     *
     * @mbggenerated
     */
    public Integer getRegisterNumber() {
        return registerNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.register_number
     *
     * @param registerNumber the value for driving_school.register_number
     *
     * @mbggenerated
     */
    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.bus_line
     *
     * @return the value of driving_school.bus_line
     *
     * @mbggenerated
     */
    public String getBusLine() {
        return busLine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.bus_line
     *
     * @param busLine the value for driving_school.bus_line
     *
     * @mbggenerated
     */
    public void setBusLine(String busLine) {
        this.busLine = busLine == null ? null : busLine.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.practice_arrangement
     *
     * @return the value of driving_school.practice_arrangement
     *
     * @mbggenerated
     */
    public String getPracticeArrangement() {
        return practiceArrangement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.practice_arrangement
     *
     * @param practiceArrangement the value for driving_school.practice_arrangement
     *
     * @mbggenerated
     */
    public void setPracticeArrangement(String practiceArrangement) {
        this.practiceArrangement = practiceArrangement == null ? null : practiceArrangement.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.car_number
     *
     * @return the value of driving_school.car_number
     *
     * @mbggenerated
     */
    public Integer getCarNumber() {
        return carNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.car_number
     *
     * @param carNumber the value for driving_school.car_number
     *
     * @mbggenerated
     */
    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.area
     *
     * @return the value of driving_school.area
     *
     * @mbggenerated
     */
    public String getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.area
     *
     * @param area the value for driving_school.area
     *
     * @mbggenerated
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.location_number
     *
     * @return the value of driving_school.location_number
     *
     * @mbggenerated
     */
    public Integer getLocationNumber() {
        return locationNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.location_number
     *
     * @param locationNumber the value for driving_school.location_number
     *
     * @mbggenerated
     */
    public void setLocationNumber(Integer locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.score
     *
     * @return the value of driving_school.score
     *
     * @mbggenerated
     */
    public Integer getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.score
     *
     * @param score the value for driving_school.score
     *
     * @mbggenerated
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column driving_school.phone
     *
     * @return the value of driving_school.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column driving_school.phone
     *
     * @param phone the value for driving_school.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}