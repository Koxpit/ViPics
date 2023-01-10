package com.example.vipiki.ui.welcome.welcomeUseCases;

import android.content.SharedPreferences;

import com.example.vipiki.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthUserUseCase {
    private final SharedPreferences settings;

    public AuthUserUseCase(SharedPreferences settings) {
        this.settings = settings;
    }

    public boolean isAlreadyAuthUser() {
        boolean isAlreadyAuth = false;

        if (!userIsEmpty()) {
            String UID = getUID();
            if (settings.contains(UID))
                if (settings.getBoolean(UID, false))
                    isAlreadyAuth = true;
        }

        return isAlreadyAuth;
    }

    public String getUID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void setUserSettings(User user, String UID, boolean status) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("UID", UID);
        editor.putString("name", user.getName());
        editor.putString("post", user.getPost());
        editor.putString("sector", user.getSector());
        editor.putString("schedule", user.getSchedule());
        editor.putString("email", user.getEmail());
        editor.putBoolean(UID, status);
        editor.apply();
    }

    public boolean userIsEmpty() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user == null;
    }
}
