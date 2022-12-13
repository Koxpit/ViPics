package com.example.vipiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.WorkDay;
import com.example.vipiki.ui.addPicks.AddPicksFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;

public class Add_Picks_Activity extends AppCompatActivity {
    DbHelper dbHelper;
    SQLiteDatabase db;
    RelativeLayout rootRelativeLayout;
    Button add_button, back_button;
    EditText selection_os_editText;
    EditText allocation_os_editText;
    EditText selection_mez_editText;
    EditText allocation_mez_editText;
    EditText extraShiftPay_editText;
    CheckBox isExtraShift_checkBox;

    private double tax_selection_os = 0, tax_allocation_os = 0;
    private double tax_selection_mez = 0, tax_allocation_mez = 0;

    private int selectionOs, allocationOs, selectionMez, allocationMez;
    private int isExtraDay = 0;
    private double pay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picks);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int day = bundle.getInt("day");
        int month = bundle.getInt("month");
        int year = bundle.getInt("year");
        Log.d("WORK_DATE", String.format("%d.%d.%d", year, month, day));

        rootRelativeLayout = findViewById(R.id.rootRelativeLayout);
        add_button = findViewById(R.id.save_button);
        back_button = findViewById(R.id.back_button);
        selection_os_editText = findViewById(R.id.selectionOS_editText);
        allocation_os_editText = findViewById(R.id.allocationOS_editText);
        selection_mez_editText = findViewById(R.id.selectionMez_editText);
        allocation_mez_editText = findViewById(R.id.allocationMez_editText);
        extraShiftPay_editText = findViewById(R.id.extraShiftPay_editText);
        isExtraShift_checkBox = findViewById(R.id.isExtraShift_checkBox);

        add_button.setOnClickListener(view -> {
            correctEntry();
            dbHelper = new DbHelper(this);
            db = dbHelper.getWritableDatabase();
            SharedPreferences settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
            int sector_id = dbHelper.getSectorId(settings, db);
            int schedule_id = dbHelper.getScheduleId(settings, db);

            if (sector_id == -1) {
                sendSectorNotFoundError();
            }
            if (schedule_id == -1) {
                sendScheduleNotFoundError();
            }

            setTax(db, String.valueOf(1), String.valueOf(2));
            dbHelper.close();
            db.close();

            if (isExtraShift_checkBox.isChecked()) {
                isExtraDay = 1;
            }
            else {
                isExtraDay = 0;
                double selection_os_pay = tax_selection_os * selectionOs;
                double allocation_os_pay = tax_allocation_os * allocationOs;
                double selection_mez_pay = tax_selection_mez * selectionMez;
                double allocation_mez_pay = tax_allocation_mez * allocationMez;

                pay = selection_os_pay + allocation_os_pay + selection_mez_pay + allocation_mez_pay;
            }

            WorkDay workDay = new WorkDay(day, month, year, isExtraDay, selectionOs, allocationOs, selectionMez, allocationMez, pay);
            addWorkDay(workDay);

            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        });
    }

    private void setTax(SQLiteDatabase db, String sector_id, String schedule_id) {
        String[] selectionArgs = { sector_id, schedule_id };

        String[] columnsTaxes = { DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX };
        Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?" , selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
            int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
            int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
            int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

            tax_selection_os = cursor.getDouble(selectionOsTaxIndex);
            tax_allocation_os = cursor.getDouble(allocationOsTaxIndex);
            tax_selection_mez = cursor.getDouble(selectionMezTaxIndex);
            tax_allocation_mez = cursor.getDouble(allocationMezTaxIndex);
        }
        cursor.close();
    }

    private void sendSectorNotFoundError() {
        Snackbar.make(rootRelativeLayout, "Ошибка: сектор не найден.", Snackbar.LENGTH_SHORT).show();
        Log.d("SECTOR_NOT_FOUND", "Сектор не найден в БД");
    }

    private void sendScheduleNotFoundError() {
        Snackbar.make(rootRelativeLayout, "Ошибка: график работы не найден.", Snackbar.LENGTH_SHORT).show();
        Log.d("SCHEDULE_NOT_FOUND", "График работы не найден в БД");
    }

    private void correctEntry() {
        if (TextUtils.isEmpty(selection_os_editText.getText().toString())) {
            selectionOs = 0;
        }
        else {
            selectionOs = Integer.parseInt(selection_os_editText.getText().toString());
        }

        if (TextUtils.isEmpty(allocation_os_editText.getText().toString())) {
            allocationOs = 0;
        }
        else {
            allocationOs = Integer.parseInt(allocation_os_editText.getText().toString());
        }

        if (TextUtils.isEmpty(selection_mez_editText.getText().toString())) {
            selectionMez = 0;
        }
        else {
            selectionMez = Integer.parseInt(selection_mez_editText.getText().toString());
        }

        if (TextUtils.isEmpty(allocation_mez_editText.getText().toString())) {
            allocationMez = 0;
        }
        else {
            allocationMez = Integer.parseInt(allocation_mez_editText.getText().toString());
        }

        if (TextUtils.isEmpty(extraShiftPay_editText.getText().toString())) {
            pay = 0;
        }
        else {
            pay = Double.parseDouble(extraShiftPay_editText.getText().toString());
        }
    }

    private void addWorkDay(WorkDay workDay) {
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbHelper.KEY_DAY, workDay.getDay());
        contentValues.put(DbHelper.KEY_MONTH, workDay.getMonth());
        contentValues.put(DbHelper.KEY_YEAR, workDay.getYear());
        contentValues.put(DbHelper.KEY_SELECTION_OS, workDay.getSelectionOs());
        contentValues.put(DbHelper.KEY_ALLOCATION_OS, workDay.getAllocationOs());
        contentValues.put(DbHelper.KEY_SELECTION_MEZ, workDay.getSelectionMez());
        contentValues.put(DbHelper.KEY_ALLOCATION_MEZ, workDay.getAllocationMez());
        contentValues.put(DbHelper.KEY_IS_EXTRA_PAY, workDay.getExtraDay());
        contentValues.put(DbHelper.KEY_PAY, workDay.getPay());

        db.insert(DbHelper.TABLE_WORKDAYS, null, contentValues);
        dbHelper.close();
        db.close();
    }
}