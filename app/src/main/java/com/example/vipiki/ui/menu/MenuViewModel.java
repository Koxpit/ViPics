package com.example.vipiki.ui.menu;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.ui.menu.menuUseCases.GetAvgMonthSalaryUseCase;
import com.example.vipiki.ui.menu.menuUseCases.SignOutUseCase;

public class MenuViewModel extends ViewModel {
    private final GetAvgMonthSalaryUseCase getAvgMonthSalaryUseCase;
    private final SignOutUseCase signOutUseCase;

    public MenuViewModel(GetAvgMonthSalaryUseCase getAvgMonthSalaryUseCase, SignOutUseCase signOutUseCase) {
        this.getAvgMonthSalaryUseCase = getAvgMonthSalaryUseCase;
        this.signOutUseCase = signOutUseCase;
    }

    public double getAvgMonthSalary() {
        return getAvgMonthSalaryUseCase.getAvgMonthSalary();
    }

    public void signOut() {
        signOutUseCase.signOut();
    }
}