package com.example.luckywheeladmin.Models;

public class ContestModel {
    int id;
    int prize;
    String draw_date;
    String name;

    public String getName() {
        return name;
    }

    public ContestModel(int id, int prize, String draw_date, String name) {
        this.id = id;
        this.prize = prize;
        this.draw_date = draw_date;
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
}
