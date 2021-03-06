package com.happydriving.rockets.server.entity;

import java.util.Date;

public class SmsValidation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_validation.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_validation.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_validation.random_code
     *
     * @mbggenerated
     */
    private String randomCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_validation.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_validation.id
     *
     * @return the value of sms_validation.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_validation.id
     *
     * @param id the value for sms_validation.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_validation.phone
     *
     * @return the value of sms_validation.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_validation.phone
     *
     * @param phone the value for sms_validation.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_validation.random_code
     *
     * @return the value of sms_validation.random_code
     *
     * @mbggenerated
     */
    public String getRandomCode() {
        return randomCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_validation.random_code
     *
     * @param randomCode the value for sms_validation.random_code
     *
     * @mbggenerated
     */
    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode == null ? null : randomCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_validation.created_at
     *
     * @return the value of sms_validation.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_validation.created_at
     *
     * @param createdAt the value for sms_validation.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SmsValidation{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", randomCode='" + randomCode + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}