package com.example.vipiki.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.ui.home.HomeViewModel;
import com.example.vipiki.ui.home.homeUseCases.GetCurrentPicsUseCase;
import com.example.vipiki.ui.home.homeUseCases.GetNeedPicsUseCase;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public HomeViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(new GetCurrentPicsUseCase(context, settings), new GetNeedPicsUseCase(context, settings));
    }
}
