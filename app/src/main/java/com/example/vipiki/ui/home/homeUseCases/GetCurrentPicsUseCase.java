package com.example.vipiki.ui.home.homeUseCases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Pics;

public class GetCurrentPicsUseCase {
    private final Context context;

    public GetCurrentPicsUseCase(Context context) {
        this.context = context;
    }

    public Pics getPics() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Pics currentPics = dbHelper.getCurrentPics(db);

        dbHelper.close();
        db.close();

        return currentPics;
    }
}
