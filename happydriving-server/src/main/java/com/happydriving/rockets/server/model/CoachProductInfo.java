package com.happydriving.rockets.server.model;

import java.math.BigDecimal;

/**
 * @author mazhiqiang
 */
public class CoachProductInfo {

    private int id;
    private String coachId;
    private String coachName;
    private String coachPersonalImgUrl;

    private int carTypeId;
    private String carTypeName;

    private int classTypeId;
    private String classTypeName;

    private BigDecimal price;

    private int coachProductId;

    private String provinceName;
    private String cityName;
    private String countyName;
    private String townName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public int getClassTypeId() {
        return classTypeId;
    }

    public void setClassTypeId(int classTypeId) {
        this.classTypeId = classTypeId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachPersonalImgUrl() {
        return coachPersonalImgUrl;
    }

    public void setCoachPersonalImgUrl(String coachPersonalImgUrl) {
        this.coachPersonalImgUrl = coachPersonalImgUrl;
    }

    public int getCoachProductId() {
        return coachProductId;
    }

    public void setCoachProductId(int coachProductId) {
        this.coachProductId = coachProductId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }


    @Override
    public String toString() {
        return "CoachProductInfo{" +
                "id=" + id +
                ", coachId='" + coachId + '\'' +
                ", coachName='" + coachName + '\'' +
                ", coachPersonalImgUrl='" + coachPersonalImgUrl + '\'' +
                ", carTypeId=" + carTypeId +
                ", carTypeName='" + carTypeName + '\'' +
                ", classTypeId=" + classTypeId +
                ", classTypeName='" + classTypeName + '\'' +
                ", price='" + price + '\'' +
                ", coachProductId=" + coachProductId +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyName='" + countyName + '\'' +
                ", townName='" + townName + '\'' +
                '}';
    }
}
