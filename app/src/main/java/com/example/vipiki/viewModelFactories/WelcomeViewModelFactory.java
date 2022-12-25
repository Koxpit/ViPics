package com.example.vipiki.viewModelFactories;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.usecases.welcomeUseCases.AuthUserUseCase;
import com.example.vipiki.usecases.welcomeUseCases.InitRegisterSpinnersUseCase;
import com.example.vipiki.usecases.welcomeUseCases.RegisterUserUseCase;
import com.example.vipiki.viewModels.WelcomeViewModel;

public class WelcomeViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private SharedPreferences settings;

    public WelcomeViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WelcomeViewModel(
                new AuthUserUseCase(settings),
                new RegisterUserUseCase(context, settings),
                        new InitRegisterSpinnersUseCase(context));
    }
}
