package com.example.vipiki.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class FirstData {
    private final SQLiteDatabase db;

    public FirstData(SQLiteDatabase db) {
        this.db = db;
    }

    public void initStartData() {
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            initPosts(db, cv);
            initSchedules(db, cv);
            initSectors(db, cv);
            initTaxes(db);
            initProductivity(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void initPosts(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_NAME, "Размещенец основы");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Размещенец мезонина");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Отборщик основы");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Отборщик мезонина");
        db.insert(DbHelper.TABLE_POSTS, null, cv);
    }

    private void initSchedules(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_NAME, "3/3 день");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(DbHelper.KEY_NAME, "3/3 ночь");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(DbHelper.KEY_NAME, "5/2 день");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
        cv.put(DbHelper.KEY_NAME, "4/3 день (ГБР)");
        db.insert(DbHelper.TABLE_SCHEDULES, null, cv);
    }

    private void initSectors(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_NAME, "А");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Б");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(DbHelper.KEY_NAME, "В");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Г");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Д");
        db.insert(DbHelper.TABLE_SECTORS, null, cv);
        cv.put(DbHelper.KEY_NAME, "Е");
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
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 11.16);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.29);

        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initNightShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 11.16);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.29);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initNightShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 19.03);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.41);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 9.52);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 1.25);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 1.27);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 3.17);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 3.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 5.08);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 6.34);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 7.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.17);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.33);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.42);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.50);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.45);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.91);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.14);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.26);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initNightShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 23.11);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 19.03);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 8.52);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 4.98);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 1.14);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 2.27);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.84);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 3.41);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 3.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 6.16);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 7.70);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 9.24);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.66);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 1.33);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 1.66);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 1.95);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 2.54);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 5.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 6.34);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 7.61);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initNightShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 11.16);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.29);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initNightShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 6);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 11.16);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.24);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.22);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.94);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.66);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.49);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.98);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.72);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 4.46);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.12);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.25);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.31);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.43);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.86);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.29);
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
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 9.18);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayB(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 9.18);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayV(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 15.02);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.06);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 7.18);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 1.59);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.96);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.92);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.39);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.87);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 2.00);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 4.00);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 5.01);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 6.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.21);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.42);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.53);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.64);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.41);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.82);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.02);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.22);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayG(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 20.65);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 8.47);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.88);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 2.71);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.92);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.84);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.29);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.75);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 2.75);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 5.51);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 6.88);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 8.26);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.36);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.72);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.90);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 1.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 1.13);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 2.26);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 2.82);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 3.29);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayD(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 9.18);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.21);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayE(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 6);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 9.18);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 3.03);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.35);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.96);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.69);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.12);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.54);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.45);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 3.06);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.67);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.13);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.20);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.40);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.81);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 1.01);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.21);
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
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initGBRShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initGBRShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 12.50);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.78);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.25);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 1.44);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.67);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.08);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.50);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.67);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 3.33);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 4.17);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 5.00);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.19);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.48);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.58);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.74);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.93);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.11);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initGBRShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 17.65);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 7.32);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.00);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.80);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.60);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.00);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.40);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 2.35);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 4.71);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 5.88);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 7.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.28);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.55);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.69);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.83);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.98);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 1.95);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 2.44);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 2.93);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initGBRShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initGBRShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 6);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.08);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
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
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayShiftB(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayShiftV(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 12.50);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.78);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.25);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 1.44);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.83);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.67);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.08);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.50);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.67);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 3.33);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 4.17);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 5.00);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.19);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.38);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.48);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.58);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.74);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.93);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.11);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayShiftG(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 17.65);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 7.32);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 6.00);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 2.07);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.80);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.60);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 2.00);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.40);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 2.35);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 4.71);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 5.88);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 7.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.28);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.55);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.69);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.83);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.98);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 1.95);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 2.44);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 2.93);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayShiftD(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
    }

    private void initDayShiftE(SQLiteDatabase db, ContentValues cv) {
        cv.put(DbHelper.KEY_SECTOR_ID, 6);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SELECTION_OS_TAX, 7.69);
        cv.put(DbHelper.KEY_ALLOCATION_OS_TAX, 2.75);
        cv.put(DbHelper.KEY_SELECTION_MEZ_TAX, 5.56);
        cv.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, 0.81);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, 0.74);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, 1.48);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, 1.85);
        cv.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, 2.22);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_80, 1.03);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_90, 2.05);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_100, 2.56);
        cv.put(DbHelper.KEY_BONUS_SELECTION_OS_120, 3.06);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, 0.11);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, 0.22);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, 0.27);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, 0.32);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, 0.37);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, 0.73);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, 0.92);
        cv.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, 1.10);
        db.insert(DbHelper.TABLE_TAXES, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 29);
        cv.put(DbHelper.KEY_MONTH_NORM, 4640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 29);
        cv.put(DbHelper.KEY_MONTH_NORM, 4640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initNightSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initNightSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 29);
        cv.put(DbHelper.KEY_MONTH_NORM, 4640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 100);
        cv.put(DbHelper.KEY_MONTH_NORM, 16000);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 100);
        cv.put(DbHelper.KEY_MONTH_NORM, 16000);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initNightAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initNightAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 100);
        cv.put(DbHelper.KEY_MONTH_NORM, 16000);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8320);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8320);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initNightSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initNightSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8320);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 55200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 55200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initNightAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initNightAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initNightAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 2);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 55200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 36);
        cv.put(DbHelper.KEY_MONTH_NORM, 6048);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 36);
        cv.put(DbHelper.KEY_MONTH_NORM, 6048);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initDaySelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initDaySelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 36);
        cv.put(DbHelper.KEY_MONTH_NORM, 6048);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 18312);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 18312);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 18312);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8736);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8736);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDaySelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initDaySelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initDaySelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 52);
        cv.put(DbHelper.KEY_MONTH_NORM, 8736);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 57960);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 57960);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 3);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 21);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 8);
        cv.put(DbHelper.KEY_HOUR_NORM, 345);
        cv.put(DbHelper.KEY_MONTH_NORM, 57960);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6240);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6240);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6240);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17440);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17440);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 1);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17440);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8640);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initDayShiftAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initDayShiftAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 1);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 16);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 10);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59200);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6318);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6318);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionOsProductivityV(SQLiteDatabase db) {

    }

    private void initGBRSelectionOsProductivityG(SQLiteDatabase db) {

    }

    private void initGBRSelectionOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 3);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 39);
        cv.put(DbHelper.KEY_MONTH_NORM, 6318);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17658);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationOsProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17658);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationOsProductivityV(SQLiteDatabase db) {

    }

    private void initGBRAllocationOsProductivityG(SQLiteDatabase db) {

    }

    private void initGBRAllocationOsProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 109);
        cv.put(DbHelper.KEY_MONTH_NORM, 17658);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8748);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8748);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRSelectionMezProductivityV(SQLiteDatabase db) {

    }

    private void initGBRSelectionMezProductivityG(SQLiteDatabase db) {

    }

    private void initGBRSelectionMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 4);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 54);
        cv.put(DbHelper.KEY_MONTH_NORM, 8748);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
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
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 1);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59940);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityB(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 2);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59940);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityV(SQLiteDatabase db) {

    }

    private void initGBRAllocationMezProductivityG(SQLiteDatabase db) {

    }

    private void initGBRAllocationMezProductivityD(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_POST_ID, 2);
        cv.put(DbHelper.KEY_SCHEDULE_ID, 4);
        cv.put(DbHelper.KEY_SECTOR_ID, 5);
        cv.put(DbHelper.KEY_WORK_DAYS_PER_MONTH, 18);
        cv.put(DbHelper.KEY_WORK_HOURS_PER_DAY, 9);
        cv.put(DbHelper.KEY_HOUR_NORM, 370);
        cv.put(DbHelper.KEY_MONTH_NORM, 59940);
        db.insert(DbHelper.TABLE_PRODUCTIVITY, null, cv);
    }

    private void initGBRAllocationMezProductivityE(SQLiteDatabase db) {

    }
}
