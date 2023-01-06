package com.example.vipiki.ui.statistic;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.ui.statistic.statisticUseCases.CalculateStatisticUseCase;
import com.example.vipiki.ui.statistic.statisticUseCases.GetUserSettingsUseCase;

public class StatisticViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final SharedPreferences settings;

    public StatisticViewModelFactory(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences(DbHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StatisticViewModel(
                new CalculateStatisticUseCase(context, settings),
                new GetUserSettingsUseCase(settings));
    }
}
