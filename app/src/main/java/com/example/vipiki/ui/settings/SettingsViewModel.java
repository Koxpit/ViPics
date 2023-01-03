package com.example.vipiki.ui.settings;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    @Override
    protected void onCleared() {
        Log.d("CLEAR_VM", "SettingsViewModel cleared");
        super.onCleared();
    }
}
