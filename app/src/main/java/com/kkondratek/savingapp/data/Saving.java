package com.kkondratek.savingapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savings")
public class Saving {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String amount;

    @ColumnInfo(name = "day_of_month")
    private int monthDay;

    public Saving(String name, String amount, int monthDay) {
        this.name = name;
        this.monthDay = monthDay;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }
    public void setMonthDay(String monthDay) {
        this.monthDay = Integer.parseInt(monthDay);
    }
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
