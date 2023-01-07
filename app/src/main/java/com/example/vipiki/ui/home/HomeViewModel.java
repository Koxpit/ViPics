package com.example.vipiki.ui.home;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.ui.home.homeUseCases.GetCurrentPicsUseCase;
import com.example.vipiki.ui.home.homeUseCases.GetNeedPicsUseCase;
import com.example.vipiki.models.Pics;

public class HomeViewModel extends ViewModel {
    private final GetCurrentPicsUseCase getCurrentPicsUseCase;
    private final GetNeedPicsUseCase getNeedPicsUseCase;

    private int picsSelection = 0, picsAllocation = 0;
    private int needPicsAllocation = 50000, needPicsSelection = 5000;

    public HomeViewModel(GetCurrentPicsUseCase getCurrentPicsUseCase, GetNeedPicsUseCase getNeedPicsUseCase) {
        this.getCurrentPicsUseCase = getCurrentPicsUseCase;
        this.getNeedPicsUseCase = getNeedPicsUseCase;
    }

    public void setCurrentPics() {
        Pics currentPics = getCurrentPicsUseCase.getPics();
        picsSelection = currentPics.getSelection();
        picsAllocation = currentPics.getAllocation();
    }

    public void setNeedPics() {
        Pics needPics = getNeedPicsUseCase.getPics();
        needPicsAllocation = needPics.getAllocation();
        needPicsSelection = needPics.getSelection();
    }

    public int getNeedPicsAllocation() {
        int needPics = needPicsAllocation - picsAllocation;

        if (needPics < 0)
            needPics = 0;

        return needPics;
    }

    public float getCurrentAllocationPercent() {
        return ((float) picsAllocation / needPicsAllocation) * 100.0f;
    }

    public int getPicsAllocation() {
        return picsAllocation;
    }

    public int getNeedPicsSelection() {
        int needPics = needPicsSelection - picsSelection;

        if (needPics < 0)
            needPics = 0;

        return needPics;
    }

    public float getCurrentSelectionPercent() {
        return ((float) picsSelection / needPicsSelection) * 100.0f;
    }

    public int getPicsSelection() {
        return picsSelection;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}