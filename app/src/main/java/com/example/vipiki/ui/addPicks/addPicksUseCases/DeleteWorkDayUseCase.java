package com.example.vipiki.ui.addPicks.addPicksUseCases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;

public class DeleteWorkDayUseCase {
    private Context context;

    public DeleteWorkDayUseCase(Context context) {
        this.context = context;
    }

    public void deleteWorkDay(int day, int month, int year) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.deleteWorkDay(db, year, month, day);
        dbHelper.close();
        db.close();
    }
}
