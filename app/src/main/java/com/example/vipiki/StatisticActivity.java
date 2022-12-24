package com.example.vipiki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vipiki.database.DbHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class StatisticActivity extends AppCompatActivity {
    TextView userNameTextView;
    TextView userPostTextView;
    TextView userScheduleTextView;
    TextView userSectorTextView;
    TextView userSalaryTextView;
    TextView userYearSalaryTextView;
    TextView taxAllocationOsTextView;
    TextView taxSelectionOsTextView;
    TextView taxAllocationMezTextView;
    TextView taxSelectionMezTextView;
    RadioGroup radioGroupBonuses;

    DbHelper dbHelper;
    SQLiteDatabase db;
    SharedPreferences settings;

    public static final String APP_PREFERENCES = "app_settings";
    final String KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_BONUS_INDEX";
    double tax_selection_os = 0, tax_allocation_os = 0;
    double tax_selection_mez = 0, tax_allocation_mez = 0;
    double bonus_selection_mez_80 = 0, bonus_selection_mez_90 = 0, bonus_selection_mez_100 = 0, bonus_selection_mez_120 = 0;
    double bonus_selection_os_80 = 0, bonus_selection_os_90 = 0, bonus_selection_os_100 = 0, bonus_selection_os_120 = 0;
    double bonus_allocation_mez_80 = 0, bonus_allocation_mez_90 = 0, bonus_allocation_mez_100 = 0, bonus_allocation_mez_120 = 0;
    double bonus_allocation_os_80 = 0, bonus_allocation_os_90 = 0, bonus_allocation_os_100 = 0, bonus_allocation_os_120 = 0;
    double bonusSelectionMez = 0, bonusSelectionOs = 0, bonusAllocationMez = 0, bonusAllocationOs = 0;
    double salary = 0;
    String schedule, sector, post, name;
    int checkedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        initComponents();

        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        int sector_id = dbHelper.getSectorId(settings, db);
        int schedule_id = dbHelper.getScheduleId(settings, db);

        setProfileData(settings);
        setTaxes(sector_id, schedule_id);
        setBonuses();
        radioGroupBonuses.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
        setSalary();
        setYearSalary();

        dbHelper.close();
        db.close();
    }

    private void initComponents() {
        userNameTextView = findViewById(R.id.userNameTextView);
        userPostTextView = findViewById(R.id.userPostTextView);
        userScheduleTextView = findViewById(R.id.userScheduleTextView);
        userSectorTextView = findViewById(R.id.userSectorTextView);
        userSalaryTextView = findViewById(R.id.userSalaryTextView);
        userYearSalaryTextView = findViewById(R.id.userYearSalaryTextView);
        taxAllocationOsTextView = findViewById(R.id.taxAllocationOsTextView);
        taxSelectionOsTextView = findViewById(R.id.taxSelectionOsTextView);
        taxAllocationMezTextView = findViewById(R.id.taxAllocationMezTextView);
        taxSelectionMezTextView = findViewById(R.id.taxSelectionMezTextView);
        radioGroupBonuses = findViewById(R.id.bonusRadioGroup);
    }

    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            RadioButton checkedRadioButton = (RadioButton) radioGroupBonuses
                    .findViewById(checkedId);
            checkedIndex = radioGroupBonuses.indexOfChild(checkedRadioButton);

            setBonuses();

            setSalary();
            setYearSalary();

            SavePreferences(KEY_RADIOBUTTON_INDEX, checkedIndex);
        }
    };

    private void setBonuses() {
        if (checkedIndex == 0) {
            bonusSelectionMez = bonus_selection_mez_120;
            bonusSelectionOs = bonus_selection_os_120;
            bonusAllocationMez = bonus_allocation_mez_120;
            bonusAllocationOs = bonus_allocation_os_120;
        }
        else if (checkedIndex == 1) {
            bonusSelectionMez = bonus_selection_mez_100;
            bonusSelectionOs = bonus_selection_os_100;
            bonusAllocationMez = bonus_allocation_mez_100;
            bonusAllocationOs = bonus_allocation_os_100;
        }
        else if (checkedIndex == 2) {
            bonusSelectionMez = bonus_selection_mez_90;
            bonusSelectionOs = bonus_selection_os_90;
            bonusAllocationMez = bonus_allocation_mez_90;
            bonusAllocationOs = bonus_allocation_os_90;
        }
        else if (checkedIndex == 3) {
            bonusSelectionMez = bonus_selection_mez_80;
            bonusSelectionOs = bonus_selection_os_80;
            bonusAllocationMez = bonus_allocation_mez_80;
            bonusAllocationOs = bonus_allocation_os_80;
        }
    }

    private void SavePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void setProfileData(SharedPreferences settings) {
        schedule = settings.getString("schedule", null);
        sector = settings.getString("sector", null);
        post = settings.getString("post", null);
        name = settings.getString("name", null);
        checkedIndex = settings.getInt(KEY_RADIOBUTTON_INDEX, 0);

        userNameTextView.setText(name);
        userPostTextView.setText(post);
        userScheduleTextView.setText(schedule);
        userSectorTextView.setText(sector);

        RadioButton savedCheckedRadioButton = (RadioButton) radioGroupBonuses
                .getChildAt(checkedIndex);
        savedCheckedRadioButton.setChecked(true);
    }

    private void setTaxes(int sector_id, int schedule_id) {
        try {
            if (sector_id == -1 || schedule_id == -1)
                return;

            String[] selectionArgs = {String.valueOf(sector_id), String.valueOf(schedule_id)};
            String[] columnsTaxes = {DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX,
            DbHelper.KEY_BONUS_SELECTION_MEZ_80, DbHelper.KEY_BONUS_SELECTION_MEZ_90, DbHelper.KEY_BONUS_SELECTION_MEZ_100, DbHelper.KEY_BONUS_SELECTION_MEZ_120,
                    DbHelper.KEY_BONUS_SELECTION_OS_80, DbHelper.KEY_BONUS_SELECTION_OS_90, DbHelper.KEY_BONUS_SELECTION_OS_100, DbHelper.KEY_BONUS_SELECTION_OS_120,
                    DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, DbHelper.KEY_BONUS_ALLOCATION_MEZ_120,
                    DbHelper.KEY_BONUS_ALLOCATION_OS_80, DbHelper.KEY_BONUS_ALLOCATION_OS_90, DbHelper.KEY_BONUS_ALLOCATION_OS_100, DbHelper.KEY_BONUS_ALLOCATION_OS_120};
            Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

            while (cursor.moveToNext()) {
                int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
                int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
                int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
                int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

                int bonusSelectionMez80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_MEZ_80);
                int bonusSelectionMez90Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_MEZ_90);
                int bonusSelectionMez100Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_MEZ_100);
                int bonusSelectionMez120Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_MEZ_120);
                int bonusAllocationMez80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80);
                int bonusAllocationMez90Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90);
                int bonusAllocationMez100Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100);
                int bonusAllocationMez120Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120);

                int bonusSelectionOs80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_OS_80);
                int bonusSelectionOs90Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_OS_90);
                int bonusSelectionOs100Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_OS_100);
                int bonusSelectionOs120Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_SELECTION_OS_120);
                int bonusAllocationOs80Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_OS_80);
                int bonusAllocationOs90Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_OS_90);
                int bonusAllocationOs100Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_OS_100);
                int bonusAllocationOs120Index = cursor.getColumnIndex(DbHelper.KEY_BONUS_ALLOCATION_OS_120);

                tax_selection_os = cursor.getDouble(selectionOsTaxIndex);
                tax_allocation_os = cursor.getDouble(allocationOsTaxIndex);
                tax_selection_mez = cursor.getDouble(selectionMezTaxIndex);
                tax_allocation_mez = cursor.getDouble(allocationMezTaxIndex);

                bonus_selection_mez_80 = cursor.getDouble(bonusSelectionMez80Index);
                bonus_selection_mez_90 = cursor.getDouble(bonusSelectionMez90Index);
                bonus_selection_mez_100 = cursor.getDouble(bonusSelectionMez100Index);
                bonus_selection_mez_120 = cursor.getDouble(bonusSelectionMez120Index);
                bonus_allocation_mez_80 = cursor.getDouble(bonusAllocationMez80Index);
                bonus_allocation_mez_90 = cursor.getDouble(bonusAllocationMez90Index);
                bonus_allocation_mez_100 = cursor.getDouble(bonusAllocationMez100Index);
                bonus_allocation_mez_120 = cursor.getDouble(bonusAllocationMez120Index);

                bonus_selection_os_80 = cursor.getDouble(bonusSelectionOs80Index);
                bonus_selection_os_90 = cursor.getDouble(bonusSelectionOs90Index);
                bonus_selection_os_100 = cursor.getDouble(bonusSelectionOs100Index);
                bonus_selection_os_120 = cursor.getDouble(bonusSelectionOs120Index);
                bonus_allocation_os_80 = cursor.getDouble(bonusAllocationOs80Index);
                bonus_allocation_os_90 = cursor.getDouble(bonusAllocationOs90Index);
                bonus_allocation_os_100 = cursor.getDouble(bonusAllocationOs100Index);
                bonus_allocation_os_120 = cursor.getDouble(bonusAllocationOs120Index);
            }

            double taxSelectionOs = 0, taxAllocationOs = 0, taxSelectionMez = 0, taxAllocationMez = 0;
            if (checkedIndex == 0) {
                taxSelectionOs = tax_selection_os + bonus_selection_os_120;
                taxAllocationOs = tax_allocation_os + bonus_allocation_os_120;
                taxSelectionMez = tax_selection_mez + bonus_selection_mez_120;
                taxAllocationMez = tax_allocation_mez + bonus_allocation_mez_120;
            }
            else if (checkedIndex == 1) {
                taxSelectionOs = tax_selection_os + bonus_selection_os_100;
                taxAllocationOs = tax_allocation_os + bonus_allocation_os_100;
                taxSelectionMez = tax_selection_mez + bonus_selection_mez_100;
                taxAllocationMez = tax_allocation_mez + bonus_allocation_mez_100;
            }
            else if (checkedIndex == 2) {
                taxSelectionOs = tax_selection_os + bonus_selection_os_90;
                taxAllocationOs = tax_allocation_os + bonus_allocation_os_90;
                taxSelectionMez = tax_selection_mez + bonus_selection_mez_90;
                taxAllocationMez = tax_allocation_mez + bonus_allocation_mez_90;
            }
            else if (checkedIndex == 3) {
                taxSelectionOs = tax_selection_os + bonus_selection_os_80;
                taxAllocationOs = tax_allocation_os + bonus_allocation_os_80;
                taxSelectionMez = tax_selection_mez + bonus_selection_mez_80;
                taxAllocationMez = tax_allocation_mez + bonus_allocation_mez_80;
            }
            else {
                taxSelectionOs = tax_selection_os;
                taxAllocationOs = tax_allocation_os;
                taxSelectionMez = tax_selection_mez;
                taxAllocationMez = tax_allocation_mez;
            }

            taxSelectionOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", taxSelectionOs));
            taxAllocationOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", taxAllocationOs));
            taxSelectionMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", taxSelectionMez));
            taxAllocationMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", taxAllocationMez));

            cursor.close();
        }
        catch (Exception e) {
            Snackbar.make(findViewById(R.id.rootRelativeLayout), e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void setSalary() {
        try {
            db = dbHelper.getReadableDatabase();
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH)+1;
            Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, new String[]{DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ}, DbHelper.KEY_MONTH + "=?", new String[] {String.valueOf(month)}, null, null, null);

            int selectionOsIndex, selectionMezIndex, allocationOsIndex, allocationMezIndex;
            int selectionOs, selectionMez, allocationOs, allocationMez;
            double selectionOsPay = 0, selectionMezPay = 0, allocationOsPay = 0, allocationMezPay = 0;

            salary = 0;
            while (cursor.moveToNext()) {
                    selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
                    selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
                    allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
                    allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);

                    selectionOs = cursor.getInt(selectionOsIndex);
                    selectionMez = cursor.getInt(selectionMezIndex);
                    allocationOs = cursor.getInt(allocationOsIndex);
                    allocationMez = cursor.getInt(allocationMezIndex);

                    selectionOsPay = (tax_selection_os + bonusSelectionOs) * selectionOs;
                    selectionMezPay = (tax_selection_mez + bonusSelectionMez) * selectionMez;
                    allocationOsPay = (tax_allocation_os + bonusAllocationOs) * allocationOs;
                    allocationMezPay = (tax_allocation_mez + bonusAllocationMez) * allocationMez;

                    salary += selectionOsPay + selectionMezPay + allocationOsPay + allocationMezPay;
                }
            taxSelectionOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax_selection_os + bonusSelectionOs));
            taxAllocationOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax_allocation_os + bonusAllocationOs));
            taxSelectionMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax_selection_mez + bonusSelectionMez));
            taxAllocationMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax_allocation_mez + bonusAllocationMez));
            userSalaryTextView.setText(String.format(Locale.ENGLISH, "%(.2f", salary));

            cursor.close();
        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.rootRelativeLayout), e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void setYearSalary() {
        int yearNorm = dbHelper.getYearNorm(db, settings);
        double tax = 0;
        if (Objects.equals(post, "Отборщик мезонина")) {
            if (checkedIndex == 0)
                tax = tax_selection_mez + bonus_selection_mez_120;
            else if (checkedIndex == 1)
                tax = tax_selection_mez + bonus_selection_mez_100;
            else if (checkedIndex == 2)
                tax = tax_selection_mez + bonus_selection_mez_90;
            else if (checkedIndex == 3)
                tax = tax_selection_mez + bonus_selection_mez_80;
        }
        else if (Objects.equals(post, "Отборщик основы")) {
            if (checkedIndex == 0)
                tax = tax_selection_os + bonus_selection_os_120;
            else if (checkedIndex == 1)
                tax = tax_selection_os + bonus_selection_os_100;
            else if (checkedIndex == 2)
                tax = tax_selection_os + bonus_selection_os_90;
            else if (checkedIndex == 3)
                tax = tax_selection_os + bonus_selection_os_80;
        }
        else if (Objects.equals(post, "Размещенец мезонина")) {
            if (checkedIndex == 0)
                tax = tax_allocation_mez + bonus_allocation_mez_120;
            else if (checkedIndex == 1)
                tax = tax_allocation_mez + bonus_allocation_mez_100;
            else if (checkedIndex == 2)
                tax = tax_allocation_mez + bonus_allocation_mez_90;
            else if (checkedIndex == 3)
                tax = tax_allocation_mez + bonus_allocation_mez_80;
        }
        else if (Objects.equals(post, "Размещенец основы")) {
            if (checkedIndex == 0)
                tax = tax_allocation_os + bonus_allocation_os_120;
            else if (checkedIndex == 1)
                tax = tax_allocation_os + bonus_allocation_os_100;
            else if (checkedIndex == 2)
                tax = tax_allocation_os + bonus_allocation_os_90;
            else if (checkedIndex == 3)
                tax = tax_allocation_os + bonus_allocation_os_80;
        }

        double yearSalary = yearNorm * tax;
        userYearSalaryTextView.setText(String.format(Locale.ENGLISH, "%(.2f", yearSalary));
    }
}