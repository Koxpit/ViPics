package com.example.vipiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vipiki.database.DbHelper;
import com.google.android.material.snackbar.Snackbar;

public class StatisticActivity extends AppCompatActivity {
    TextView userNameTextView;
    TextView userPostTextView;
    TextView userScheduleTextView;
    TextView userSectorTextView;
    TextView taxAllocationOsTextView;
    TextView taxSelectionOsTextView;
    TextView taxAllocationMezTextView;
    TextView taxSelectionMezTextView;
    TextView userSalaryTextView;

    DbHelper dbHelper;
    SQLiteDatabase db;
    double tax_selection_os = 0, tax_allocation_os = 0;
    double tax_selection_mez = 0, tax_allocation_mez = 0;
    double salary = 0;
    String schedule, sector, post, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initComponents();

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        SharedPreferences settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);

        int sector_id = dbHelper.getSectorId(settings, db);
        int schedule_id = dbHelper.getScheduleId(settings, db);

        setProfileData(settings);
        setTaxes(sector_id, schedule_id);
        setSalary();

        dbHelper.close();
        db.close();
    }

    private void initComponents() {
        userNameTextView = findViewById(R.id.userNameTextView);
        userPostTextView = findViewById(R.id.userPostTextView);
        userScheduleTextView = findViewById(R.id.userScheduleTextView);
        userSectorTextView = findViewById(R.id.userSectorTextView);
        taxAllocationOsTextView = findViewById(R.id.taxAllocationOsTextView);
        taxSelectionOsTextView = findViewById(R.id.taxSelectionOsTextView);
        taxAllocationMezTextView = findViewById(R.id.taxAllocationMezTextView);
        taxSelectionMezTextView = findViewById(R.id.taxSelectionMezTextView);
        userSalaryTextView = findViewById(R.id.userSalaryTextView);
    }

    private void setProfileData(SharedPreferences settings) {
        schedule = settings.getString("schedule", null);
        sector = settings.getString("sector", null);
        post = settings.getString("post", null);
        name = settings.getString("name", null);

        userNameTextView.setText(name);
        userPostTextView.setText(post);
        userScheduleTextView.setText(schedule);
        userSectorTextView.setText(sector);
    }

    private void setTaxes(int sector_id, int schedule_id) {
        try {
            if (sector_id == -1 || schedule_id == -1)
                return;

            String[] selectionArgs = {String.valueOf(sector_id), String.valueOf(schedule_id)};
            String[] columnsTaxes = {DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX};
            Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

            while (cursor.moveToNext()) {
                int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
                int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
                int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
                int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

                tax_selection_os = cursor.getDouble(selectionOsTaxIndex);
                tax_allocation_os = cursor.getDouble(allocationOsTaxIndex);
                tax_selection_mez = cursor.getDouble(selectionMezTaxIndex);
                tax_allocation_mez = cursor.getDouble(allocationMezTaxIndex);
            }

            taxSelectionOsTextView.setText(String.valueOf(tax_selection_os));
            taxAllocationOsTextView.setText(String.valueOf(tax_allocation_os));
            taxSelectionMezTextView.setText(String.valueOf(tax_selection_mez));
            taxAllocationMezTextView.setText(String.valueOf(tax_allocation_mez));

            cursor.close();
        }
        catch (Exception e) {
            Snackbar.make(findViewById(R.id.rootRelativeLayout), e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void setSalary() {
        try {
            Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, new String[]{DbHelper.KEY_PAY}, null, null, null, null, null);

            if (cursor.moveToNext()) {
                do {
                    int payIndex = cursor.getColumnIndex(DbHelper.KEY_PAY);
                    salary += cursor.getDouble(payIndex);
                } while (cursor.moveToNext());
            }

            userSalaryTextView.setText(String.valueOf(salary));

            cursor.close();
        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.rootRelativeLayout), e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}