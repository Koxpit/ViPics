package com.example.vipiki.ui.welcome;

import android.text.TextUtils;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.User;
import com.example.vipiki.ui.welcome.welcomeUseCases.AuthUserUseCase;
import com.example.vipiki.ui.welcome.welcomeUseCases.InitRegisterSpinnersUseCase;

public class WelcomeViewModel extends ViewModel {
    private final AuthUserUseCase authUserUseCase;
    private final InitRegisterSpinnersUseCase initRegisterSpinnersUseCase;
    private final String[] postulatesTitles = new String[] {"Команда", "Клиент", "Эффективность"};

    public WelcomeViewModel(AuthUserUseCase authUserUseCase,
                            InitRegisterSpinnersUseCase initRegisterSpinnersUseCase) {
        this.authUserUseCase = authUserUseCase;
        this.initRegisterSpinnersUseCase = initRegisterSpinnersUseCase;
    }

    public String getPostulateTitle() {
        int postulateId = (int)Math.floor(Math.random() * postulatesTitles.length);
        return postulatesTitles[postulateId];
    }

    public ArrayAdapter<String> getAdapterPosts() {
        return initRegisterSpinnersUseCase.getAdapterPosts();
    }

    public ArrayAdapter<String> getAdapterSectors() {
        return initRegisterSpinnersUseCase.getAdapterSectors();
    }

    public ArrayAdapter<String> getAdapterSchedules() {
        return initRegisterSpinnersUseCase.getAdapterSchedules();
    }

    public boolean isAlreadyAuthUser() {
        return authUserUseCase.isAlreadyAuthUser();
    }

    public void setUserSettings(User user, String UID, boolean userIsEmpty) {
        authUserUseCase.setUserSettings(user, UID, userIsEmpty);
    }

    public boolean userIsEmpty() {
        return authUserUseCase.userIsEmpty();
    }

    public String getUID() {
        return authUserUseCase.getUID();
    }

    public boolean authEntryIsEmpty(String email, String password) {
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password);
    }

    public boolean passwordIsIncorrect(String password) {
        return password.length() < 6;
    }

    public boolean regEntryIsEmpty(String name, String email, String post, String sector, String schedule, String password) {
        return TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(post)
                || TextUtils.isEmpty(sector) || TextUtils.isEmpty(schedule);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
