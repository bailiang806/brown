package com.happydriving.rockets.server.model;

/**
 * Use this object to select in index.jsp page.
 *
 * @author mazhiqiang
 */
public class CoachTopCountQueryInfo {

    private int id;

    private String coachTxImgUrl;
    private String shenfenzhengPositiveImgUrl;
    private String shenfenzhengNegativeImgUrl;
    private String jiashizhengImgUrl;
    private String xingshizhengImgUrl;
    private String jiaolianzhengImgUrl;
    private String yunyingzhengImgUrl;

    private String coachName;
    private String comment;

    private String carTypeName;

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

    public String getCoachTxImgUrl() {
        return coachTxImgUrl;
    }

    public void setCoachTxImgUrl(String coachTxImgUrl) {
        this.coachTxImgUrl = coachTxImgUrl;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
