package com.happydriving.rockets.server.model;

/**
 * Created by gaoc10 on 2015/11/12 0012.
 */
public class DrivingSchoolBasicInfo {
    private int schoolId;
    private String schoolName;
    private String schoolAddress;
    private String imgURL;

    public int  getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
