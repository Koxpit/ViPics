package com.example.vipiki.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        initStartData(db);
    }

    private void initStartData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        initPosts(db, cv);
        initSchedules(db, cv);
        initSectors(db, cv);
        initTaxes(db);
        initProductivity(db);
    }

    private void initPosts(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "Размещенец основы");
        db.insert(TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Размещенец мезонина");
        db.insert(TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Отборщик основы");
        db.insert(TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Отборщик мезонина");
        db.insert(TABLE_POSTS, null, cv);
    }

    private void initSchedules(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "3/3 день");
        db.insert(TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "3/3 ночь");
        db.insert(TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "5/2 день");
        db.insert(TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "4/3 день (ГБР)");
        db.insert(TABLE_SCHEDULES, null, cv);
    }

    private void initSectors(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "А");
        db.insert(TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Б");
        db.insert(TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "В");
        db.insert(TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Г");
        db.insert(TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Д");
        db.insert(TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Е");
        db.insert(TABLE_SECTORS, null, cv);
    }

    private void initTaxes(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        initNightShift(db, cv);
        initDayShift(db, cv);
        initGBRShift(db, cv);
        initDay(db, cv);
    }

    private void initNightShift(SQLiteDatabase db, ContentValues cv) {
        initNightShiftA(db, cv);
        initNightShiftB(db, cv);
        initNightShiftV(db, cv);
        initNightShiftG(db, cv);
        initNightShiftD(db, cv);
        initNightShiftE(db, cv);
    }

    private void initNightShiftA(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 11.16);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.29);

        db.insert(TABLE_TAXES, null, cv);
    }

    private void initNightShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 11.16);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.29);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initNightShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 19.03);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.41);
        cv.put(KEY_SELECTION_MEZ_TAX, 9.52);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 1.25);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 1.27);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 2.54);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 3.17);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 3.81);
        cv.put(KEY_BONUS_SELECTION_OS_80, 2.54);
        cv.put(KEY_BONUS_SELECTION_OS_90, 5.08);
        cv.put(KEY_BONUS_SELECTION_OS_100, 6.34);
        cv.put(KEY_BONUS_SELECTION_OS_120, 7.01);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.17);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.33);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.42);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.50);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.45);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.91);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.14);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.26);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initNightShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 23.11);
        cv.put(KEY_ALLOCATION_OS_TAX, 19.03);
        cv.put(KEY_SELECTION_MEZ_TAX, 8.52);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 4.98);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 1.14);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 2.27);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.84);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 3.41);
        cv.put(KEY_BONUS_SELECTION_OS_80, 3.05);
        cv.put(KEY_BONUS_SELECTION_OS_90, 6.16);
        cv.put(KEY_BONUS_SELECTION_OS_100, 7.70);
        cv.put(KEY_BONUS_SELECTION_OS_120, 9.24);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.66);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 1.33);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 1.66);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 1.95);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 2.54);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 5.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 6.34);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 7.61);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initNightShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 11.16);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.29);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initNightShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 6);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SELECTION_OS_TAX, 11.16);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.29);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDay(SQLiteDatabase db, ContentValues cv) {
        initDayA(db, cv);
        initDayB(db, cv);
        initDayV(db, cv);
        initDayG(db, cv);
        initDayD(db, cv);
        initDayE(db, cv);
    }

    private void initDayA(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 9.18);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayB(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 9.18);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayV(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 15.02);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.06);
        cv.put(KEY_SELECTION_MEZ_TAX, 7.18);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 1.59);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.96);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.92);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.39);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.87);
        cv.put(KEY_BONUS_SELECTION_OS_80, 2.00);
        cv.put(KEY_BONUS_SELECTION_OS_90, 4.00);
        cv.put(KEY_BONUS_SELECTION_OS_100, 5.01);
        cv.put(KEY_BONUS_SELECTION_OS_120, 6.01);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.21);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.42);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.53);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.64);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.41);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.82);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.02);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.22);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayG(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 20.65);
        cv.put(KEY_ALLOCATION_OS_TAX, 8.47);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.88);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 2.71);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.92);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.84);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.29);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.75);
        cv.put(KEY_BONUS_SELECTION_OS_80, 2.75);
        cv.put(KEY_BONUS_SELECTION_OS_90, 5.51);
        cv.put(KEY_BONUS_SELECTION_OS_100, 6.88);
        cv.put(KEY_BONUS_SELECTION_OS_120, 8.26);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.36);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.72);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.90);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 1.08);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 1.13);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 2.26);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 2.82);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 3.29);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayD(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 9.18);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayE(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 6);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SELECTION_OS_TAX, 9.18);
        cv.put(KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShift(SQLiteDatabase db, ContentValues cv) {
        initGBRShiftA(db, cv);
        initGBRShiftB(db, cv);
        initGBRShiftV(db, cv);
        initGBRShiftG(db, cv);
        initGBRShiftD(db, cv);
        initGBRShiftE(db, cv);
    }

    private void initGBRShiftA(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 12.50);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.78);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.25);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 1.44);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.67);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.08);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.50);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.67);
        cv.put(KEY_BONUS_SELECTION_OS_90, 3.33);
        cv.put(KEY_BONUS_SELECTION_OS_100, 4.17);
        cv.put(KEY_BONUS_SELECTION_OS_120, 5.00);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.19);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.48);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.58);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.74);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.93);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.11);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 17.65);
        cv.put(KEY_ALLOCATION_OS_TAX, 7.32);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.00);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.80);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.60);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.00);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.40);
        cv.put(KEY_BONUS_SELECTION_OS_80, 2.35);
        cv.put(KEY_BONUS_SELECTION_OS_90, 4.71);
        cv.put(KEY_BONUS_SELECTION_OS_100, 5.88);
        cv.put(KEY_BONUS_SELECTION_OS_120, 7.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.28);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.55);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.69);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.83);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.98);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 1.95);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 2.44);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 2.93);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initGBRShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 6);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShift(SQLiteDatabase db, ContentValues cv) {
        initDayShiftA(db, cv);
        initDayShiftB(db, cv);
        initDayShiftV(db, cv);
        initDayShiftG(db, cv);
        initDayShiftD(db, cv);
        initDayShiftE(db, cv);
    }

    private void initDayShiftA(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 12.50);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.78);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.25);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 1.44);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.67);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.08);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.50);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.67);
        cv.put(KEY_BONUS_SELECTION_OS_90, 3.33);
        cv.put(KEY_BONUS_SELECTION_OS_100, 4.17);
        cv.put(KEY_BONUS_SELECTION_OS_120, 5.00);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.19);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.38);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.48);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.58);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.74);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.93);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.11);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 17.65);
        cv.put(KEY_ALLOCATION_OS_TAX, 7.32);
        cv.put(KEY_SELECTION_MEZ_TAX, 6.00);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 2.07);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.80);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.60);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 2.00);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.40);
        cv.put(KEY_BONUS_SELECTION_OS_80, 2.35);
        cv.put(KEY_BONUS_SELECTION_OS_90, 4.71);
        cv.put(KEY_BONUS_SELECTION_OS_100, 5.88);
        cv.put(KEY_BONUS_SELECTION_OS_120, 7.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.28);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.55);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.69);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.83);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.98);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 1.95);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 2.44);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 2.93);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initDayShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_SECTOR_ID, 6);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SELECTION_OS_TAX, 7.69);
        cv.put(KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(TABLE_TAXES, null, cv);
    }

    private void initProductivity(SQLiteDatabase db) {
        initNightProductivity(db);
        initDayProductivity(db);
        initDayShiftProductivity(db);
        initGBRProductivity(db);
    }

    private void initNightProductivity(SQLiteDatabase db) {
        initNightSelectionOsProductivity(db);
        initNightAllocationOsProductivity(db);
        initNightSelectionMezProductivity(db);
        initNightAllocationMezProductivity(db);
    }

    private void initNightSelectionOsProductivity(SQLiteDatabase db) {
        initNightSelectionOsProductivityA(db);
        initNightSelectionOsProductivityB(db);
        initNightSelectionOsProductivityV(db);
        initNightSelectionOsProductivityG(db);
        initNightSelectionOsProductivityD(db);
        initNightSelectionOsProductivityE(db);
    }

    private void initNightSelectionOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 29);
        cv.put(KEY_MONTH_NORM, 4640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 29);
        cv.put(KEY_MONTH_NORM, 4640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initNightSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initNightSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 29);
        cv.put(KEY_MONTH_NORM, 4640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionOsProductivityE(SQLiteDatabase db) {

    }

    private void initNightAllocationOsProductivity(SQLiteDatabase db) {
        initNightAllocationOsProductivityA(db);
        initNightAllocationOsProductivityB(db);
        initNightAllocationOsProductivityV(db);
        initNightAllocationOsProductivityG(db);
        initNightAllocationOsProductivityD(db);
        initNightAllocationOsProductivityE(db);
    }

    private void initNightAllocationOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 100);
        cv.put(KEY_MONTH_NORM, 16000);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 100);
        cv.put(KEY_MONTH_NORM, 16000);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initNightAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initNightAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 100);
        cv.put(KEY_MONTH_NORM, 16000);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationOsProductivityE(SQLiteDatabase db) {

    }

    private void initNightSelectionMezProductivity(SQLiteDatabase db) {
        initNightSelectionMezProductivityA(db);
        initNightSelectionMezProductivityB(db);
        initNightSelectionMezProductivityV(db);
        initNightSelectionMezProductivityG(db);
        initNightSelectionMezProductivityD(db);
        initNightSelectionMezProductivityE(db);
    }

    private void initNightSelectionMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8320);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8320);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initNightSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initNightSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8320);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionMezProductivityE(SQLiteDatabase db) {

    }

    private void initNightAllocationMezProductivity(SQLiteDatabase db) {
        initNightAllocationMezProductivityA(db);
        initNightAllocationMezProductivityB(db);
        initNightAllocationMezProductivityV(db);
        initNightAllocationMezProductivityG(db);
        initNightAllocationMezProductivityD(db);
        initNightAllocationMezProductivityE(db);
    }

    private void initNightAllocationMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 55200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 55200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initNightAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initNightAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 2);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 55200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationMezProductivityE(SQLiteDatabase db) {

    }



    private void initDayProductivity(SQLiteDatabase db) {
        initDaySelectionOsProductivity(db);
        initDayAllocationOsProductivity(db);
        initDaySelectionMezProductivity(db);
        initDayAllocationMezProductivity(db);
    }

    private void initDaySelectionOsProductivity(SQLiteDatabase db) {
        initDaySelectionOsProductivityA(db);
        initDaySelectionOsProductivityB(db);
        initDaySelectionOsProductivityV(db);
        initDaySelectionOsProductivityG(db);
        initDaySelectionOsProductivityD(db);
        initDaySelectionOsProductivityE(db);
    }

    private void initDaySelectionOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 36);
        cv.put(KEY_MONTH_NORM, 6048);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 36);
        cv.put(KEY_MONTH_NORM, 6048);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initDaySelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initDaySelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 36);
        cv.put(KEY_MONTH_NORM, 6048);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionOsProductivityE(SQLiteDatabase db) {

    }

    private void initDayAllocationOsProductivity(SQLiteDatabase db) {
        initDayAllocationOsProductivityA(db);
        initDayAllocationOsProductivityB(db);
        initDayAllocationOsProductivityV(db);
        initDayAllocationOsProductivityG(db);
        initDayAllocationOsProductivityD(db);
        initDayAllocationOsProductivityE(db);
    }

    private void initDayAllocationOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 18312);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 18312);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 18312);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationOsProductivityE(SQLiteDatabase db) {

    }

    private void initDaySelectionMezProductivity(SQLiteDatabase db) {
        initDaySelectionMezProductivityA(db);
        initDaySelectionMezProductivityB(db);
        initDaySelectionMezProductivityV(db);
        initDaySelectionMezProductivityG(db);
        initDaySelectionMezProductivityD(db);
        initDaySelectionMezProductivityE(db);
    }

    private void initDaySelectionMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8736);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8736);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initDaySelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initDaySelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 52);
        cv.put(KEY_MONTH_NORM, 8736);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionMezProductivityE(SQLiteDatabase db) {

    }

    private void initDayAllocationMezProductivity(SQLiteDatabase db) {
        initDayAllocationMezProductivityA(db);
        initDayAllocationMezProductivityB(db);
        initDayAllocationMezProductivityV(db);
        initDayAllocationMezProductivityG(db);
        initDayAllocationMezProductivityD(db);
        initDayAllocationMezProductivityE(db);
    }

    private void initDayAllocationMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 57960);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 57960);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 3);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(KEY_HOUR_NORM, 345);
        cv.put(KEY_MONTH_NORM, 57960);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationMezProductivityE(SQLiteDatabase db) {

    }

    private void initDayShiftProductivity(SQLiteDatabase db) {
        initDayShiftSelectionOsProductivity(db);
        initDayShiftAllocationOsProductivity(db);
        initDayShiftSelectionMezProductivity(db);
        initDayShiftAllocationMezProductivity(db);
    }

    private void initDayShiftSelectionOsProductivity(SQLiteDatabase db) {
        initDayShiftSelectionOsProductivityA(db);
        initDayShiftSelectionOsProductivityB(db);
        initDayShiftSelectionOsProductivityV(db);
        initDayShiftSelectionOsProductivityG(db);
        initDayShiftSelectionOsProductivityD(db);
        initDayShiftSelectionOsProductivityE(db);
    }

    private void initDayShiftSelectionOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6240);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6240);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6240);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionOsProductivityE(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationOsProductivity(SQLiteDatabase db) {
        initDayShiftAllocationOsProductivityA(db);
        initDayShiftAllocationOsProductivityB(db);
        initDayShiftAllocationOsProductivityV(db);
        initDayShiftAllocationOsProductivityG(db);
        initDayShiftAllocationOsProductivityD(db);
        initDayShiftAllocationOsProductivityE(db);
    }

    private void initDayShiftAllocationOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17440);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17440);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 1);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17440);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationOsProductivityE(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionMezProductivity(SQLiteDatabase db) {
        initDayShiftSelectionMezProductivityA(db);
        initDayShiftSelectionMezProductivityB(db);
        initDayShiftSelectionMezProductivityV(db);
        initDayShiftSelectionMezProductivityG(db);
        initDayShiftSelectionMezProductivityD(db);
        initDayShiftSelectionMezProductivityE(db);
    }

    private void initDayShiftSelectionMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8640);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionMezProductivityE(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationMezProductivity(SQLiteDatabase db) {
        initDayShiftAllocationMezProductivityA(db);
        initDayShiftAllocationMezProductivityB(db);
        initDayShiftAllocationMezProductivityV(db);
        initDayShiftAllocationMezProductivityG(db);
        initDayShiftAllocationMezProductivityD(db);
        initDayShiftAllocationMezProductivityE(db);
    }

    private void initDayShiftAllocationMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 1);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59200);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationMezProductivityE(SQLiteDatabase db) {

    }



    private void initGBRProductivity(SQLiteDatabase db) {
        initGBRSelectionOsProductivity(db);
        initGBRAllocationOsProductivity(db);
        initGBRSelectionMezProductivity(db);
        initGBRAllocationMezProductivity(db);
    }

    private void initGBRSelectionOsProductivity(SQLiteDatabase db) {
        initGBRSelectionOsProductivityA(db);
        initGBRSelectionOsProductivityB(db);
        initGBRSelectionOsProductivityV(db);
        initGBRSelectionOsProductivityG(db);
        initGBRSelectionOsProductivityD(db);
        initGBRSelectionOsProductivityE(db);
    }

    private void initGBRSelectionOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6318);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6318);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initGBRSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initGBRSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 3);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 39);
        cv.put(KEY_MONTH_NORM, 6318);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionOsProductivityE(SQLiteDatabase db) {

    }

    private void initGBRAllocationOsProductivity(SQLiteDatabase db) {
        initGBRAllocationOsProductivityA(db);
        initGBRAllocationOsProductivityB(db);
        initGBRAllocationOsProductivityV(db);
        initGBRAllocationOsProductivityG(db);
        initGBRAllocationOsProductivityD(db);
        initGBRAllocationOsProductivityE(db);
    }

    private void initGBRAllocationOsProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17658);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17658);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initGBRAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initGBRAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 109);
        cv.put(KEY_MONTH_NORM, 17658);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationOsProductivityE(SQLiteDatabase db) {

    }


    private void initGBRSelectionMezProductivity(SQLiteDatabase db) {
        initGBRSelectionMezProductivityA(db);
        initGBRSelectionMezProductivityB(db);
        initGBRSelectionMezProductivityV(db);
        initGBRSelectionMezProductivityG(db);
        initGBRSelectionMezProductivityD(db);
        initGBRSelectionMezProductivityE(db);
    }

    private void initGBRSelectionMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8748);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8748);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initGBRSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initGBRSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 4);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 54);
        cv.put(KEY_MONTH_NORM, 8748);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionMezProductivityE(SQLiteDatabase db) {

    }

    private void initGBRAllocationMezProductivity(SQLiteDatabase db) {
        initGBRAllocationMezProductivityA(db);
        initGBRAllocationMezProductivityB(db);
        initGBRAllocationMezProductivityV(db);
        initGBRAllocationMezProductivityG(db);
        initGBRAllocationMezProductivityD(db);
        initGBRAllocationMezProductivityE(db);
    }

    private void initGBRAllocationMezProductivityA(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 1);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59940);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 2);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59940);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initGBRAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initGBRAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_POST_ID, 2);
        cv.put(KEY_SCHEDULE_ID, 4);
        cv.put(KEY_SECTOR_ID, 5);
        cv.put(KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(KEY_HOUR_NORM, 370);
        cv.put(KEY_MONTH_NORM, 59940);
        db.insert(TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityE(SQLiteDatabase db) {

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

    public Spinner getFilledWorkDaysSpinner(Spinner spinnerWorkDays, FragmentActivity context) {
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

        ArrayAdapter<String> adapterWorkDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, workDays);
        adapterWorkDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkDays.setAdapter(adapterWorkDays);

        db.close();
        return spinnerWorkDays;
    }

    public void refreshWorkDaysSpinner(Spinner workDaySpinner, FragmentActivity context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> workDays = new ArrayList<>();

        Cursor cursor = db.query(TABLE_WORKDAYS, new String[] {KEY_YEAR, KEY_MONTH, KEY_DAY}, null, null, null, null, null);
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
                workDays.add(new SimpleDateFormat("dd/MM/yyyy", new Locale("RU", "ru")).format(new Date(cal.getTimeInMillis())));
            } while(cursor.moveToNext());
        }
        else workDays.add("Нет рабочих смен");
        cursor.close();

        ArrayAdapter<String> adapterWorkDays = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, workDays);
        adapterWorkDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workDaySpinner.setAdapter(adapterWorkDays);
        workDaySpinner.setSelection(0, false);

        db.close();
    }

    public double getAverageMonthSalary(SQLiteDatabase db) {
        int workMonths = 0;
        double totalSalary = 0, averageSalary = 0;

        Cursor cursor;
        for (int i = 1; i <= 12; i++) {
            cursor = db.rawQuery("SELECT SUM(" + KEY_PAY + ") FROM " + TABLE_WORKDAYS + " WHERE " + KEY_MONTH + " = ?;", new String[] {String.valueOf(i)});
            if (cursor.moveToFirst()) {
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

    public void deleteWOrkDay(SQLiteDatabase db, int year, int month, int day) {
        db.delete(TABLE_WORKDAYS, KEY_YEAR + " = ? AND " + KEY_MONTH + " = ? AND " + KEY_DAY + " = ?",
                new String[] {String.valueOf(year), String.valueOf(month), String.valueOf(day)});
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

    public Cursor getSectorsCursor(SQLiteDatabase db) {
        return db.query(TABLE_SECTORS, null, null, null, null, null, null);
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

    public Cursor getProductivity(SQLiteDatabase db, int post_id, int sector_id, int schedule_id) {
        String[] selectionPostsArgs = {String.valueOf(post_id), String.valueOf(sector_id), String.valueOf(schedule_id)};
        return db.query(TABLE_PRODUCTIVITY, null,
                KEY_POST_ID + " = ? AND "
                        + KEY_SECTOR_ID + " = ? AND "
                        + KEY_SCHEDULE_ID + " = ?",
                selectionPostsArgs, null, null, null);
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

    public Cursor getTaxesCursor(SQLiteDatabase db) {
        return db.query(TABLE_TAXES, null, null, null, null, null, null);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKDAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTIVITY);
    }
}
