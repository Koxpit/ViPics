package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Bonus;
import com.example.vipiki.models.Tax;

public class EditBonusesUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public EditBonusesUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void updateBonuses(Bonus newBonus) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.updateBonuses(db, settings, newBonus);
        db.close();
        dbHelper.close();
    }

    public Bonus getBonuses() {
        Tax taxes = getFullTax();
        Bonus currentBonus = new Bonus();
        int checkedIndex = getCheckedBonusIndex();

        double bonusSelectionOs = 0, bonusAllocationOs = 9, bonusSelectionMez = 0, bonusAllocationMez = 0;
        if (checkedIndex == 0) {
            bonusSelectionOs = taxes.getBonus_selection_os_120();
            bonusAllocationOs = taxes.getBonus_allocation_os_120();
            bonusSelectionMez = taxes.getBonus_selection_mez_120();
            bonusAllocationMez = taxes.getBonus_allocation_mez_120();
        }
        else if (checkedIndex == 1) {
            bonusSelectionOs = taxes.getBonus_selection_os_100();
            bonusAllocationOs = taxes.getBonus_allocation_os_100();
            bonusSelectionMez = taxes.getBonus_selection_mez_100();
            bonusAllocationMez = taxes.getBonus_allocation_mez_100();
        }
        else if (checkedIndex == 2) {
            bonusSelectionOs = taxes.getBonus_selection_os_90();
            bonusAllocationOs = taxes.getBonus_allocation_os_90();
            bonusSelectionMez = taxes.getBonus_selection_mez_90();
            bonusAllocationMez = taxes.getBonus_allocation_mez_90();
        }
        else if (checkedIndex == 3) {
            bonusSelectionOs = taxes.getBonus_selection_os_80();
            bonusAllocationOs = taxes.getBonus_allocation_os_80();
            bonusSelectionMez = taxes.getBonus_selection_mez_80();
            bonusAllocationMez = taxes.getBonus_allocation_mez_80();
        }

        currentBonus.setSelectionOsBonus(bonusSelectionOs);
        currentBonus.setAllocationOsBonus(bonusAllocationOs);
        currentBonus.setSelectionMezBonus(bonusSelectionMez);
        currentBonus.setAllocationMezBonus(bonusAllocationMez);

        return currentBonus;
    }

    private Tax getFullTax() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Tax taxes = dbHelper.getFullTax(db, settings);
        db.close();
        dbHelper.close();

        return taxes;
    }

    public int getCheckedBonusIndex() {
        return settings.getInt("bonusIndex", 0);
    }

    public void setBonusIndex(int bonusIndex) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("bonusIndex", bonusIndex);
        editor.apply();
    }
}
