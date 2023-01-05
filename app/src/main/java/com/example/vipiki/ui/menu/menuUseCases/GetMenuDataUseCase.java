package com.example.vipiki.ui.menu.menuUseCases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vipiki.database.DbHelper;

public class GetMenuDataUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public GetMenuDataUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public double getAvgMonthSalary() {
        DbHelper dbHelper = new DbHelper(context);
        double averageMonthSalary =  dbHelper.getAverageMonthSalary(dbHelper, settings);
        dbHelper.close();

        return averageMonthSalary;
    }

    public String getUserName() {
        return settings.getString("name", "not found");
    }
}
