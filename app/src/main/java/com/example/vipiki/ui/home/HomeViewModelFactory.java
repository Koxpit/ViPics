package com.example.vipiki.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.ui.home.homeUseCases.GetCurrentPicsUseCase;
import com.example.vipiki.ui.home.homeUseCases.GetNeedPicsUseCase;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public HomeViewModelFactory(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences(DbHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(new GetCurrentPicsUseCase(context), new GetNeedPicsUseCase(context, settings));
    }
}
