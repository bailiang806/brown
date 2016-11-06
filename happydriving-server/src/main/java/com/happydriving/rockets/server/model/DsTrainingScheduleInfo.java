package com.happydriving.rockets.server.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gaoc10 on 2015/11/13 0013.
 */
public class DsTrainingScheduleInfo {
    private String id;
    private String cityId;
    private String trainingCity;
    private String coachSchoolId;
    private Date trainingDate;
    private String stringTrainingDate;
    private String trainingTime;
    private String trainingCourse;
    private BigDecimal trainingCount;
    private String coachId;
    private String trainingSiteId;
    private String coachImgUrl;
    private int isSelected;
    private String trainingSiteAddress;

    public String getTrainingSiteAddress() {
        return trainingSiteAddress;
    }

    public void setTrainingSiteAddress(String trainingSiteAddress) {
        this.trainingSiteAddress = trainingSiteAddress;
    }

    public BigDecimal getTrainingCount() {
        return trainingCount;
    }

    public void setTrainingCount(BigDecimal trainingCount) {
        this.trainingCount = trainingCount;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTrainingCity() {
        return trainingCity;
    }

    public void setTrainingCity(String trainingCity) {
        this.trainingCity = trainingCity;
    }

    public String getCoachSchoolId() {
        return coachSchoolId;
    }

    public void setCoachSchoolId(String coachSchoolId) {
        this.coachSchoolId = coachSchoolId;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public String getStringTrainingDate() {
        return stringTrainingDate;
    }

    public void setStringTrainingDate(String stringTrainingDate) {
        this.stringTrainingDate = stringTrainingDate;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }

    public String getTrainingCourse() {
        return trainingCourse;
    }

    public void setTrainingCourse(String trainingCourse) {
        this.trainingCourse = trainingCourse;
    }

    public String getTrainingSiteId() {
        return trainingSiteId;
    }

    public void setTrainingSiteId(String trainingSiteId) {
        this.trainingSiteId = trainingSiteId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachImgUrl() {
        return coachImgUrl;
    }

    public void setCoachImgUrl(String coachImgUrl) {
        this.coachImgUrl = coachImgUrl;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
