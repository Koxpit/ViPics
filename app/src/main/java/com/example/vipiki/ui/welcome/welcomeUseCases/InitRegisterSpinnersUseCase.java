package com.example.vipiki.ui.welcome.welcomeUseCases;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vipiki.database.DbHelper;

import java.util.List;

public class InitRegisterSpinnersUseCase {
    private final Context context;

    public InitRegisterSpinnersUseCase(Context context) {
        this.context = context;
    }

    public List<String> getPosts() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> posts = dbHelper.getPosts();
        dbHelper.close();

        return posts;
    }

    public List<String> getSectors() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> sectors = dbHelper.getSectors();
        dbHelper.close();

        return sectors;
    }

    public List<String> getSchedules() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> schedules = dbHelper.getSchedules();
        dbHelper.close();

        return schedules;
    }
}
