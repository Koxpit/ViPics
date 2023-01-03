package com.example.vipiki.models;

public class Tax {
    private double tax_selection_os, tax_allocation_os;
    private double tax_selection_mez, tax_allocation_mez;
    private double bonus_selection_mez_80, bonus_selection_os_80;
    private double bonus_allocation_mez_80, bonus_allocation_os_80;

    public Tax() {

    }

    public Tax(double tax_selection_os, double tax_allocation_os, double tax_selection_mez, double tax_allocation_mez,
               double bonus_selection_mez_80, double bonus_selection_os_80, double bonus_allocation_mez_80, double bonus_allocation_os_80) {
        this.tax_selection_os = tax_selection_os;
        this.tax_allocation_os = tax_allocation_os;
        this.tax_selection_mez = tax_selection_mez;
        this.tax_allocation_mez = tax_allocation_mez;
        this.bonus_selection_mez_80 = bonus_selection_mez_80;
        this.bonus_selection_os_80 = bonus_selection_os_80;
        this.bonus_allocation_mez_80 = bonus_allocation_mez_80;
        this.bonus_allocation_os_80 = bonus_allocation_os_80;
    }

    public double getTax_selection_os() {
        return tax_selection_os;
    }

    public void setTax_selection_os(double tax_selection_os) {
        this.tax_selection_os = tax_selection_os;
    }

    public double getTax_allocation_os() {
        return tax_allocation_os;
    }

    public void setTax_allocation_os(double tax_allocation_os) {
        this.tax_allocation_os = tax_allocation_os;
    }

    public double getTax_selection_mez() {
        return tax_selection_mez;
    }

    public void setTax_selection_mez(double tax_selection_mez) {
        this.tax_selection_mez = tax_selection_mez;
    }

    public double getTax_allocation_mez() {
        return tax_allocation_mez;
    }

    public void setTax_allocation_mez(double tax_allocation_mez) {
        this.tax_allocation_mez = tax_allocation_mez;
    }

    public double getBonus_selection_mez_80() {
        return bonus_selection_mez_80;
    }

    public void setBonus_selection_mez_80(double bonus_selection_mez_80) {
        this.bonus_selection_mez_80 = bonus_selection_mez_80;
    }

    public double getBonus_selection_os_80() {
        return bonus_selection_os_80;
    }

    public void setBonus_selection_os_80(double bonus_selection_os_80) {
        this.bonus_selection_os_80 = bonus_selection_os_80;
    }

    public double getBonus_allocation_mez_80() {
        return bonus_allocation_mez_80;
    }

    public void setBonus_allocation_mez_80(double bonus_allocation_mez_80) {
        this.bonus_allocation_mez_80 = bonus_allocation_mez_80;
    }

    public double getBonus_allocation_os_80() {
        return bonus_allocation_os_80;
    }

    public void setBonus_allocation_os_80(double bonus_allocation_os_80) {
        this.bonus_allocation_os_80 = bonus_allocation_os_80;
    }
}
