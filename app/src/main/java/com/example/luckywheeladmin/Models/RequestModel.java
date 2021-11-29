package com.example.luckywheeladmin.Models;

public class RequestModel {
    int id;
    int user_id;
    String user_name;
    String user_email;
    String bank_code;
    int amount;
    String date;
    boolean status;

    public RequestModel(int id, int user_id, String user_name, String user_email, String bank_code, int amount, String date, boolean status) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.bank_code = bank_code;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }


    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getAmount() {
        return amount;
    }

    public String getBank_code() {
        return bank_code;
    }

    public String getDate() {
        return date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
