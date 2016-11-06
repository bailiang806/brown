package com.happydriving.rockets.server.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazhiqiang
 */
public class CoachDetailInfo {

    private String coachId;
    private String coachName;
    private String phoneNumber;

    private String urgentName;
    private String urgentPhoneNumber;

    private int provinceId;
    private String provinceName;
    private int cityId;
    private String cityName;
    private int countyId;
    private String countyName;
    private int townId;
    private String townName;
    private String detailAddress;

    private String bankId;
    private String bankName;
    private String cardUserName;
    private String cardNumber;

    private String personalImgUrl;
    private String shenfenzhengPositiveImgUrl;
    private String shenfenzhengNegativeImgUrl;
    private String jiashizhengImgUrl;
    private String xingshizhengImgUrl;
    private String jiaolianzhengImgUrl;
    private String yunyingzhengImgUrl;

    private String comment;
    private String personalitytest;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getPersonalitytest() {
        return personalitytest;
    }

    public void setPersonalitytest(String personalitytest) {
        this.personalitytest = personalitytest;
    }

    private List<CoachDetailProductInfo> coachProductInfos = new ArrayList<>();

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPersonalImgUrl() {
        return personalImgUrl;
    }

    public void setPersonalImgUrl(String personalImgUrl) {
        this.personalImgUrl = personalImgUrl;
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

    public String getUrgentName() {
        return urgentName;
    }

    public void setUrgentName(String urgentName) {
        this.urgentName = urgentName;
    }

    public String getUrgentPhoneNumber() {
        return urgentPhoneNumber;
    }

    public void setUrgentPhoneNumber(String urgentPhoneNumber) {
        this.urgentPhoneNumber = urgentPhoneNumber;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    public List<CoachDetailProductInfo> getCoachProductInfos() {
        return coachProductInfos;
    }

    public void setCoachProductInfos(List<CoachDetailProductInfo> coachProductInfos) {
        this.coachProductInfos = coachProductInfos;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }


    public static class CoachDetailProductInfo {

        private int id;

        private int carTypeId;
        private String carTypeName;

        private int classTypeId;
        private String classTypeName;

        private BigDecimal price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(int carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }

        public int getClassTypeId() {
            return classTypeId;
        }

        public void setClassTypeId(int classTypeId) {
            this.classTypeId = classTypeId;
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
    }
}
