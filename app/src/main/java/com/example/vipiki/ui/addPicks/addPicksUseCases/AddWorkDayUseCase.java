package com.example.vipiki.ui.addPicks.addPicksUseCases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.vipiki.models.Pics;
import com.example.vipiki.models.Tax;
import com.example.vipiki.ui.main.MainActivity;
import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.WorkDay;

import java.util.List;

public class AddWorkDayUseCase {
    private final Context context;
    private final SharedPreferences settings;

    public AddWorkDayUseCase(Context context, SharedPreferences settings) {
        this.context = context;
        this.settings = settings;
    }

    public void addWorkDay(int day, int month, int year, Pics pics) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Tax tax = dbHelper.getMinTax(db, settings);
        double pay = calculatePay(pics, tax);
        WorkDay workDay = new WorkDay(day, month, year, 0, pics.getSelectionOs(), pics.getAllocationOs(),
                pics.getSelectionMez(), pics.getAllocationMez(), pay, getUID());

        dbHelper.addWorkDay(db, workDay);

        dbHelper.close();
        db.close();

        Intent intentMain = new Intent(context, MainActivity.class);
        context.startActivity(intentMain);
    }

    private double calculatePay(Pics pics, Tax tax) {
        double selection_os_pay = (tax.getTax_selection_os() + tax.getBonus_selection_os_80()) * pics.getSelectionOs();
        double allocation_os_pay = (tax.getTax_allocation_os() + tax.getBonus_allocation_os_80()) * pics.getAllocationOs();
        double selection_mez_pay = (tax.getTax_selection_mez() + tax.getBonus_selection_mez_80()) * pics.getSelectionMez();
        double allocation_mez_pay = (tax.getTax_allocation_mez() + tax.getBonus_allocation_mez_80()) * pics.getAllocationMez();

        return selection_os_pay + allocation_os_pay + selection_mez_pay + allocation_mez_pay;
    }

    public void addExtraDay(int day, int month, int year, double pay) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        WorkDay extraDay = new WorkDay(day, month, year, 1, 0, 0, 0, 0, pay, getUID());
        dbHelper.addWorkDay(db, extraDay);
        dbHelper.close();
        db.close();

        Intent intentMain = new Intent(context, MainActivity.class);
        context.startActivity(intentMain);
    }

    public int findSectorId() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sectorName = settings.getString("sector", null);

        return dbHelper.getSectorId(sectorName, db);
    }

    public int findScheduleId() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String scheduleName = settings.getString("schedule", null);

        return dbHelper.getScheduleId(scheduleName, db);
    }

    public String getUID() {
        return settings.getString("UID", null);
    }

    public Bundle getWorkDayBundle(int day, int month, int year) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Bundle bundle = dbHelper.getWorkDayBundle(db, getUID(), day, month, year);
        dbHelper.close();
        db.close();

        return bundle;
    }

    public ArrayAdapter<String> getAdapterWorkDays() {
        DbHelper dbHelper = new DbHelper(context);
        List<String> workDays = dbHelper.getWorkDays();
        ArrayAdapter<String> adapterWorkDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, workDays);
        adapterWorkDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dbHelper.close();

        return adapterWorkDays;
    }
}
