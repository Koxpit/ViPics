package com.example.vipiki.models;

public class WorkDay {
    private int day;
    private int month;
    private int year;
    private int selectionOs;
    private int allocationOs;
    private int selectionMez;
    private int allocationMez;
    private int isExtraDay;
    private double pay;
    private String userUID;

    public WorkDay() {}

    public WorkDay(int day, int month, int year, int isExtraDay, int selectionOs, int allocationOs, int selectionMez, int allocationMez, double pay, String userUID)  {
        this.day = day;
        this.month = month;
        this.year = year;
        this.isExtraDay = isExtraDay;
        this.selectionOs = selectionOs;
        this.allocationOs = allocationOs;
        this.selectionMez = selectionMez;
        this.allocationMez = allocationMez;
        this.pay = pay;
        this.userUID = userUID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getExtraDay() {
        return isExtraDay;
    }

    public void setExtraDay(int isExtraDay) {
        this.isExtraDay = isExtraDay;
    }

    public int getSelectionOs() {
        return selectionOs;
    }

    public void setSelectionOs(int selectionOs) {
        this.selectionOs = selectionOs;
    }

    public int getAllocationOs() {
        return allocationOs;
    }

    public void setAllocationOs(int allocationOs) {
        this.allocationOs = allocationOs;
    }

    public int getSelectionMez() {
        return selectionMez;
    }

    public void setSelectionMez(int selectionMez) {
        this.selectionMez = selectionMez;
    }

    public int getAllocationMez() {
        return allocationMez;
    }

    public void setAllocationMez(int allocationMez) {
        this.allocationMez = allocationMez;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
