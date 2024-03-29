package com.example.vipiki.ui.settings.settingsUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EditProfileUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public EditProfileUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void edit(UserSettings userSettings) {
        updateFirebaseUser(userSettings);
        updateSettingsUser(userSettings);
    }

    public void editEmail(String newEmail) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("email", newEmail);
        editor.apply();
    }

    public String getUserName() {
        return settings.getString("name", ErrorHandler.getNameNotFoundError());
    }

    public int getPostIndex() {
        return settings.getInt("postIndex", 0);
    }

    public int getScheduleIndex() {
        return settings.getInt("scheduleIndex", 0);
    }

    public int getSectorIndex() {
        return settings.getInt("sectorIndex", 0);
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

    private void updateFirebaseUser(UserSettings userSettings) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String UID = currentUser.getUid();
            DatabaseReference firebaseDb = FirebaseDatabase.getInstance().getReference();
            firebaseDb.child("users").child(UID).child("name").setValue(userSettings.getName());
            firebaseDb.child("users").child(UID).child("post").setValue(userSettings.getPost());
            firebaseDb.child("users").child(UID).child("schedule").setValue(userSettings.getSchedule());
            firebaseDb.child("users").child(UID).child("sector").setValue(userSettings.getSector());
        }
    }

    private void updateSettingsUser(UserSettings userSettings) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", userSettings.getName());
        editor.putString("post", userSettings.getPost());
        editor.putString("schedule", userSettings.getSchedule());
        editor.putString("sector", userSettings.getSector());
        editor.putInt("postIndex", userSettings.getPostIndex());
        editor.putInt("scheduleIndex", userSettings.getScheduleIndex());
        editor.putInt("sectorIndex", userSettings.getSectorIndex());
        editor.apply();
    }
}
