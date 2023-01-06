package com.example.vipiki.ui.menu.menuUseCases;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.messages.errors.ErrorHandler;

public class ChangeAvatarUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public ChangeAvatarUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void saveImage(Bitmap userImage) {
        DbHelper dbHelper = new DbHelper(context);
        String UID = settings.getString("UID", ErrorHandler.getUidNotFoundError());
        dbHelper.saveUserImage(UID, userImage);
        dbHelper.close();
    }

    public Bitmap getUserImage() {
        DbHelper dbHelper = new DbHelper(context);
        String UID = settings.getString("UID", ErrorHandler.getUidNotFoundError());
        Bitmap userImage = dbHelper.getUserImage(UID);
        dbHelper.close();

        return userImage;
    }
}
