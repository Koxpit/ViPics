package com.example.vipiki.ui.welcome.welcomeUseCases;

import android.content.SharedPreferences;

import com.example.vipiki.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                    String UID = getUID();

                    users.child(UID).get().addOnSuccessListener(dataSnapshot -> {
                        User user = dataSnapshot.getValue(User.class);
                        setAuthSettings(editor, user, UID, !userIsEmpty());
                    }).addOnFailureListener(e -> setLogoutSettings(editor, UID));
                }).addOnFailureListener(e -> {
                    String UID = getUID();
                    setLogoutSettings(editor, UID);
                });
    }

    private String getUID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void setAuthSettings(SharedPreferences.Editor editor, User user, String UID, boolean status) {
        editor.putString("UID", UID);
        editor.putString("name", user.getName());
        editor.putString("post", user.getPost());
        editor.putString("sector", user.getSector());
        editor.putString("schedule", user.getSchedule());
        editor.putBoolean(UID, status);
        editor.apply();
    }

    private void setLogoutSettings(SharedPreferences.Editor editor, String UID) {
        editor.putBoolean(UID, false);
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

        return settings.getBoolean(UID, false);
    }
}
