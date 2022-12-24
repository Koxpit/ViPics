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
import android.widget.CompoundButton;
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
import java.util.Locale;

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

    int day, month, year;
    private double tax_selection_os = 0, tax_allocation_os = 0;
    private double tax_selection_mez = 0, tax_allocation_mez = 0;

    private int selectionOs = 0, allocationOs = 0;
    private int selectionMez = 0, allocationMez = 0;
    double bonus_selection_mez_80 = 0, bonus_selection_os_80 = 0, bonus_allocation_mez_80 = 0, bonus_allocation_os_80 = 0;
    private int isExtraDay = 0;
    private double pay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picks);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        day = bundle.getInt("day");
        month = bundle.getInt("month");
        year = bundle.getInt("year");
        selectionOs = bundle.getInt("selectionOs");
        allocationOs = bundle.getInt("allocationOs");
        selectionMez = bundle.getInt("selectionMez");
        allocationMez = bundle.getInt("allocationMez");
        isExtraDay = bundle.getInt("isExtraDay");
        pay = bundle.getDouble("pay");
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

        selection_os_editText.setText(String.valueOf(selectionOs));
        allocation_os_editText.setText(String.valueOf(allocationOs));
        selection_mez_editText.setText(String.valueOf(selectionMez));
        allocation_mez_editText.setText(String.valueOf(allocationMez));
        if (isExtraDay == 1) {
            isExtraShift_checkBox.setChecked(true);
            extraShiftPay_editText.setEnabled(true);
        }
        else {
            isExtraShift_checkBox.setChecked(false);
            extraShiftPay_editText.setEnabled(false);
        }
        extraShiftPay_editText.setText(String.format(Locale.ENGLISH, "%(.2f", pay));

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

            setTax(db, String.valueOf(sector_id), String.valueOf(schedule_id));
            dbHelper.close();
            db.close();

            if (isExtraShift_checkBox.isChecked()) {
                isExtraDay = 1;
                pay = Double.parseDouble(extraShiftPay_editText.getText().toString());
            }
            else {
                isExtraDay = 0;
                double selection_os_pay = (tax_selection_os + bonus_selection_os_80) * selectionOs;
                double allocation_os_pay = (tax_allocation_os + bonus_allocation_os_80) * allocationOs;
                double selection_mez_pay = (tax_selection_mez + bonus_selection_mez_80) * selectionMez;
                double allocation_mez_pay = (tax_allocation_mez + bonus_allocation_mez_80) * allocationMez;

                pay = selection_os_pay + allocation_os_pay + selection_mez_pay + allocation_mez_pay;
            }

            WorkDay workDay = new WorkDay(day, month, year, isExtraDay, selectionOs, allocationOs, selectionMez, allocationMez, pay);
            addWorkDay(workDay);

            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        });

        isExtraShift_checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isExtraDay == 0) {
                isExtraDay = 1;
                extraShiftPay_editText.setEnabled(true);
            }
            else if (isExtraDay == 1) {
                isExtraDay = 0;
                extraShiftPay_editText.setEnabled(false);
            }
        });
    }

    private void setTax(SQLiteDatabase db, String sector_id, String schedule_id) {
        String[] selectionArgs = { sector_id, schedule_id };

        String[] columnsTaxes = {DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX,
                DbHelper.KEY_BONUS_SELECTION_MEZ_80, DbHelper.KEY_BONUS_SELECTION_OS_80, DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, DbHelper.KEY_BONUS_ALLOCATION_OS_80};
        Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?" , selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
            int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
            int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
            int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

            int bonusSelectionMez80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_MEZ_80);
            int bonusAllocationMez80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80);

            int bonusSelectionOs80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_OS_80);
            int bonusAllocationOs80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_OS_80);

            tax_selection_os = cursor.getDouble(selectionOsTaxIndex);
            tax_allocation_os = cursor.getDouble(allocationOsTaxIndex);
            tax_selection_mez = cursor.getDouble(selectionMezTaxIndex);
            tax_allocation_mez = cursor.getDouble(allocationMezTaxIndex);

            bonus_selection_mez_80 = cursor.getDouble(bonusSelectionMez80Index);
            bonus_allocation_mez_80 = cursor.getDouble(bonusAllocationMez80Index);

            bonus_selection_os_80 = cursor.getDouble(bonusSelectionOs80Index);
            bonus_allocation_os_80 = cursor.getDouble(bonusAllocationOs80Index);
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

        if (isExtraShift_checkBox.isChecked()) {
            if (TextUtils.isEmpty(extraShiftPay_editText.getText().toString())) {
                pay = 0;
            } else {
                pay = Double.parseDouble(extraShiftPay_editText.getText().toString());
            }
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

        String[] columnsWorkDays = {DbHelper.KEY_ID};
        String[] selectionSectorsArgs = {String.valueOf(year), String.valueOf(month), String.valueOf(day)};
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columnsWorkDays, DbHelper.KEY_YEAR + " = ? AND " + DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_DAY + " = ?", selectionSectorsArgs, null, null, null);

        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
            int id = cursor.getInt(idIndex);
            db.update(DbHelper.TABLE_WORKDAYS, contentValues, DbHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)});
        }
        else {
            db.insert(DbHelper.TABLE_WORKDAYS, null, contentValues);
        }

        cursor.close();
        dbHelper.close();
        db.close();
    }
}