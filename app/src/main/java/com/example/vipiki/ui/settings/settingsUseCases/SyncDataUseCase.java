package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;

public class SyncDataUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public SyncDataUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void synchronize() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        dbHelper.synchronizeData(db, settings);
        db.close();
        dbHelper.close();
    }
}
