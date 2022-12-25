package com.example.vipiki.usecases.welcomeUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserUseCase {
    private final Context context;
    private final FirebaseAuth auth;
    private final DatabaseReference users;
    private final SharedPreferences settings;
    private String post, sector, schedule, name;

    public RegisterUserUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
        auth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("users");
    }

    public void registerUser(User newUser, String password) {
        name = newUser.getName();
        post = newUser.getPost();
        sector = newUser.getSector();
        schedule = newUser.getSchedule();
        SharedPreferences.Editor editor = settings.edit();

        auth.createUserWithEmailAndPassword(newUser.getEmail(), password)
                .addOnSuccessListener(authResult -> {
                    if (userIsEmpty())
                        return;

                    String UID  = getUID();

                    users.child(UID).setValue(newUser)
                            .addOnSuccessListener(unused -> {
                                //recreateDatabase();
                                setUserSettings(UID);
                            })
                            .addOnFailureListener(e -> {
                                setAuthSettings(editor, UID, false);
                            });
                });
    }

    private boolean userIsEmpty() {
        boolean userIsEmpty = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
            userIsEmpty = true;

        return userIsEmpty;
    }

    private String getUID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void setAuthSettings(SharedPreferences.Editor editor, String UID, boolean status) {
        editor.putBoolean(UID, status);
        editor.apply();
    }

    private void recreateDatabase() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.dropDB(db);
        dbHelper.onCreate(db);
        dbHelper.close();
        db.close();
    }

    private void setUserSettings(String userUID) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("UID", userUID);
        editor.putBoolean(userUID, true);
        editor.putString("name", name);
        editor.putString("sector", sector);
        editor.putString("schedule", schedule);
        editor.putString("post", post);
        editor.apply();
    }
}
