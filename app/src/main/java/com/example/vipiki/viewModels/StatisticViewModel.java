package com.example.vipiki.viewModels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {
    @Override
    protected void onCleared() {
        Log.d("CLEAR_VM", "StatisticViewModel cleared");
        super.onCleared();
    }
}
