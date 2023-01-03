package com.example.vipiki.ui.home;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.ui.home.homeUseCases.GetCurrentPicsUseCase;
import com.example.vipiki.ui.home.homeUseCases.GetNeedPicsUseCase;
import com.example.vipiki.models.Pics;

public class HomeViewModel extends ViewModel {
    private final GetCurrentPicsUseCase getCurrentPicsUseCase;
    private final GetNeedPicsUseCase getNeedPicsUseCase;

    public HomeViewModel(GetCurrentPicsUseCase getCurrentPicsUseCase, GetNeedPicsUseCase getNeedPicsUseCase) {
        this.getCurrentPicsUseCase = getCurrentPicsUseCase;
        this.getNeedPicsUseCase = getNeedPicsUseCase;
    }

    public Pics getCurrentPics() {
        return getCurrentPicsUseCase.getPics();
    }

    public Pics getNeedPics() {
        return getNeedPicsUseCase.getPics();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}