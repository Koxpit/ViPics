package com.example.vipiki.ui.menu;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.ui.menu.MenuViewModel;
import com.example.vipiki.ui.menu.menuUseCases.GetAvgMonthSalaryUseCase;
import com.example.vipiki.ui.menu.menuUseCases.SignOutUseCase;

public class MenuViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public MenuViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MenuViewModel(new GetAvgMonthSalaryUseCase(context), new SignOutUseCase(context, settings));
    }
}
