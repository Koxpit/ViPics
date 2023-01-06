package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;

public class GetAuthSettingsUseCase {
    private final SharedPreferences settings;

    public GetAuthSettingsUseCase(SharedPreferences settings) {
        this.settings = settings;
    }

    public String getEmail() {
        return settings.getString("email", "example@mail.ru");
    }

    public String getUID() {
        return settings.getString("UID", "not found");
    }
}
