package com.happydriving.rockets.server.model;

import java.util.Date;

/**
 * Created by gaoc10 on 2015/10/15 0015.
 */
public class TrainingTaskInfo {
    Date trainingDate;
    String trainingTime;
    String coachName;
    String trainingSite;
    String trainingCourse;
    String studentName;
    String studentPhone;


    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getTrainingCourse() {
        return trainingCourse;
    }

    public void setTrainingCourse(String trainingCourse) {
        this.trainingCourse = trainingCourse;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTrainingSite() {
        return trainingSite;
    }

    public void setTrainingSite(String trainingSite) {
        this.trainingSite = trainingSite;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }
}
