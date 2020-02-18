package com.kkondratek.savingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "goals")
public class Goal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String price;

    private int priority;

    private String details;

    public Goal(String name, String price, int priority, String details) {
        this.name = name;
        this.price = price;
        this.priority = priority;
        this.details = details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price =price;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {return id;}
}

