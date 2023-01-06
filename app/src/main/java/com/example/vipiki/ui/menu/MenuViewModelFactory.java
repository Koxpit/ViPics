package com.example.vipiki.ui.menu;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.ui.menu.menuUseCases.ChangeAvatarUseCase;
import com.example.vipiki.ui.menu.menuUseCases.GetMenuDataUseCase;
import com.example.vipiki.ui.menu.menuUseCases.SignOutUseCase;

public class MenuViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public MenuViewModelFactory(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences(DbHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MenuViewModel(
                new GetMenuDataUseCase(context, settings),
                new SignOutUseCase(context, settings),
                new ChangeAvatarUseCase(context, settings));
    }
}
