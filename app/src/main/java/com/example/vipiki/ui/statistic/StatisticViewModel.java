package com.example.vipiki.ui.statistic;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;

import com.example.vipiki.ui.statistic.statisticUseCases.CalculateStatisticUseCase;
import com.example.vipiki.ui.statistic.statisticUseCases.GetUserSettingsUseCase;

public class StatisticViewModel extends ViewModel {
    private final CalculateStatisticUseCase calculateStatisticUseCase;
    private final GetUserSettingsUseCase getUserSettingsUseCase;

    public StatisticViewModel(CalculateStatisticUseCase calculateStatisticUseCase, GetUserSettingsUseCase getUserSettingsUseCase) {
        this.calculateStatisticUseCase = calculateStatisticUseCase;
        this.getUserSettingsUseCase = getUserSettingsUseCase;
    }

    public UserSettings getProfileData() {
        return getUserSettingsUseCase.getProfileData();
    }

    public Tax getCurrentTax() {
        return calculateStatisticUseCase.getCurrentTax();
    }

    public int getCheckedBonusIndex() {
        return calculateStatisticUseCase.getCheckedBonusIndex();
    }

    public void setBonusIndex(int bonusIndex) {
        calculateStatisticUseCase.setBonusIndex(bonusIndex);
    }

    public double getMonthSalary(Tax currentTax) {
        return calculateStatisticUseCase.getMonthSalary(currentTax);
    }

    public double getYearSalary(Tax currentTax) {
        return calculateStatisticUseCase.getYearSalary(currentTax);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
