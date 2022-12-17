package com.example.vipiki.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.vipiki.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ViPikiDB";

    public static final String TABLE_WORKDAYS = "workdays";
    public static final String TABLE_POSTS = "posts";
    public static final String TABLE_SCHEDULES = "schedules";
    public static final String TABLE_SECTORS = "sectors";
    public static final String TABLE_TAXES = "taxes";

    public static final String KEY_ID = "_id";
    public static final String KEY_SECTOR_ID = "sector_id";
    public static final String KEY_SCHEDULE_ID = "schedule_id";
    public static final String KEY_SELECTION_OS_TAX = "selectionOsTax";
    public static final String KEY_SELECTION_MEZ_TAX = "selectionMezTax";
    public static final String KEY_ALLOCATION_OS_TAX = "allocationOsTax";
    public static final String KEY_ALLOCATION_MEZ_TAX = "allocationMezTax";
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
                + KEY_PAY + " INTEGER NOT NULL" + ")");

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

        initStartData(db);
    }

    private void initStartData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        initPosts(db, cv);
        initSchedules(db, cv);
        initSectors(db, cv);
        initTaxes(db);
    }

    private void initPosts(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "Размещенец основы");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Размещенец мезонина");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Отборщик основы");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(KEY_NAME, "Отборщик мезонина");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
    }

    private void initSchedules(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "3/3 день");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "3/3 ночь");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "5/2 день");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(KEY_NAME, "4/3 день (ГБР)");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
    }

    private void initSectors(SQLiteDatabase db, ContentValues cv) {
        cv.put(KEY_NAME, "А");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Б");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "В");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Г");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Д");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(KEY_NAME, "Е");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
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

        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    public int getSectorId(SharedPreferences settings, SQLiteDatabase db) {
        int sector_id = -1;
        String sector = settings.getString("sector", null);

        if (sector != null) {
            String[] columnsSectors = {DbHelper.KEY_ID};
            String[] selectionSectorsArgs = {sector};
            Cursor cursor = db.query(DbHelper.TABLE_SECTORS, columnsSectors, DbHelper.KEY_NAME + " = ?", selectionSectorsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int postIdIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                sector_id = cursor.getInt(postIdIndex);
            }

            cursor.close();
        }
        else {
            Log.d("SECTOR_NOT_FOUND", "Сектор не найден в настройках.");
        }

        return sector_id;
    }

    public int getScheduleId(SharedPreferences settings, SQLiteDatabase db) {
        int schedule_id = -1;
        String schedule = settings.getString("schedule", null);

        if (schedule != null) {
            String[] columnsSectors = {DbHelper.KEY_ID};
            String[] selectionSectorsArgs = {schedule};
            Cursor cursor = db.query(DbHelper.TABLE_SCHEDULES, columnsSectors, DbHelper.KEY_NAME + " = ?", selectionSectorsArgs, null, null, null);

            if (cursor.moveToNext()) {
                int postIdIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                schedule_id = cursor.getInt(postIdIndex);
            }

            cursor.close();
        }
        else {
            Log.d("SECTOR_NOT_FOUND", "Сектор не найден в настройках.");
        }

        return schedule_id;
    }

    public String getUserName(SharedPreferences settings) {
        return settings.getString("name", null);
    }

    public Spinner getFilledPostsSpinner(Spinner spinnerPosts, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> posts = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_POSTS, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
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

    public Spinner getFilledSchedulesSpinner(Spinner spinnerSchedules, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> schedules = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_SCHEDULES, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
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

    public Spinner getFilledSectorsSpinner(Spinner spinnerSectors, Context context) {
        SQLiteDatabase db = getWritableDatabase();
        List<String> sectors = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_SECTORS, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
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

    public Cursor getSectorsCursor(SQLiteDatabase db) {
        return db.query(TABLE_SECTORS, null, null, null, null, null, null);
    }

    public String getSectorsString(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SECTORS, null, null, null, null, null, null);
        StringBuilder text = new StringBuilder();

        while (cursor.moveToNext()) {
            do {
                int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);

                text.append(id).append(" - ").append(name).append("\n");

            } while (cursor.moveToNext());
        }
        cursor.close();

        return text.toString();
    }

    public Cursor getTaxesCursor(SQLiteDatabase db) {
        return db.query(TABLE_TAXES, null, null, null, null, null, null);
    }

    public String getTaxesString(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_TAXES, null, null, null, null, null, null);
        StringBuilder text = new StringBuilder();

        while (cursor.moveToNext()) {
            do {
                int scheduleIdIndex = cursor.getColumnIndex(DbHelper.KEY_SCHEDULE_ID);
                int sectorIdIndex = cursor.getColumnIndex(DbHelper.KEY_SECTOR_ID);
                int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
                int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
                int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
                int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

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
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_WORKDAYS);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_SECTORS);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_TAXES);

        onCreate(db);
    }
}
