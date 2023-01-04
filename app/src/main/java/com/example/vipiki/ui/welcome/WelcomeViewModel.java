package com.example.vipiki.ui.welcome;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.User;
import com.example.vipiki.ui.welcome.welcomeUseCases.AuthUserUseCase;
import com.example.vipiki.ui.welcome.welcomeUseCases.InitRegisterSpinnersUseCase;
import com.example.vipiki.ui.welcome.welcomeUseCases.RegisterUserUseCase;

import java.util.List;

public class WelcomeViewModel extends ViewModel {
    private final AuthUserUseCase authUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final InitRegisterSpinnersUseCase initRegisterSpinnersUseCase;
    private final String[] postulatesTitles = new String[] {"Команда", "Клиент", "Эффективность"};

    public WelcomeViewModel(AuthUserUseCase authUserUseCase, RegisterUserUseCase registerUserUseCase,
                            InitRegisterSpinnersUseCase initRegisterSpinnersUseCase) {
        this.authUserUseCase = authUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
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

    public void authUser(String email, String password) {
        authUserUseCase.authUser(email, password);
    }

    public boolean getAuthStatus() {
        return authUserUseCase.getAuthStatus();
    }

    public boolean authEntryIsEmpty(String email, String password) {
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password);
    }

    public boolean passwordIsIncorrect(String password) {
        return password.length() < 6;
    }

    public void registerUser(User newUser, String password) {
        registerUserUseCase.registerUser(newUser, password);
    }

    public boolean regEntryIsEmpty(String name, String email, String post, String sector, String schedule, String password) {
        return TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(post)
                || TextUtils.isEmpty(sector) || TextUtils.isEmpty(schedule);
    }

    @Override
    protected void onCleared() {
        Log.d("CLEAR_VM", "WelcomeViewModel cleared");
        super.onCleared();
    }
}
