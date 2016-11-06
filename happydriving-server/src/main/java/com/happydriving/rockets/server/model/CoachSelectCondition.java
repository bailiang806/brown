package com.happydriving.rockets.server.model;

/**
 * @author mazhiqiang
 */
public class CoachSelectCondition {

    private int carTypeId;
    private int classTypeId;

    private int priceRangeMin;
    private int priceRangeMax;

    private int provinceId;
    private int cityId;
    private int countyId;
    private int townId;

    public int getPriceRangeMin() {
        return priceRangeMin;
    }

    public void setPriceRangeMin(int priceRangeMin) {
        this.priceRangeMin = priceRangeMin;
    }

    public int getPriceRangeMax() {
        return priceRangeMax;
    }

    public void setPriceRangeMax(int priceRangeMax) {
        this.priceRangeMax = priceRangeMax;
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
}
