package com.example.vipiki.ui.welcome.welcomeUseCases;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.vipiki.database.DbHelper;

import java.util.List;

public class InitRegisterSpinnersUseCase {
    private final Context context;

    public InitRegisterSpinnersUseCase(Context context) {
        this.context = context;
    }

    public ArrayAdapter<String> getAdapterPosts() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> posts = dbHelper.getPosts();
        ArrayAdapter<String> adapterPosts = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, posts);
        adapterPosts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dbHelper.close();

        return adapterPosts;
    }

    public ArrayAdapter<String> getAdapterSectors() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> sectors = dbHelper.getSectors();
        ArrayAdapter<String> adapterSectors = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, sectors);
        adapterSectors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dbHelper.close();

        return adapterSectors;
    }

    public ArrayAdapter<String> getAdapterSchedules() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> schedules = dbHelper.getSchedules();
        ArrayAdapter<String> adapterSchedules = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, schedules);
        adapterSchedules.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dbHelper.close();

        return adapterSchedules;
    }
}
