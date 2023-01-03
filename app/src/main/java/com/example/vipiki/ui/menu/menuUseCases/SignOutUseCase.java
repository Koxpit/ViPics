package com.example.vipiki.ui.menu.menuUseCases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.vipiki.ui.welcome.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignOutUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public SignOutUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void signOut() {
        SharedPreferences.Editor editor = settings.edit();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        editor.putBoolean(UID, false);
        editor.apply();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
    }
}
