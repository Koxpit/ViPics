package com.example.vipiki.usecases.welcomeUseCases;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthUserUseCase {
    private final SharedPreferences settings;

    public AuthUserUseCase(SharedPreferences settings) {
        this.settings = settings;
    }

    public boolean isAlreadyAuthUser() {
        boolean isAlreadyAuth = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String UID = user.getUid();
            if (settings.contains(UID)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(UID, false);
                editor.apply();
                if (settings.getBoolean(UID, false)) {
                    isAlreadyAuth = true;
                }
            }
        }

        return isAlreadyAuth;
    }

    public void authUser(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        SharedPreferences.Editor editor = settings.edit();

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String UID = getUID();
                    setAuthSettings(editor, UID, !userIsEmpty());
                }).addOnFailureListener(e -> {
                    String UID = getUID();
                    setAuthSettings(editor, UID, false);
                });
    }

    private String getUID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void setAuthSettings(SharedPreferences.Editor editor, String UID, boolean status) {
        editor.putBoolean(UID, status);
        editor.apply();
    }

    private boolean userIsEmpty() {
        boolean userIsEmpty = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            userIsEmpty = true;
        }

        return userIsEmpty;
    }

    public boolean getAuthStatus() {
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        boolean isAuth = settings.getBoolean(UID, false);

        return isAuth;
    }
}
