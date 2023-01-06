package com.example.vipiki.ui.statistic.statisticUseCases;

import android.content.SharedPreferences;

import com.example.vipiki.models.UserSettings;

public class GetUserSettingsUseCase {
    private final SharedPreferences settings;

    public GetUserSettingsUseCase(SharedPreferences settings) {
        this.settings = settings;
    }

    public UserSettings getProfileData() {
        String schedule = settings.getString("schedule", null);
        String sector = settings.getString("sector", null);
        String post = settings.getString("post", null);
        String name = settings.getString("name", null);

        UserSettings userSettings = new UserSettings();
        userSettings.setSchedule(schedule);
        userSettings.setSector(sector);
        userSettings.setPost(post);
        userSettings.setName(name);

        return userSettings;
    }
}
