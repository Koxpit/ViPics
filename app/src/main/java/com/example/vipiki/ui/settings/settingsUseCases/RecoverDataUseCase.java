package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vipiki.database.DbHelper;

public class RecoverDataUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public RecoverDataUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void recoverData() {
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.recoverData(dbHelper, settings);
    }
}
