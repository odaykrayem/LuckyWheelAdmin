package com.example.luckywheeladmin.Models;

public class UserModel {
    int id;
//    String first_name, middle_name, last_name;
    String user_name;
    String email;
    int points, balance;
    String referral_code;

    public UserModel(int id, String user_name, String email, int points, int balance, String referral_code) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.points = points;
        this.balance = balance;
        this.referral_code = referral_code;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

//    public String getMiddle_name() {
//        return middle_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }

    public int getBalance() {
        return balance;
    }

    public String getReferral_code() {
        return referral_code;
    }
}
