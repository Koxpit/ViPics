package com.example.vipiki.ui.addPicks;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.Pics;
import com.example.vipiki.ui.addPicks.addPicksUseCases.AddWorkDayUseCase;
import com.example.vipiki.ui.addPicks.addPicksUseCases.DeleteWorkDayUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPicksViewModel extends ViewModel {
    private final AddWorkDayUseCase addWorkDayUseCase;
    private final DeleteWorkDayUseCase deleteWorkDayUseCase;

    public AddPicksViewModel(AddWorkDayUseCase addWorkDayUseCase, DeleteWorkDayUseCase deleteWorkDayUseCase) {
        this.addWorkDayUseCase = addWorkDayUseCase;
        this.deleteWorkDayUseCase = deleteWorkDayUseCase;
    }

    public void addWorkDay(int day, int month, int year, Pics pics) {
        addWorkDayUseCase.addWorkDay(day, month, year, pics);
    }

    public void addExtraDay(int day, int month, int year, double pay) {
        addWorkDayUseCase.addExtraDay(day, month, year, pay);
    }

    public int getSectorId() {
        return addWorkDayUseCase.findSectorId();
    }

    public int getScheduleId() {
        return addWorkDayUseCase.findScheduleId();
    }

    public boolean isCorrectDate(int day, int month, int year) {
        boolean dateIsCorrect = false;
        Locale ruLocal = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", ruLocal);
        Date currentDate = null, selectedDate = null;

        try {
            month += 1;
            Calendar calendar = Calendar.getInstance();
            currentDate = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
            selectedDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateIsCorrect = !selectedDate.after(currentDate);

        return dateIsCorrect;
    }

    public Bundle getWorkDayBundle(int day, int month, int year) {
        return addWorkDayUseCase.getWorkDayBundle(day, month, year);
    }

    public ArrayAdapter<String> getAdapterWorkDays() {
        return addWorkDayUseCase.getAdapterWorkDays();
    }

    public void deleteWorkDay(int day, int month, int year) {
        deleteWorkDayUseCase.deleteWorkDay(day, month, year);
    }
}