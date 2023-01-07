package com.example.vipiki.ui.settings;

import android.text.TextUtils;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.Bonus;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;
import com.example.vipiki.ui.settings.settingsUseCases.EditBonusesUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.GetAuthSettingsUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.EditProfileUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.EditTaxUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.RecoverDataUseCase;
import com.example.vipiki.ui.settings.settingsUseCases.SyncDataUseCase;

import java.util.Objects;

public class SettingsViewModel extends ViewModel {
    private final EditProfileUseCase editProfileUseCase;
    private final EditTaxUseCase editTaxUseCase;
    private final RecoverDataUseCase recoverDataUseCase;
    private final SyncDataUseCase syncDataUseCase;
    private final GetAuthSettingsUseCase getAuthSettingsUseCase;
    private final EditBonusesUseCase editBonusesUseCase;

    public SettingsViewModel(EditProfileUseCase editProfileUseCase, EditTaxUseCase editTaxUseCase,
                             RecoverDataUseCase recoverDataUseCase, SyncDataUseCase syncDataUseCase,
                             GetAuthSettingsUseCase getAuthSettingsUseCase, EditBonusesUseCase editBonusesUseCase) {
        this.editProfileUseCase = editProfileUseCase;
        this.editTaxUseCase = editTaxUseCase;
        this.recoverDataUseCase = recoverDataUseCase;
        this.syncDataUseCase = syncDataUseCase;
        this.getAuthSettingsUseCase = getAuthSettingsUseCase;
        this.editBonusesUseCase = editBonusesUseCase;
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

    public void updateBonuses(Bonus newBonus) { editBonusesUseCase.updateBonuses(newBonus); }

    public Bonus getBonus() { return editBonusesUseCase.getBonuses(); }

    public void setBonusIndex(int bonusIndex) {
        editBonusesUseCase.setBonusIndex(bonusIndex);
    }

    public int getCheckedBonusIndex() {
        return editBonusesUseCase.getCheckedBonusIndex();
    }

    public void recoverData() {
        recoverDataUseCase.recoverData();
    }

    public void syncData() {
        syncDataUseCase.synchronize();
    }

    public boolean passwordsMatch(String newPassword, String confirmPassword) {
        return Objects.equals(newPassword, confirmPassword);
    }

    public String getEmail() {
        return getAuthSettingsUseCase.getEmail();
    }

    public void saveNewEmail(String newEmail) {
        editProfileUseCase.editEmail(newEmail);
    }

    public boolean inputIncorrect(String oldPassword, String newPassword, String confirmPassword) {
        return TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword);
    }

    public boolean passwordIncorrect(String password) {
        return password.length() < 6;
    }

    public String getUID() {
        return getAuthSettingsUseCase.getUID();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
