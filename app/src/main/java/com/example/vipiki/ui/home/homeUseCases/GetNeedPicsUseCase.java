package com.example.vipiki.ui.home.homeUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Pics;

public class GetNeedPicsUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public GetNeedPicsUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public Pics getPics() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Pics needPics =  dbHelper.getNeedPics(db, settings);

        db.close();
        dbHelper.close();

        return needPics;
    }
}
