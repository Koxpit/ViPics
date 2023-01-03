package com.example.vipiki.ui.addPicks;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.ui.addPicks.AddPicksViewModel;
import com.example.vipiki.ui.addPicks.addPicksUseCases.AddWorkDayUseCase;

public class AddPicksViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public AddPicksViewModelFactory(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddPicksViewModel(new AddWorkDayUseCase(context, settings));
    }
}
