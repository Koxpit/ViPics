package com.example.vipiki.viewModels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class AddPicksViewModel extends ViewModel {
    @Override
    protected void onCleared() {
        Log.d("CLEAR_VM", "AddPicksViewModel cleared");
        super.onCleared();
    }
}
