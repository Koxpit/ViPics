package com.example.vipiki.ui.addPicks;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;

import com.example.vipiki.models.Pics;
import com.example.vipiki.ui.addPicks.addPicksUseCases.ChangeWorkDaysUseCase;
import com.example.vipiki.ui.addPicks.addPicksUseCases.DeleteWorkDayUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ChangePicksViewModel extends ViewModel {
    private final ChangeWorkDaysUseCase addWorkDayUseCase;
    private final DeleteWorkDayUseCase deleteWorkDayUseCase;

    public ChangePicksViewModel(ChangeWorkDaysUseCase addWorkDayUseCase, DeleteWorkDayUseCase deleteWorkDayUseCase) {
        this.addWorkDayUseCase = addWorkDayUseCase;
        this.deleteWorkDayUseCase = deleteWorkDayUseCase;
    }

    public void addWorkDay(int day, int month, int year, Pics pics) {
        addWorkDayUseCase.addWorkDay(day, month, year, pics);
    }

    public void addExtraDay(int day, int month, int year, double pay) {
        addWorkDayUseCase.addExtraDay(day, month, year, pay);
    }

    public boolean isCorrectDate(int day, int month, int year) {
        boolean dateIsCorrect = false;
        Locale ruLocal = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", ruLocal);

        try {
            month += 1;
            Calendar calendar = Calendar.getInstance();
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
            Date selectedDate = simpleDateFormat.parse(year + "-" + month + "-" + day);

            dateIsCorrect = !(selectedDate != null && selectedDate.after(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    public String getDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd", new Locale("RU", "ru")).format(new Date(calendar.getTimeInMillis()));
    }

    public String getMessageWorkDay(Bundle args) {
        int selectionOs = args.getInt("selectionOs");
        int allocationOs = args.getInt("allocationOs");
        int selectionMez = args.getInt("selectionMez");
        int allocationMez = args.getInt("allocationMez");
        double pay = args.getDouble("pay");

        return "Отбор основа: " + selectionOs + ";\n" + "Размещение основа: " + allocationOs + ";\n"
                + "Отбор мезонин: " + selectionMez + ";\n" + "Размещение мезонин: " + allocationMez + ".\n" + "Минимальная оплата: "
                + String.format(Locale.ENGLISH, "%(.2f", pay) + "руб.";
    }

    public String getMessageExtraDay(double pay) {
        return "Доп смена.\nОплата: " + String.format(Locale.ENGLISH, "%(.2f", pay);
    }
}