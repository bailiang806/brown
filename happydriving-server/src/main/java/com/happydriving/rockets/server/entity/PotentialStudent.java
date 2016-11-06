package com.happydriving.rockets.server.entity;

import java.util.Date;

public class PotentialStudent {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column potential_student.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column potential_student.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column potential_student.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column potential_student.city
     *
     * @mbggenerated
     */
    private String city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column potential_student.timestamp
     *
     * @mbggenerated
     */
    private Date timestamp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column potential_student.id
     *
     * @return the value of potential_student.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column potential_student.id
     *
     * @param id the value for potential_student.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column potential_student.phone
     *
     * @return the value of potential_student.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column potential_student.phone
     *
     * @param phone the value for potential_student.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column potential_student.name
     *
     * @return the value of potential_student.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column potential_student.name
     *
     * @param name the value for potential_student.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column potential_student.city
     *
     * @return the value of potential_student.city
     *
     * @mbggenerated
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column potential_student.city
     *
     * @param city the value for potential_student.city
     *
     * @mbggenerated
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column potential_student.timestamp
     *
     * @return the value of potential_student.timestamp
     *
     * @mbggenerated
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column potential_student.timestamp
     *
     * @param timestamp the value for potential_student.timestamp
     *
     * @mbggenerated
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}