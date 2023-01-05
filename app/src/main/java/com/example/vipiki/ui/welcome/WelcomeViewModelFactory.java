package com.example.vipiki.ui.welcome;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.ui.welcome.welcomeUseCases.AuthUserUseCase;
import com.example.vipiki.ui.welcome.welcomeUseCases.InitRegisterSpinnersUseCase;

public class WelcomeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public WelcomeViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WelcomeViewModel(
                new AuthUserUseCase(settings),
                        new InitRegisterSpinnersUseCase(context));
    }
}
