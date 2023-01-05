package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vipiki.database.DbHelper;

public class ChangeEmailUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public ChangeEmailUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void changeEmail(String newEmail) {
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.changePassword(dbHelper, newEmail);
    }
}
