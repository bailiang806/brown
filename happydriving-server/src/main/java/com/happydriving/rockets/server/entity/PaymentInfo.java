package com.happydriving.rockets.server.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.school_id
     *
     * @mbggenerated
     */
    private String schoolId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    private Date lastUpdatedTxStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    private Date lastUpdatedStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.CREATED_STAMP
     *
     * @mbggenerated
     */
    private Date createdStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    private Date createdTxStamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.trade_state
     *
     * @mbggenerated
     */
    private String tradeState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.out_trade_no
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_info.third_party_trade_no
     *
     * @mbggenerated
     */
    private String thirdPartyTradeNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.order_id
     *
     * @return the value of payment_info.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.order_id
     *
     * @param orderId the value for payment_info.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.phone
     *
     * @return the value of payment_info.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.phone
     *
     * @param phone the value for payment_info.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.school_id
     *
     * @return the value of payment_info.school_id
     *
     * @mbggenerated
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.school_id
     *
     * @param schoolId the value for payment_info.school_id
     *
     * @mbggenerated
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId == null ? null : schoolId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.price
     *
     * @return the value of payment_info.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.price
     *
     * @param price the value for payment_info.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.LAST_UPDATED_TX_STAMP
     *
     * @return the value of payment_info.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.LAST_UPDATED_TX_STAMP
     *
     * @param lastUpdatedTxStamp the value for payment_info.LAST_UPDATED_TX_STAMP
     *
     * @mbggenerated
     */
    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.LAST_UPDATED_STAMP
     *
     * @return the value of payment_info.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.LAST_UPDATED_STAMP
     *
     * @param lastUpdatedStamp the value for payment_info.LAST_UPDATED_STAMP
     *
     * @mbggenerated
     */
    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.CREATED_STAMP
     *
     * @return the value of payment_info.CREATED_STAMP
     *
     * @mbggenerated
     */
    public Date getCreatedStamp() {
        return createdStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.CREATED_STAMP
     *
     * @param createdStamp the value for payment_info.CREATED_STAMP
     *
     * @mbggenerated
     */
    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.CREATED_TX_STAMP
     *
     * @return the value of payment_info.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.CREATED_TX_STAMP
     *
     * @param createdTxStamp the value for payment_info.CREATED_TX_STAMP
     *
     * @mbggenerated
     */
    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.name
     *
     * @return the value of payment_info.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.name
     *
     * @param name the value for payment_info.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.trade_state
     *
     * @return the value of payment_info.trade_state
     *
     * @mbggenerated
     */
    public String getTradeState() {
        return tradeState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.trade_state
     *
     * @param tradeState the value for payment_info.trade_state
     *
     * @mbggenerated
     */
    public void setTradeState(String tradeState) {
        this.tradeState = tradeState == null ? null : tradeState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.out_trade_no
     *
     * @return the value of payment_info.out_trade_no
     *
     * @mbggenerated
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.out_trade_no
     *
     * @param outTradeNo the value for payment_info.out_trade_no
     *
     * @mbggenerated
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_info.third_party_trade_no
     *
     * @return the value of payment_info.third_party_trade_no
     *
     * @mbggenerated
     */
    public String getThirdPartyTradeNo() {
        return thirdPartyTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_info.third_party_trade_no
     *
     * @param thirdPartyTradeNo the value for payment_info.third_party_trade_no
     *
     * @mbggenerated
     */
    public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
        this.thirdPartyTradeNo = thirdPartyTradeNo == null ? null : thirdPartyTradeNo.trim();
    }
}