package com.happydriving.rockets.server.entity;

public class BankInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bank_info.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bank_info.bank_name
     *
     * @mbggenerated
     */
    private String bankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bank_info.bank_code
     *
     * @mbggenerated
     */
    private String bankCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank_info.ID
     *
     * @return the value of bank_info.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank_info.ID
     *
     * @param id the value for bank_info.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank_info.bank_name
     *
     * @return the value of bank_info.bank_name
     *
     * @mbggenerated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank_info.bank_name
     *
     * @param bankName the value for bank_info.bank_name
     *
     * @mbggenerated
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bank_info.bank_code
     *
     * @return the value of bank_info.bank_code
     *
     * @mbggenerated
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bank_info.bank_code
     *
     * @param bankCode the value for bank_info.bank_code
     *
     * @mbggenerated
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }
}