package com.example.luckywheeladmin.Models;

public class ContestModel {
    int id;
    int prize;
    String draw_date;
    String draw_time;

    String name;

    public String getName() {
        return name;
    }

    public ContestModel(int id, int prize, String draw_date, String draw_time, String name) {
        this.id = id;
        this.prize = prize;
        this.draw_date = draw_date;
        this.draw_time = draw_time;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrize() {
        return prize;
    }

    public String getDraw_date() {
        return draw_date;
    }

    public String getDraw_time() {
        return draw_time;
    }
}
