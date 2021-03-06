package com.happydriving.rockets.server.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CoachProduct {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.cartype_id
     *
     * @mbggenerated
     */
    private Integer cartypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.classtype_id
     *
     * @mbggenerated
     */
    private Integer classtypeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column coach_product.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.id
     *
     * @return the value of coach_product.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.id
     *
     * @param id the value for coach_product.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.user_id
     *
     * @return the value of coach_product.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.user_id
     *
     * @param userId the value for coach_product.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.created_at
     *
     * @return the value of coach_product.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.created_at
     *
     * @param createdAt the value for coach_product.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.updated_at
     *
     * @return the value of coach_product.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.updated_at
     *
     * @param updatedAt the value for coach_product.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.cartype_id
     *
     * @return the value of coach_product.cartype_id
     *
     * @mbggenerated
     */
    public Integer getCartypeId() {
        return cartypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.cartype_id
     *
     * @param cartypeId the value for coach_product.cartype_id
     *
     * @mbggenerated
     */
    public void setCartypeId(Integer cartypeId) {
        this.cartypeId = cartypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.classtype_id
     *
     * @return the value of coach_product.classtype_id
     *
     * @mbggenerated
     */
    public Integer getClasstypeId() {
        return classtypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.classtype_id
     *
     * @param classtypeId the value for coach_product.classtype_id
     *
     * @mbggenerated
     */
    public void setClasstypeId(Integer classtypeId) {
        this.classtypeId = classtypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column coach_product.price
     *
     * @return the value of coach_product.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column coach_product.price
     *
     * @param price the value for coach_product.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}