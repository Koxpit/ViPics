package com.example.vipiki.ui.settings;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;
import com.example.vipiki.ui.settings.settingsUseCases.EditProfileUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.EditTaxUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.RecoverDataUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.SyncDataUseCase;

public class SettingsViewModel extends ViewModel {
    private final EditProfileUseCase editProfileUseCase;
    private final EditTaxUseCase editTaxUseCase;
    private final RecoverDataUseCase recoverDataUseCase;
    private final SyncDataUseCase syncDataUseCase;

    public SettingsViewModel(EditProfileUseCase editProfileUseCase, EditTaxUseCase editTaxUseCase,
                             RecoverDataUseCase recoverDataUseCase, SyncDataUseCase syncDataUseCase) {
        this.editProfileUseCase = editProfileUseCase;
        this.editTaxUseCase = editTaxUseCase;
        this.recoverDataUseCase = recoverDataUseCase;
        this.syncDataUseCase = syncDataUseCase;
    }

    public void editProfile(UserSettings userSettings) {
        editProfileUseCase.edit(userSettings);
    }

    public int getPostIndex() {
        return editProfileUseCase.getPostIndex();
    }

    public int getScheduleIndex() {
        return editProfileUseCase.getScheduleIndex();
    }

    public int getSectorIndex() {
        return editProfileUseCase.getSectorIndex();
    }

    public ArrayAdapter<String> getAdapterPosts() {
        return editProfileUseCase.getAdapterPosts();
    }

    public ArrayAdapter<String> getAdapterSectors() {
        return editProfileUseCase.getAdapterSectors();
    }

    public ArrayAdapter<String> getAdapterSchedules() {
        return editProfileUseCase.getAdapterSchedules();
    }

    public String getUserName() {
        return editProfileUseCase.getUserName();
    }

    public void updateTax(Tax newTax) {
        editTaxUseCase.updateTax(newTax);
    }

    public Tax getTax() {
        return editTaxUseCase.getTax();
    }

    public void recoverData() {
        recoverDataUseCase.recoverData();
    }

    public void syncData() {
        syncDataUseCase.synchronize();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
