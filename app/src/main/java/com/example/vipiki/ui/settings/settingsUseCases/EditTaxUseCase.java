package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Tax;

public class EditTaxUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public EditTaxUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void updateTax(Tax newTax) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.updateTax(db, settings, newTax);
        db.close();
        dbHelper.close();
    }

    public Tax getTax() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Tax tax = dbHelper.getTax(db, settings);
        db.close();
        dbHelper.close();

        return tax;
    }
}
