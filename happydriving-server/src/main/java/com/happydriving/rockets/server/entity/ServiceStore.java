package com.happydriving.rockets.server.entity;

public class ServiceStore {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.service_city_id
     *
     * @mbggenerated
     */
    private Integer serviceCityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.address
     *
     * @mbggenerated
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_store.head
     *
     * @mbggenerated
     */
    private String head;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.id
     *
     * @return the value of service_store.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.id
     *
     * @param id the value for service_store.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.service_city_id
     *
     * @return the value of service_store.service_city_id
     *
     * @mbggenerated
     */
    public Integer getServiceCityId() {
        return serviceCityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.service_city_id
     *
     * @param serviceCityId the value for service_store.service_city_id
     *
     * @mbggenerated
     */
    public void setServiceCityId(Integer serviceCityId) {
        this.serviceCityId = serviceCityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.name
     *
     * @return the value of service_store.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.name
     *
     * @param name the value for service_store.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.address
     *
     * @return the value of service_store.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.address
     *
     * @param address the value for service_store.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.phone
     *
     * @return the value of service_store.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.phone
     *
     * @param phone the value for service_store.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_store.head
     *
     * @return the value of service_store.head
     *
     * @mbggenerated
     */
    public String getHead() {
        return head;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_store.head
     *
     * @param head the value for service_store.head
     *
     * @mbggenerated
     */
    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }
}