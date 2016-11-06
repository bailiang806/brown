package com.happydriving.rockets.server.model;

/**
 * @author mazhiqiang
 */
public class UserLoginInfo {

    private final int userId;
    private final String role;
    private final String phoneNumber;

    public UserLoginInfo(int userId, String role, String phoneNumber) {
        this.userId = userId;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
