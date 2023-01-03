package com.example.vipiki.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.vipiki.models.Pics;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.WorkDay;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ViPikiDB";

    public static final String TABLE_WORKDAYS = "workdays";
    public static final String TABLE_POSTS = "posts";
    public static final String TABLE_SCHEDULES = "schedules";
    public static final String TABLE_SECTORS = "sectors";
    public static final String TABLE_TAXES = "taxes";
    public static final String TABLE_PRODUCTIVITY = "productivity";

    public static final String KEY_ID = "_id";
    public static final String KEY_USER_UID = "user_uid";
    public static final String KEY_POST_ID = "post_id";
    public static final String KEY_SECTOR_ID = "sector_id";
    public static final String KEY_SCHEDULE_ID = "schedule_id";
    public static final String KEY_SELECTION_OS_TAX = "selectionOsTax";
    public static final String KEY_SELECTION_MEZ_TAX = "selectionMezTax";
    public static final String KEY_ALLOCATION_OS_TAX = "allocationOsTax";
    public static final String KEY_ALLOCATION_MEZ_TAX = "allocationMezTax";
    public static final String KEY_WORK_DAYS_PER_MONTH = "workDaysMonth";
    public static final String KEY_WORK_HOURS_PER_DAY = "workHoursDay";
    public static final String KEY_HOUR_NORM = "hourNorm";
    public static final String KEY_MONTH_NORM = "monthNorm";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    public static final String KEY_SELECTION_OS = "selectionOs";
    public static final String KEY_ALLOCATION_OS = "allocationOs";
    public static final String KEY_SELECTION_MEZ = "selectionMez";
    public static final String KEY_ALLOCATION_MEZ = "allocationMez";
    public static final String KEY_BONUS_SELECTION_MEZ_80 = "bonus_selection_mez_80";
    public static final String KEY_BONUS_SELECTION_MEZ_90 = "bonus_selection_mez_90";
    public static final String KEY_BONUS_SELECTION_MEZ_100 = "bonus_selection_mez_100";
    public static final String KEY_BONUS_SELECTION_MEZ_120 = "bonus_selection_mez_120";
    public static final String KEY_BONUS_ALLOCATION_MEZ_80 = "bonus_allocation_mez_80";
    public static final String KEY_BONUS_ALLOCATION_MEZ_90 = "bonus_allocation_mez_90";
    public static final String KEY_BONUS_ALLOCATION_MEZ_100 = "bonus_allocation_mez_100";
    public static final String KEY_BONUS_ALLOCATION_MEZ_120 = "bonus_allocation_mez_120";
    public static final String KEY_BONUS_SELECTION_OS_80 = "bonus_selection_os_80";
    public static final String KEY_BONUS_SELECTION_OS_90 = "bonus_selection_os_90";
    public static final String KEY_BONUS_SELECTION_OS_100 = "bonus_selection_os_100";
    public static final String KEY_BONUS_SELECTION_OS_120 = "bonus_selection_os_120";
    public static final String KEY_BONUS_ALLOCATION_OS_80 = "bonus_allocation_os_80";
    public static final String KEY_BONUS_ALLOCATION_OS_90 = "bonus_allocation_os_90";
    public static final String KEY_BONUS_ALLOCATION_OS_100 = "bonus_allocation_os_100";
    public static final String KEY_BONUS_ALLOCATION_OS_120 = "bonus_allocation_os_120";
    public static final String KEY_IS_EXTRA_PAY = "isExtraPay";
    public static final String KEY_PAY = "pay";

    public static final String KEY_NAME = "name";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_WORKDAYS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DAY + " INTEGER NOT NULL, "
                + KEY_MONTH + " INTEGER NOT NULL, "
                + KEY_YEAR + " INTEGER NOT NULL, "
                + KEY_SELECTION_OS + " INTEGER NOT NULL, "
                + KEY_ALLOCATION_OS + " INTEGER NOT NULL, "
                + KEY_SELECTION_MEZ + " INTEGER NOT NULL, "
                + KEY_ALLOCATION_MEZ + " INTEGER NOT NULL, "
                + KEY_IS_EXTRA_PAY  + " BOOLEAN NOT NULL CHECK (" + KEY_IS_EXTRA_PAY + " IN (0, 1)), "
                + KEY_PAY + " INTEGER NOT NULL, "
                + KEY_USER_UID + " TEXT NOT NULL )");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_POSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT NOT NULL UNIQUE" + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SECTORS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT NOT NULL UNIQUE" + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT NOT NULL UNIQUE" + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TAXES + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_SCHEDULE_ID + " INTEGER NOT NULL, "
                + KEY_SECTOR_ID + " INTEGER NOT NULL, "
                + KEY_SELECTION_OS_TAX + " REAL NOT NULL, "
                + KEY_ALLOCATION_OS_TAX + " REAL NOT NULL, "
                + KEY_SELECTION_MEZ_TAX + " REAL NOT NULL, "
                + KEY_ALLOCATION_MEZ_TAX + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_MEZ_80 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_MEZ_90 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_MEZ_100 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_MEZ_120 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_OS_80 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_OS_90 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_OS_100 + " REAL NOT NULL, "
                + KEY_BONUS_SELECTION_OS_120 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_MEZ_80 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_MEZ_90 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_MEZ_100 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_MEZ_120 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_OS_80 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_OS_90 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_OS_100 + " REAL NOT NULL, "
                + KEY_BONUS_ALLOCATION_OS_120 + " REAL NOT NULL, "
                + "FOREIGN KEY (" + KEY_SCHEDULE_ID + ") REFERENCES " + TABLE_SCHEDULES + "(" + KEY_ID + "), "
                + "FOREIGN KEY (" + KEY_SECTOR_ID + ") REFERENCES " + TABLE_SECTORS + "(" + KEY_ID + ") " + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTIVITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_POST_ID + " INTEGER NOT NULL, "
                + KEY_SCHEDULE_ID + " INTEGER NOT NULL, "
                + KEY_SECTOR_ID + " INTEGER NOT NULL, "
                + KEY_WORK_DAYS_PER_MONTH + " INTEGER NOT NULL, "
                + KEY_WORK_HOURS_PER_DAY + " INTEGER NOT NULL, "
                + KEY_HOUR_NORM + " INTEGER NOT NULL, "
                + KEY_MONTH_NORM + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + KEY_POST_ID + ") REFERENCES " + TABLE_POSTS + "(" + KEY_ID + "), "
                + "FOREIGN KEY (" + KEY_SCHEDULE_ID + ") REFERENCES " + TABLE_SCHEDULES + "(" + KEY_ID + "), "
                + "FOREIGN KEY (" + KEY_SECTOR_ID + ") REFERENCES " + TABLE_SECTORS + "(" + KEY_ID + ") " + ")");

        FirstData firstData = new FirstData(db);
        firstData.initStartData();
        db.close();
    }


    public int getSectorId(SharedPreferences settings, SQLiteDatabase db) {
        int sector_id = -1;
        String sector = settings.getString("sector", null);

        if (sector != null) {
            String[] columnsSectors = {KEY_ID};
            String[] selectionSectorsArgs = {sector};
            Cursor cursor = db.query(TABLE_SECTORS, columnsSectors, KEY_NAME + " = ?", selectionSectorsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int sectorIdIndex = cursor.getColumnIndex(KEY_ID);
                sector_id = cursor.getInt(sectorIdIndex);
            }
            else {
                Log.d("SECTOR_NOT_FOUND", "Сектор не найден в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("SECTOR_NOT_FOUND", "Сектор не найден в настройках.");
        }

        return sector_id;
    }

    public int getSectorId(String sectorName, SQLiteDatabase db) {
        int sector_id = -1;

        if (sectorName != null) {
            String[] columnsSectors = {KEY_ID};
            String[] selectionSectorsArgs = {sectorName};
            Cursor cursor = db.query(TABLE_SECTORS, columnsSectors, KEY_NAME + " = ?", selectionSectorsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int sectorIdIndex = cursor.getColumnIndex(KEY_ID);
                sector_id = cursor.getInt(sectorIdIndex);
            }
            else {
                Log.d("SECTOR_NOT_FOUND", "Сектор не найден в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("SECTOR_NOT_FOUND", "Сектор не указан (null).");
        }

        return sector_id;
    }

    public int getScheduleId(SharedPreferences settings, SQLiteDatabase db) {
        int schedule_id = -1;
        String schedule = settings.getString("schedule", null);

        if (schedule != null) {
            String[] columnsSchedules = {DbHelper.KEY_ID};
            String[] selectionSchedulesArgs = {schedule};
            Cursor cursor = db.query(DbHelper.TABLE_SCHEDULES, columnsSchedules, DbHelper.KEY_NAME + " = ?", selectionSchedulesArgs, null, null, null);

            if (cursor.moveToNext()) {
                int postIdIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                schedule_id = cursor.getInt(postIdIndex);
            }
            else {
                Log.d("SCHEDULE_NOT_FOUND", "График работы не найден в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("SCHEDULE_NOT_FOUND", "График работы не найден в настройках.");
        }

        return schedule_id;
    }

    public int getScheduleId(String scheduleName, SQLiteDatabase db) {
        int schedule_id = -1;

        if (scheduleName != null) {
            String[] columnsSchedules = {KEY_ID};
            String[] selectionSchedulesArgs = {scheduleName};
            Cursor cursor = db.query(TABLE_SCHEDULES, columnsSchedules, KEY_NAME + " = ?", selectionSchedulesArgs, null, null, null);

            if (cursor.moveToNext()) {
                int scheduleIdIndex = cursor.getColumnIndex(KEY_ID);
                schedule_id = cursor.getInt(scheduleIdIndex);
            }
            else {
                Log.d("SCHEDULE_NOT_FOUND", "График работы не найден в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("SCHEDULE_NOT_FOUND", "График работы не указан (null).");
        }

        return schedule_id;
    }

    public int getPostId(SharedPreferences settings, SQLiteDatabase db) {
        int post_id = -1;
        String post = settings.getString("post", null);

        if (post != null) {
            String[] columnsPosts = {KEY_ID};
            String[] selectionPostsArgs = {post};
            Cursor cursor = db.query(TABLE_POSTS, columnsPosts, KEY_NAME + " = ?", selectionPostsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int postIdIndex = cursor.getColumnIndex(KEY_ID);
                post_id = cursor.getInt(postIdIndex);
            }
            else {
                Log.d("POST_NOT_FOUND", "Должность не найдена в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("POST_NOT_FOUND", "Должность не найдена в настройках.");
        }

        return post_id;
    }

    public int getPostId(String postName, SQLiteDatabase db) {
        int post_id = -1;

        if (postName != null) {
            String[] columnsPosts = {KEY_ID};
            String[] selectionPostsArgs = {postName};
            Cursor cursor = db.query(TABLE_POSTS, columnsPosts, KEY_NAME + " = ?", selectionPostsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int postIdIndex = cursor.getColumnIndex(KEY_ID);
                post_id = cursor.getInt(postIdIndex);
            }
            else {
                Log.d("POST_NOT_FOUND", "Должность не найдена в БД.");
            }

            cursor.close();
        }
        else {
            Log.d("POST_NOT_FOUND", "Должность не указана (null).");
        }

        return post_id;
    }

    public String getUserName(SharedPreferences settings) {
        return settings.getString("name", null);
    }

    public String getUserSchedule(SharedPreferences settings) {
        return settings.getString("schedule", null);
    }

    public String getUserPost(SharedPreferences settings) {
        return settings.getString("post", null);
    }

    public String getUserSector(SharedPreferences settings) {
        return settings.getString("sector", null);
    }

    public List<String> getWorkDays() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> workDays = new ArrayList<>();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Cursor cursor = db.query(TABLE_WORKDAYS, new String[] {KEY_YEAR, KEY_MONTH, KEY_DAY}, KEY_USER_UID + " = ?", new String[] {UID}, null, null, KEY_YEAR + " DESC, " + KEY_MONTH + " DESC, " + KEY_DAY + " DESC");
        if (cursor.moveToNext()) {
            do {
                int yearColumnIndex = cursor.getColumnIndex(KEY_YEAR);
                int monthColumnIndex = cursor.getColumnIndex(KEY_MONTH);
                int dayColumnIndex = cursor.getColumnIndex(KEY_DAY);
                int year = cursor.getInt(yearColumnIndex);
                int month = cursor.getInt(monthColumnIndex);
                int day = cursor.getInt(dayColumnIndex);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month-1);
                cal.set(Calendar.DAY_OF_MONTH, day);
                workDays.add(new SimpleDateFormat("yyyy/MM/dd", new Locale("RU", "ru")).format(new Date(cal.getTimeInMillis())));
            } while(cursor.moveToNext());
        }
        else workDays.add("Нет рабочих смен");
        cursor.close();
        db.close();

        return workDays;
    }

    public Pics getNeedPics(SQLiteDatabase db, SharedPreferences settings) {
        String post = getUserPost(settings);
        int sector_id = getSectorId(settings, db);
        int schedule_id = getScheduleId(settings, db);
        int needPicsSelection, needPicsAllocation;

        if (Objects.equals(post, "Отборщик мезонина") || Objects.equals(post, "Размещенец мезонина")) {
            int post_selector_mez = getPostId("Отборщик мезонина", db);
            int post_allocator_mez = getPostId("Размещенец мезонина", db);

            needPicsSelection = getMonthNorm(db, post_selector_mez, sector_id, schedule_id);
            needPicsAllocation = getMonthNorm(db, post_allocator_mez, sector_id, schedule_id);
        }
        else if (Objects.equals(post, "Отборщик основы") || Objects.equals(post, "Размещенец основы")) {
            int post_selector_os = getPostId("Отборщик основы", db);
            int post_allocator_os = getPostId("Размещенец основы", db);

            needPicsSelection = getMonthNorm(db, post_selector_os, sector_id, schedule_id);
            needPicsAllocation = getMonthNorm(db, post_allocator_os, sector_id, schedule_id);
        }
        else {
            needPicsSelection = 5000;
            needPicsAllocation = 50000;
        }

        return new Pics(needPicsAllocation, needPicsSelection);
    }

    public void addWorkDay(SQLiteDatabase db, WorkDay workDay) {
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
        contentValues.put(DbHelper.KEY_USER_UID, workDay.getUserUID());

        String[] columnsWorkDays = {DbHelper.KEY_ID};
        String[] selectionSectorsArgs = {String.valueOf(workDay.getYear()), String.valueOf(workDay.getMonth()), String.valueOf(workDay.getDay()), workDay.getUserUID()};
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columnsWorkDays, DbHelper.KEY_YEAR + " = ? AND " + DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_DAY + " = ? AND " + DbHelper.KEY_USER_UID + " = ?", selectionSectorsArgs, null, null, null);

        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
            int id = cursor.getInt(idIndex);
            db.update(DbHelper.TABLE_WORKDAYS, contentValues, DbHelper.KEY_ID + " = ?", new String[] {String.valueOf(id)});
        }
        else {
            db.insert(DbHelper.TABLE_WORKDAYS, null, contentValues);
        }

        cursor.close();
    }

    public Tax getTax(SQLiteDatabase db, String sector_id, String schedule_id) {
        Tax tax = null;
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

            tax = new Tax();
            tax.setTax_allocation_os(cursor.getDouble(allocationOsTaxIndex));
            tax.setTax_allocation_mez(cursor.getDouble(allocationMezTaxIndex));
            tax.setTax_selection_os(cursor.getDouble(selectionOsTaxIndex));
            tax.setTax_selection_mez(cursor.getDouble(selectionMezTaxIndex));

            tax.setBonus_selection_mez_80(cursor.getDouble(bonusSelectionMez80Index));
            tax.setBonus_allocation_mez_80(cursor.getDouble(bonusAllocationMez80Index));

            tax.setBonus_selection_os_80(cursor.getDouble(bonusSelectionOs80Index));
            tax.setBonus_allocation_os_80(cursor.getDouble(bonusAllocationOs80Index));
        }
        cursor.close();

        return tax;
    }

    public Bundle getWorkDayBundle(SQLiteDatabase db, String UID, int day, int month, int year) {
        String[] columnsWorkDays = {DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ, DbHelper.KEY_IS_EXTRA_PAY, DbHelper.KEY_PAY};
        String[] selectionSectorsArgs = {String.valueOf(year), String.valueOf(month), String.valueOf(day), UID};
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columnsWorkDays, DbHelper.KEY_YEAR + " = ? AND " + DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_DAY + " = ? AND " + DbHelper.KEY_USER_UID + " = ?", selectionSectorsArgs, null, null, null);
        Bundle bundle = new Bundle();
        if (cursor.moveToNext()) {
            int selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
            int allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
            int selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
            int allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);
            int isExtraPayIndex = cursor.getColumnIndex(DbHelper.KEY_IS_EXTRA_PAY);
            int payIndex = cursor.getColumnIndex(DbHelper.KEY_PAY);

            int selectionOs = cursor.getInt(selectionOsIndex);
            int allocationOs = cursor.getInt(allocationOsIndex);
            int selectionMez = cursor.getInt(selectionMezIndex);
            int allocationMez = cursor.getInt(allocationMezIndex);
            int isExtraPay = cursor.getInt(isExtraPayIndex);
            double pay = cursor.getDouble(payIndex);

            bundle.putBoolean("workDayExist", true);
            bundle.putInt("year", year);
            bundle.putInt("month", month);
            bundle.putInt("day", day);
            bundle.putInt("selectionOs", selectionOs);
            bundle.putInt("allocationOs", allocationOs);
            bundle.putInt("selectionMez", selectionMez);
            bundle.putInt("allocationMez", allocationMez);
            bundle.putInt("isExtraDay", isExtraPay);
            bundle.putDouble("pay", pay);
        }
        else {
            bundle.putBoolean("workDayExist", false);
            bundle.putInt("year", year);
            bundle.putInt("month", month);
            bundle.putInt("day", day);
            bundle.putInt("selectionOs", 0);
            bundle.putInt("allocationOs", 0);
            bundle.putInt("selectionMez", 0);
            bundle.putInt("allocationMez", 0);
            bundle.putInt("isExtraDay", 0);
            bundle.putDouble("pay", 0);
        }
        cursor.close();

        return bundle;
    }

    public boolean workDayExist(SQLiteDatabase db, String UID, int day, int month, int year) {
        boolean exist = false;
        String[] columnsWorkDays = {KEY_ID};
        String[] selectionSectorsArgs = {String.valueOf(year), String.valueOf(month), String.valueOf(day), UID};
        Cursor cursor = db.query(TABLE_WORKDAYS, columnsWorkDays,
                KEY_YEAR + " = ? AND "
                + KEY_MONTH + " = ? AND "
                + KEY_DAY + " = ? AND "
                + KEY_USER_UID + " = ?", selectionSectorsArgs, null, null, null);

        exist = cursor.moveToNext();

        return exist;
    }

    public Pics getCurrentPics(SQLiteDatabase db) {
        int picsSelection = 0, picsAllocation = 0;
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String[] columns = { DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ };
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columns, DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_USER_UID + " = ?", new String[] {String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1), UID}, null, null, null);
        if (cursor.moveToNext()) {
            int selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
            int allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
            int selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
            int allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);

            do {
                picsSelection += cursor.getInt(selectionOsIndex) + cursor.getInt(selectionMezIndex);
                picsAllocation += cursor.getInt(allocationOsIndex) + cursor.getInt(allocationMezIndex);
            } while(cursor.moveToNext());
        }
        cursor.close();

        return new Pics(picsAllocation, picsSelection);
    }

    public double getAverageMonthSalary(SQLiteDatabase db) {
        int workMonths = 0;
        double totalSalary = 0, averageSalary = 0;

        Cursor cursor;
        for (int i = 1; i <= 12; i++) {
            cursor = db.rawQuery("SELECT SUM(" + KEY_PAY + ") FROM " + TABLE_WORKDAYS + " WHERE " + KEY_MONTH + " = ?;", new String[] {String.valueOf(i)});
            if (cursor.moveToNext()) {
                double monthSalary = cursor.getDouble(0);
                if (monthSalary != 0) {
                    workMonths += 1;
                    totalSalary += monthSalary;
                }
            }
            cursor.close();
        }

        if (totalSalary == 0)
            averageSalary = 0;
        else
            averageSalary = totalSalary/workMonths;

        return averageSalary;
    }

    public void deleteWorkDay(SQLiteDatabase db, int year, int month, int day) {
        String UID = FirebaseAuth.getInstance().getUid();
        db.delete(TABLE_WORKDAYS,
                KEY_YEAR + " = ? AND "
                        + KEY_MONTH + " = ? AND "
                        + KEY_DAY + " = ? AND "
                        + KEY_USER_UID + " = ?",
                new String[] {String.valueOf(year), String.valueOf(month), String.valueOf(day), UID});
    }

    public Spinner getFilledPostsSpinner(Spinner spinnerPosts, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> posts = new ArrayList<>();

        Cursor cursor = db.query(TABLE_POSTS, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                posts.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapterPosts = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, posts);
        adapterPosts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosts.setAdapter(adapterPosts);

        db.close();
        return spinnerPosts;
    }

    public List<String> getPosts() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> posts = new ArrayList<>();

        Cursor cursor = db.query(TABLE_POSTS, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                posts.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return posts;
    }

    public Spinner getFilledSchedulesSpinner(Spinner spinnerSchedules, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> schedules = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SCHEDULES, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                schedules.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapterSchedules = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, schedules);
        adapterSchedules.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchedules.setAdapter(adapterSchedules);

        db.close();
        return spinnerSchedules;
    }

    public List<String> getSchedules() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> schedules = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SCHEDULES, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                schedules.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return schedules;
    }

    public Spinner getFilledSectorsSpinner(Spinner spinnerSectors, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> sectors = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SECTORS, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                sectors.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapterSchedules = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, sectors);
        adapterSchedules.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSectors.setAdapter(adapterSchedules);

        db.close();
        return spinnerSectors;
    }

    public List<String> getSectors() {
        SQLiteDatabase db = getWritableDatabase();
        List<String> sectors = new ArrayList<>();

        Cursor cursor = db.query(TABLE_SECTORS, new String[] {KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(KEY_NAME);
                String value = cursor.getString(columnIndex);
                sectors.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return sectors;
    }

    public String getSectorsString(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SECTORS, null, null, null, null, null, null);
        StringBuilder text = new StringBuilder();

        while (cursor.moveToNext()) {
            do {
                int idIndex = cursor.getColumnIndex(KEY_ID);
                int nameIndex = cursor.getColumnIndex(KEY_NAME);
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);

                text.append(id).append(" - ").append(name).append("\n");

            } while (cursor.moveToNext());
        }
        cursor.close();

        return text.toString();
    }

    public int getMonthNorm(SQLiteDatabase db, int post_id, int sector_id, int schedule_id) {
        int monthNorm = 0;
        String[] selectionPostsArgs = {String.valueOf(post_id), String.valueOf(sector_id), String.valueOf(schedule_id)};
        Cursor cursor = db.query(TABLE_PRODUCTIVITY, new String[] {KEY_MONTH_NORM},
                KEY_POST_ID + " = ? AND "
                        + KEY_SECTOR_ID + " = ? AND "
                        + KEY_SCHEDULE_ID + " = ?",
                selectionPostsArgs, null, null, null);

        if (cursor.moveToNext()) {
            int monthNormIndex = cursor.getColumnIndex(KEY_MONTH_NORM);
            monthNorm = cursor.getInt(monthNormIndex);
        }
        cursor.close();

        return monthNorm;
    }

    public int getMonthNorm(SQLiteDatabase db, SharedPreferences settings) {
        int post_id = getPostId(settings, db);
        int sector_id = getSectorId(settings, db);
        int schedule_id = getScheduleId(settings, db);

        int monthNorm = 0;
        String[] selectionPostsArgs = {String.valueOf(post_id), String.valueOf(sector_id), String.valueOf(schedule_id)};
        Cursor cursor = db.query(TABLE_PRODUCTIVITY, new String[] {KEY_MONTH_NORM},
                KEY_POST_ID + " = ? AND "
                        + KEY_SECTOR_ID + " = ? AND "
                        + KEY_SCHEDULE_ID + " = ?",
                selectionPostsArgs, null, null, null);

        if (cursor.moveToNext()) {
            int monthNormIndex = cursor.getColumnIndex(KEY_MONTH_NORM);
            monthNorm = cursor.getInt(monthNormIndex);
        }
        else {
            Log.d("PRODUCTIVITY_NOT_FOUND", "Продуктивность не найдена в БД.");
        }
        cursor.close();

        return monthNorm;
    }

    public int getYearNorm(SQLiteDatabase db, SharedPreferences settings) {
        return getMonthNorm(db, settings) * 12;
    }

    public String getTaxesString(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_TAXES, null, null, null, null, null, null);
        StringBuilder text = new StringBuilder();

        while (cursor.moveToNext()) {
            do {
                int scheduleIdIndex = cursor.getColumnIndex(KEY_SCHEDULE_ID);
                int sectorIdIndex = cursor.getColumnIndex(KEY_SECTOR_ID);
                int selectionOsTaxIndex = cursor.getColumnIndex(KEY_SELECTION_OS_TAX);
                int allocationOsTaxIndex = cursor.getColumnIndex(KEY_ALLOCATION_OS_TAX);
                int selectionMezTaxIndex = cursor.getColumnIndex(KEY_SELECTION_MEZ_TAX);
                int allocationMezTaxIndex = cursor.getColumnIndex(KEY_ALLOCATION_MEZ_TAX);

                int scheduleId = cursor.getInt(scheduleIdIndex);
                int sectorId = cursor.getInt(sectorIdIndex);
                double taxSelectOs = cursor.getDouble(selectionOsTaxIndex);
                double taxAllocOs = cursor.getDouble(allocationOsTaxIndex);
                double taxSelectMez = cursor.getDouble(selectionMezTaxIndex);
                double taxAllocMez = cursor.getDouble(allocationMezTaxIndex);

                text.append(scheduleId).append(", ").append(sectorId).append(", ").append(taxSelectOs).append(", ").append(taxAllocOs).append(", ").append(taxSelectMez).append(", ").append(taxAllocMez).append("\n");

            } while (cursor.moveToNext());
        }
        cursor.close();

        return text.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKDAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTIVITY);

        onCreate(db);
    }

    public void dropDB(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKDAYS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTORS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTIVITY);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
