package com.example.vipiki.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.ui.settings.settingsUseCases.ChangeEmailUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.ChangePasswordUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.EditProfileUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.EditTaxUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.RecoverDataUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.SyncDataUseCase;

public class SettingsViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public SettingsViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(
                new EditProfileUseCase(context, settings),
                new EditTaxUseCase(context, settings),
                new RecoverDataUseCase(context, settings),
                new SyncDataUseCase(context, settings),
                new ChangePasswordUseCase(context, settings),
                new ChangeEmailUseCase(context, settings));
    }
}
