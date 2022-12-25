package com.example.vipiki.usecases.welcomeUseCases;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.vipiki.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
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
                    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                    String UID = getUID();

                    users.child(UID).get().addOnSuccessListener(dataSnapshot -> {
                        User user = dataSnapshot.getValue(User.class);
                        setAuthSettings(editor, user, UID, !userIsEmpty());
                    }).addOnFailureListener(e -> setAuthSettings(editor, UID, false));
                }).addOnFailureListener(e -> {
                    String UID = getUID();
                    setAuthSettings(editor, UID, false);
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
