package com.happydriving.rockets.server.entity;

import com.happydriving.rockets.server.common.BusinessRuntimeException;

import java.util.Date;

public class PaymentResult {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.xueyuan_id
     *
     * @mbggenerated
     */
    private Integer xueyuanId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.coach_product_id
     *
     * @mbggenerated
     */
    private Integer coachProductId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.channel
     *
     * @mbggenerated
     */
    private Integer channel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.out_trade_no
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.third_party_trade_no
     *
     * @mbggenerated
     */
    private String thirdPartyTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.trade_state
     *
     * @mbggenerated
     */
    private Integer tradeState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_result.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.id
     *
     * @return the value of payment_result.id
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.id
     *
     * @param id the value for payment_result.id
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.xueyuan_id
     *
     * @return the value of payment_result.xueyuan_id
     * @mbggenerated
     */
    public Integer getXueyuanId() {
        return xueyuanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.xueyuan_id
     *
     * @param xueyuanId the value for payment_result.xueyuan_id
     * @mbggenerated
     */
    public void setXueyuanId(Integer xueyuanId) {
        this.xueyuanId = xueyuanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.coach_product_id
     *
     * @return the value of payment_result.coach_product_id
     * @mbggenerated
     */
    public Integer getCoachProductId() {
        return coachProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.coach_product_id
     *
     * @param coachProductId the value for payment_result.coach_product_id
     * @mbggenerated
     */
    public void setCoachProductId(Integer coachProductId) {
        this.coachProductId = coachProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.channel
     *
     * @return the value of payment_result.channel
     * @mbggenerated
     */
    public Integer getChannel() {
        return channel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.channel
     *
     * @param channel the value for payment_result.channel
     * @mbggenerated
     */
    public void setChannelEnum(CHANNEL channel) {
        this.channel = channel.getValue();
    }

    public void setChannel(int channel) {
        for (CHANNEL channelEnum : CHANNEL.values()) {
            if (channelEnum.getValue() == channel) {
                this.channel = channel;
                return;
            }
        }
        throw new BusinessRuntimeException(String.format("Channel :%s is invalid!", channel));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.out_trade_no
     *
     * @return the value of payment_result.out_trade_no
     * @mbggenerated
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.out_trade_no
     *
     * @param outTradeNo the value for payment_result.out_trade_no
     * @mbggenerated
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.third_party_trade_no
     *
     * @return the value of payment_result.third_party_trade_no
     * @mbggenerated
     */
    public String getThirdPartyTradeNo() {
        return thirdPartyTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.third_party_trade_no
     *
     * @param thirdPartyTradeNo the value for payment_result.third_party_trade_no
     * @mbggenerated
     */
    public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
        this.thirdPartyTradeNo = thirdPartyTradeNo == null ? null : thirdPartyTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.trade_state
     *
     * @return the value of payment_result.trade_state
     * @mbggenerated
     */
    public Integer getTradeState() {
        return tradeState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.trade_state
     *
     * @param tradeState the value for payment_result.trade_state
     * @mbggenerated
     */
    public void setTradeStateEnum(TRADE_STATE tradeState) {
        this.tradeState = tradeState.getValue();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.trade_state
     *
     * @param tradeState the value for payment_result.trade_state
     * @mbggenerated
     */
    public void setTradeState(int tradeState) {
        for (TRADE_STATE tradeStateEnum : TRADE_STATE.values()) {
            if (tradeStateEnum.getValue() == tradeState) {
                this.tradeState = tradeState;
                return;
            }
        }
        throw new BusinessRuntimeException(String.format("Trade state :%s is invalid!", tradeState));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.created_at
     *
     * @return the value of payment_result.created_at
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.created_at
     *
     * @param createdAt the value for payment_result.created_at
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_result.updated_at
     *
     * @return the value of payment_result.updated_at
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_result.updated_at
     *
     * @param updatedAt the value for payment_result.updated_at
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "PaymentResult{" +
                "id=" + id +
                ", xueyuanId=" + xueyuanId +
                ", coachProductId=" + coachProductId +
                ", channel=" + channel +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", thirdPartyTradeNo='" + thirdPartyTradeNo + '\'' +
                ", tradeState=" + tradeState +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * 付款渠道
     */
    public static enum CHANNEL {
        WEIXIN(0),  //微信支付
        ALIPAY(1);  //支付宝支付

        private final int id;

        CHANNEL(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    /**
     * 付款状态码
     */
    public static enum TRADE_STATE {
        ONGOING(0),  //正在执行中
        PAYED_SUCCESS(1),  //付款成功
        FEE_INCONSISTENT(2);    //支付宝：付款金额与教练产品不一致

        private final int id;

        TRADE_STATE(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }
}