package com.example.vipiki.ui.addPicks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPicksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddPicksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Выберите дату чтобы добавить смену");
    }

    public LiveData<String> getText() {
        return mText;
    }
}