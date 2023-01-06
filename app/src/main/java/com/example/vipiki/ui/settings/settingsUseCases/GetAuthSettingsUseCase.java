package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.SharedPreferences;

import com.example.vipiki.messages.errors.ErrorHandler;

public class GetAuthSettingsUseCase {
    private final SharedPreferences settings;

    public GetAuthSettingsUseCase(SharedPreferences settings) {
        this.settings = settings;
    }

    public String getEmail() {
        return settings.getString("email", ErrorHandler.getEmailNotFoundError());
    }

    public String getUID() {
        return settings.getString("UID", ErrorHandler.getUidNotFoundError());
    }
}
