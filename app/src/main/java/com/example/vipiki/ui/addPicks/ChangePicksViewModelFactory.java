package com.example.vipiki.ui.addPicks;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.ui.addPicks.addPicksUseCases.ChangeWorkDaysUseCase;
import com.example.vipiki.ui.addPicks.addPicksUseCases.DeleteWorkDayUseCase;

public class ChangePicksViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public ChangePicksViewModelFactory(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences(DbHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChangePicksViewModel(
                new ChangeWorkDaysUseCase(context, settings),
                new DeleteWorkDayUseCase(context));
    }
}
