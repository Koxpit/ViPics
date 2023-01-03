package com.example.vipiki.ui.menu.menuUseCases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;

public class GetAvgMonthSalaryUseCase {
    private final Context context;

    public GetAvgMonthSalaryUseCase(Context context) {
        this.context = context;
    }

    public double getAvgMonthSalary() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        double averageMonthSalary =  dbHelper.getAverageMonthSalary(db);

        db.close();
        dbHelper.close();

        return averageMonthSalary;
    }
}
