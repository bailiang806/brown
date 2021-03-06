package com.happydriving.rockets.campaign.spread.entity;

import java.util.Date;

public class SpreadReferrer {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.password_digest
     *
     * @mbggenerated
     */
    private String passwordDigest;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.remember_token
     *
     * @mbggenerated
     */
    private String rememberToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spread_referrer.city_id
     *
     * @mbggenerated
     */
    private Integer cityId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.id
     *
     * @return the value of spread_referrer.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.id
     *
     * @param id the value for spread_referrer.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.phone
     *
     * @return the value of spread_referrer.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.phone
     *
     * @param phone the value for spread_referrer.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.name
     *
     * @return the value of spread_referrer.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.name
     *
     * @param name the value for spread_referrer.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.password_digest
     *
     * @return the value of spread_referrer.password_digest
     *
     * @mbggenerated
     */
    public String getPasswordDigest() {
        return passwordDigest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.password_digest
     *
     * @param passwordDigest the value for spread_referrer.password_digest
     *
     * @mbggenerated
     */
    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest == null ? null : passwordDigest.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.remember_token
     *
     * @return the value of spread_referrer.remember_token
     *
     * @mbggenerated
     */
    public String getRememberToken() {
        return rememberToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.remember_token
     *
     * @param rememberToken the value for spread_referrer.remember_token
     *
     * @mbggenerated
     */
    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken == null ? null : rememberToken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.created_at
     *
     * @return the value of spread_referrer.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.created_at
     *
     * @param createdAt the value for spread_referrer.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spread_referrer.city_id
     *
     * @return the value of spread_referrer.city_id
     *
     * @mbggenerated
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spread_referrer.city_id
     *
     * @param cityId the value for spread_referrer.city_id
     *
     * @mbggenerated
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}