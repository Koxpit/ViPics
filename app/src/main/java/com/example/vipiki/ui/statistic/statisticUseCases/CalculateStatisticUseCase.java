package com.example.vipiki.ui.statistic.statisticUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.models.Tax;

public class CalculateStatisticUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public CalculateStatisticUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    private Tax getFullTax() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Tax taxes = dbHelper.getFullTax(db, settings);
        db.close();
        dbHelper.close();

        return taxes;
    }

    public Tax getCurrentTax() {
        Tax taxes = getFullTax();
        Tax currentTax = new Tax();
        int checkedIndex = getCheckedBonusIndex();

        double taxSelectionOs, taxAllocationOs, taxSelectionMez, taxAllocationMez;
        if (checkedIndex == 0) {
            taxSelectionOs = taxes.getTax_selection_os() + taxes.getBonus_selection_os_120();
            taxAllocationOs = taxes.getTax_allocation_os() + taxes.getBonus_allocation_os_120();
            taxSelectionMez = taxes.getTax_selection_mez() + taxes.getBonus_selection_mez_120();
            taxAllocationMez = taxes.getTax_allocation_mez() + taxes.getBonus_allocation_mez_120();
        }
        else if (checkedIndex == 1) {
            taxSelectionOs = taxes.getTax_selection_os() + taxes.getBonus_selection_os_100();
            taxAllocationOs = taxes.getTax_allocation_os() + taxes.getBonus_allocation_os_100();
            taxSelectionMez = taxes.getTax_selection_mez() + taxes.getBonus_selection_mez_100();
            taxAllocationMez = taxes.getTax_allocation_mez() + taxes.getBonus_allocation_mez_100();
        }
        else if (checkedIndex == 2) {
            taxSelectionOs = taxes.getTax_selection_os() + taxes.getBonus_selection_os_90();
            taxAllocationOs = taxes.getTax_allocation_os() + taxes.getBonus_allocation_os_90();
            taxSelectionMez = taxes.getTax_selection_mez() + taxes.getBonus_selection_mez_90();
            taxAllocationMez = taxes.getTax_allocation_mez() + taxes.getBonus_allocation_mez_90();
        }
        else if (checkedIndex == 3) {
            taxSelectionOs = taxes.getTax_selection_os() + taxes.getBonus_selection_os_80();
            taxAllocationOs = taxes.getTax_allocation_os() + taxes.getBonus_allocation_os_80();
            taxSelectionMez = taxes.getTax_selection_mez() + taxes.getBonus_selection_mez_80();
            taxAllocationMez = taxes.getTax_allocation_mez() + taxes.getBonus_allocation_mez_80();
        }
        else {
            taxSelectionOs = taxes.getTax_selection_os();
            taxAllocationOs = taxes.getTax_allocation_os();
            taxSelectionMez = taxes.getTax_selection_mez();
            taxAllocationMez = taxes.getTax_allocation_mez();
        }

        currentTax.setTax_selection_os(taxSelectionOs);
        currentTax.setTax_allocation_os(taxAllocationOs);
        currentTax.setTax_selection_mez(taxSelectionMez);
        currentTax.setTax_allocation_mez(taxAllocationMez);

        return currentTax;
    }

    public int getCheckedBonusIndex() {
        return settings.getInt("bonusIndex", 0);
    }

    public void setBonusIndex(int bonusIndex) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("bonusIndex", bonusIndex);
        editor.apply();
    }

    public double getMonthSalary(Tax currentTax) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String UID = getUID();
        double monthSalary = dbHelper.getMonthSalary(db, UID, currentTax);
        db.close();
        dbHelper.close();

        return monthSalary;
    }

    private String getUID() {
        return settings.getString("UID", ErrorHandler.getUidNotFoundError());
    }

    private String getUserPost() {
        return settings.getString("post", ErrorHandler.getPostNotFoundError());
    }

    public double getYearSalary(Tax tax) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String post = getUserPost();
        int yearNorm = dbHelper.getYearNorm(db, settings);

        double postTax = 0;
        switch (post) {
            case "Отборщик мезонина":
                postTax = tax.getTax_selection_mez();
                break;
            case "Отборщик основы":
                postTax = tax.getTax_selection_os();
                break;
            case "Размещенец мезонина":
                postTax = tax.getTax_allocation_mez();
                break;
            case "Размещенец основы":
                postTax = tax.getTax_allocation_os();
                break;
        }

        db.close();
        dbHelper.close();

        return yearNorm * postTax;
    }
}
