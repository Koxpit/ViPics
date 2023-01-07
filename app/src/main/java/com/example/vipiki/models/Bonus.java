package com.example.vipiki.models;

public class Bonus {
    private double selectionOsBonus;
    private double allocationOsBonus;
    private double selectionMezBonus;
    private double allocationMezBonus;

    public Bonus() {}

    public Bonus(double selectionOsBonus, double allocationOsBonus, double selectionMezBonus, double allocationMezBonus) {
        this.selectionOsBonus = selectionOsBonus;
        this.allocationOsBonus = allocationOsBonus;
        this.selectionMezBonus = selectionMezBonus;
        this.allocationMezBonus = allocationMezBonus;
    }

    public double getSelectionOsBonus() {
        return selectionOsBonus;
    }

    public void setSelectionOsBonus(double selectionOsBonus) {
        this.selectionOsBonus = selectionOsBonus;
    }

    public double getAllocationOsBonus() {
        return allocationOsBonus;
    }

    public void setAllocationOsBonus(double allocationOsBonus) {
        this.allocationOsBonus = allocationOsBonus;
    }

    public double getSelectionMezBonus() {
        return selectionMezBonus;
    }

    public void setSelectionMezBonus(double selectionMezBonus) {
        this.selectionMezBonus = selectionMezBonus;
    }

    public double getAllocationMezBonus() {
        return allocationMezBonus;
    }

    public void setAllocationMezBonus(double allocationMezBonus) {
        this.allocationMezBonus = allocationMezBonus;
    }
}
