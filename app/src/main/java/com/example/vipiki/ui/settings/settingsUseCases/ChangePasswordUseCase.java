package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vipiki.database.DbHelper;

public class ChangePasswordUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public ChangePasswordUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void changePassword(String newPassword) {
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.changePassword(dbHelper, newPassword);
    }

    public String getEmail() {
        return settings.getString("email", "example@mail.ru");
    }
}
