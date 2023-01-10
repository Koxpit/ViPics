package com.example.vipiki.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.models.Bonus;
import com.example.vipiki.models.Pics;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.WorkDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ViPikiDB";
    public static final String APP_PREFERENCES = "app_settings";

    public static final String TABLE_USERS_AVATARS = "avatars";
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
    public static final String KEY_AVATAR = "userImage";
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
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS_AVATARS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_USER_UID + " TEXT UNIQUE NOT NULL, "
                + KEY_AVATAR + " BLOB )");

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
    }


    private int getSectorId(SharedPreferences settings, SQLiteDatabase db) {
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

            cursor.close();
        }

        return sector_id;
    }

    private int getScheduleId(SharedPreferences settings, SQLiteDatabase db) {
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

            cursor.close();
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

            cursor.close();
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

            cursor.close();
        }

        return post_id;
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
        int needPicsSelection, needPicsAllocation;

        db.beginTransaction();
        try {
            String post = getUserPost(settings);
            int sector_id = getSectorId(settings, db);
            int schedule_id = getScheduleId(settings, db);

            if (Objects.equals(post, "Отборщик мезонина") || Objects.equals(post, "Размещенец мезонина")) {
                int post_selector_mez = getPostId("Отборщик мезонина", db);
                int post_allocator_mez = getPostId("Размещенец мезонина", db);

                needPicsSelection = getMonthNorm(db, post_selector_mez, sector_id, schedule_id);
                needPicsAllocation = getMonthNorm(db, post_allocator_mez, sector_id, schedule_id);
            } else if (Objects.equals(post, "Отборщик основы") || Objects.equals(post, "Размещенец основы")) {
                int post_selector_os = getPostId("Отборщик основы", db);
                int post_allocator_os = getPostId("Размещенец основы", db);

                needPicsSelection = getMonthNorm(db, post_selector_os, sector_id, schedule_id);
                needPicsAllocation = getMonthNorm(db, post_allocator_os, sector_id, schedule_id);
            } else {
                needPicsSelection = 5000;
                needPicsAllocation = 50000;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return new Pics(needPicsAllocation, needPicsSelection);
    }

    private String getUserPost(SharedPreferences settings) {
        return settings.getString("post", null);
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

    public Tax getFullTax(SQLiteDatabase db, SharedPreferences settings) {
        Tax tax = new Tax();
        db.beginTransaction();
        try {
            String sector_id = String.valueOf(getSectorId(settings, db));
            String schedule_id = String.valueOf(getScheduleId(settings, db));
            String[] selectionArgs = {sector_id, schedule_id};
            Cursor cursor = db.query(DbHelper.TABLE_TAXES, null, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

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

                tax.setTax_selection_os(cursor.getDouble(selectionOsTaxIndex));
                tax.setTax_allocation_os(cursor.getDouble(allocationOsTaxIndex));
                tax.setTax_selection_mez(cursor.getDouble(selectionMezTaxIndex));
                tax.setTax_allocation_mez(cursor.getDouble(allocationMezTaxIndex));

                tax.setBonus_allocation_mez_80(cursor.getDouble(bonusAllocationMez80Index));
                tax.setBonus_allocation_mez_90(cursor.getDouble(bonusAllocationMez90Index));
                tax.setBonus_allocation_mez_100(cursor.getDouble(bonusAllocationMez100Index));
                tax.setBonus_allocation_mez_120(cursor.getDouble(bonusAllocationMez120Index));
                tax.setBonus_selection_mez_80(cursor.getDouble(bonusSelectionMez80Index));
                tax.setBonus_selection_mez_90(cursor.getDouble(bonusSelectionMez90Index));
                tax.setBonus_selection_mez_100(cursor.getDouble(bonusSelectionMez100Index));
                tax.setBonus_selection_mez_120(cursor.getDouble(bonusSelectionMez120Index));

                tax.setBonus_allocation_os_80(cursor.getDouble(bonusAllocationOs80Index));
                tax.setBonus_allocation_os_90(cursor.getDouble(bonusAllocationOs90Index));
                tax.setBonus_allocation_os_100(cursor.getDouble(bonusAllocationOs100Index));
                tax.setBonus_allocation_os_120(cursor.getDouble(bonusAllocationOs120Index));
                tax.setBonus_selection_os_80(cursor.getDouble(bonusSelectionOs80Index));
                tax.setBonus_selection_os_90(cursor.getDouble(bonusSelectionOs90Index));
                tax.setBonus_selection_os_100(cursor.getDouble(bonusSelectionOs100Index));
                tax.setBonus_selection_os_120(cursor.getDouble(bonusSelectionOs120Index));
            }
            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return tax;
    }

    public Tax getMinTax(SQLiteDatabase db, SharedPreferences settings) {
        Tax tax = null;
        db.beginTransaction();
        try {
            String sector_id = String.valueOf(getSectorId(settings, db));
            String schedule_id = String.valueOf(getScheduleId(settings, db));
            String[] selectionArgs = {sector_id, schedule_id};
            String[] columnsTaxes = {DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX,
                    DbHelper.KEY_BONUS_SELECTION_MEZ_80, DbHelper.KEY_BONUS_SELECTION_OS_80, DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, DbHelper.KEY_BONUS_ALLOCATION_OS_80};
            Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);
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
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return tax;
    }

    public void updateTax(SQLiteDatabase db, SharedPreferences settings, Tax newTax) {
        db.beginTransaction();
        try {
            String sector_id = String.valueOf(getSectorId(settings, db));
            String schedule_id = String.valueOf(getScheduleId(settings, db));
            String[] selectionArgs = {sector_id, schedule_id};

            Cursor cursor = db.query(DbHelper.TABLE_TAXES, new String[] {DbHelper.KEY_ID}, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.KEY_SELECTION_OS_TAX, newTax.getTax_selection_os());
            contentValues.put(DbHelper.KEY_ALLOCATION_OS_TAX, newTax.getTax_allocation_os());
            contentValues.put(DbHelper.KEY_SELECTION_MEZ_TAX, newTax.getTax_selection_mez());
            contentValues.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, newTax.getTax_allocation_mez());

            if (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                int id = cursor.getInt(idIndex);
                db.update(DbHelper.TABLE_TAXES, contentValues, DbHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            }

            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void updateBonuses(SQLiteDatabase db, SharedPreferences settings, Bonus newBonus) {
        db.beginTransaction();
        try {
            String sector_id = String.valueOf(getSectorId(settings, db));
            String schedule_id = String.valueOf(getScheduleId(settings, db));
            int checkedBonus = settings.getInt("bonusIndex", 0);
            String[] selectionArgs = {sector_id, schedule_id};

            Cursor cursor = db.query(DbHelper.TABLE_TAXES, new String[] {DbHelper.KEY_ID}, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

            if (cursor.moveToNext()) {
                ContentValues contentValues = new ContentValues();
                double bonusSelectionOs = newBonus.getSelectionOsBonus();
                double bonusAllocationOs = newBonus.getAllocationOsBonus();
                double bonusSelectionMez = newBonus.getSelectionMezBonus();
                double bonusAllocationMez = newBonus.getAllocationMezBonus();

                if (checkedBonus == 0) {
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_OS_120, bonusSelectionOs);
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_OS_120, bonusAllocationOs);
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_MEZ_120, bonusSelectionMez);
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_120, bonusAllocationMez);
                }
                else if (checkedBonus == 1) {
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_OS_100, newBonus.getSelectionOsBonus());
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_OS_100, bonusAllocationOs);
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_MEZ_100, bonusSelectionMez);
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_100, bonusAllocationMez);
                }
                else if (checkedBonus == 2) {
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_OS_90, newBonus.getSelectionOsBonus());
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_OS_90, bonusAllocationOs);
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_MEZ_90, bonusSelectionMez);
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_90, bonusAllocationMez);
                }
                else if (checkedBonus == 3) {
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_OS_80, newBonus.getSelectionOsBonus());
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_OS_80, bonusAllocationOs);
                    contentValues.put(DbHelper.KEY_BONUS_SELECTION_MEZ_80, bonusSelectionMez);
                    contentValues.put(DbHelper.KEY_BONUS_ALLOCATION_MEZ_80, bonusAllocationMez);
                }

                int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                int id = cursor.getInt(idIndex);
                db.update(DbHelper.TABLE_TAXES, contentValues, DbHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            }

            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
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

    public double getMonthSalary(SQLiteDatabase db, String UID, Tax tax) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, new String[]{DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ}, DbHelper.KEY_MONTH + "=? AND " + DbHelper.KEY_USER_UID + " = ?", new String[] {String.valueOf(month), UID}, null, null, null);

        double salary = 0;
        while (cursor.moveToNext()) {
            int selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
            int selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
            int allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
            int allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);

            int selectionOs = cursor.getInt(selectionOsIndex);
            int selectionMez = cursor.getInt(selectionMezIndex);
            int allocationOs = cursor.getInt(allocationOsIndex);
            int allocationMez = cursor.getInt(allocationMezIndex);

            double selectionOsPay = tax.getTax_selection_os() * selectionOs;
            double selectionMezPay = tax.getTax_selection_mez() * selectionMez;
            double allocationOsPay = tax.getTax_allocation_os() * allocationOs;
            double allocationMezPay = tax.getTax_allocation_mez() * allocationMez;

            salary += selectionOsPay + selectionMezPay + allocationOsPay + allocationMezPay;
        }
        cursor.close();

        return salary;
    }

    public double getRealYearSalary(SQLiteDatabase db, String UID, Tax tax) {
        int workMonths = 0;
        double totalSalary = 0, yearRealSalary;

        Cursor cursor;
        for (int i = 1; i <= 12; i++) {
            double monthSalary = 0;
            cursor = db.query(DbHelper.TABLE_WORKDAYS, new String[]{DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ}, DbHelper.KEY_MONTH + "=? AND " + DbHelper.KEY_USER_UID + " = ?", new String[] {String.valueOf(i), UID}, null, null, null);
            while (cursor.moveToNext()) {
                int selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
                int selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
                int allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
                int allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);

                int selectionOs = cursor.getInt(selectionOsIndex);
                int selectionMez = cursor.getInt(selectionMezIndex);
                int allocationOs = cursor.getInt(allocationOsIndex);
                int allocationMez = cursor.getInt(allocationMezIndex);

                double selectionOsPay = tax.getTax_selection_os() * selectionOs;
                double selectionMezPay = tax.getTax_selection_mez() * selectionMez;
                double allocationOsPay = tax.getTax_allocation_os() * allocationOs;
                double allocationMezPay = tax.getTax_allocation_mez() * allocationMez;

                monthSalary += selectionOsPay + selectionMezPay + allocationOsPay + allocationMezPay;
            }
            if (monthSalary != 0) {
                workMonths += 1;
                totalSalary += monthSalary;
            }
            cursor.close();
        }
        db.close();

        if (totalSalary == 0)
            yearRealSalary = 0;
        else
        {
            if (workMonths < 12)
                yearRealSalary = (totalSalary / workMonths) * 12;
            else
                yearRealSalary = totalSalary;
        }

        return yearRealSalary;
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

    public double getAverageMonthSalary(DbHelper dbHelper, SharedPreferences settings) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int workMonths = 0;
        double totalSalary = 0, averageSalary;
        String UID = settings.getString("UID", ErrorHandler.getUidNotFoundError());

        Cursor cursor;
        for (int i = 1; i <= 12; i++) {
            cursor = db.rawQuery("SELECT SUM(" + KEY_PAY + ") FROM " + TABLE_WORKDAYS + " WHERE " + KEY_MONTH + " = ? AND " + KEY_USER_UID + " = ? ", new String[] {String.valueOf(i), UID});
            if (cursor.moveToNext()) {
                double monthSalary = cursor.getDouble(0);
                if (monthSalary != 0) {
                    workMonths += 1;
                    totalSalary += monthSalary;
                }
            }
            cursor.close();
        }
        db.close();

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

        cursor.close();

        return monthNorm;
    }

    public int getYearNorm(SQLiteDatabase db, SharedPreferences settings) {
        return getMonthNorm(db, settings) * 12;
    }

    public void synchronizeData(SQLiteDatabase db, SharedPreferences settings) {
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
        String UID = settings.getString("UID", null);
        Calendar calendar = Calendar.getInstance();
        HashMap<String, WorkDay> workDays = new HashMap<>();
        Cursor cursor = db.query(TABLE_WORKDAYS, null, KEY_USER_UID + " = ?", new String[] {UID}, null, null, KEY_YEAR + " DESC, " + KEY_MONTH + " DESC, " + KEY_DAY + " DESC");
        if (cursor.moveToNext()) {
            do {
                WorkDay workDay = new WorkDay();
                int yearColumnIndex = cursor.getColumnIndex(KEY_YEAR);
                int monthColumnIndex = cursor.getColumnIndex(KEY_MONTH);
                int dayColumnIndex = cursor.getColumnIndex(KEY_DAY);
                int isExtraDayColumnIndex = cursor.getColumnIndex(KEY_IS_EXTRA_PAY);
                int selectionOsColumnIndex = cursor.getColumnIndex(KEY_SELECTION_OS);
                int allocationOsColumnIndex = cursor.getColumnIndex(KEY_ALLOCATION_OS);
                int selectionMezColumnIndex = cursor.getColumnIndex(KEY_SELECTION_MEZ);
                int allocationMezColumnIndex = cursor.getColumnIndex(KEY_ALLOCATION_MEZ);
                int payColumnIndex = cursor.getColumnIndex(KEY_PAY);

                int year = cursor.getInt(yearColumnIndex);
                int month = cursor.getInt(monthColumnIndex);
                int day = cursor.getInt(dayColumnIndex);
                int isExtraDay = cursor.getInt(isExtraDayColumnIndex);
                int selectionOs = cursor.getInt(selectionOsColumnIndex);
                int allocationOs = cursor.getInt(allocationOsColumnIndex);
                int selectionMez = cursor.getInt(selectionMezColumnIndex);
                int allocationMez = cursor.getInt(allocationMezColumnIndex);
                double pay = cursor.getDouble(payColumnIndex);

                workDay.setUserUID(UID);
                workDay.setDay(day);
                workDay.setMonth(month);
                workDay.setYear(year);
                workDay.setExtraDay(isExtraDay);
                workDay.setSelectionOs(selectionOs);
                workDay.setAllocationOs(allocationOs);
                workDay.setSelectionMez(selectionMez);
                workDay.setAllocationMez(allocationMez);
                workDay.setPay(pay);

                String workDate = getDate(calendar, year, month, day);
                workDays.put(workDate, workDay);
            } while(cursor.moveToNext());
        }
        cursor.close();
        users.child(UID).child("workDays").removeValue();
        users.child(UID).child("workDays").setValue(workDays);
    }

    private String getDate(Calendar calendar, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return new SimpleDateFormat("yyyy-MM-dd", new Locale("RU", "ru")).format(new Date(calendar.getTimeInMillis()));
    }

    public void recoverData(DbHelper dbHelper, SharedPreferences settings) {
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
        String UID = settings.getString("UID", ErrorHandler.getUidNotFoundError());
        String[] columnsWorkDays = {DbHelper.KEY_ID};

        users.child(UID).child("workDays").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                dbHelper.close();
            }
            else {
                SQLiteDatabase db = getWritableDatabase();
                db.beginTransaction();
                try {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        WorkDay workDay = ds.getValue(WorkDay.class);
                        if (workDay != null) {
                            String[] selectionSectorsArgs = {String.valueOf(workDay.getYear()), String.valueOf(workDay.getMonth()), String.valueOf(workDay.getDay()), workDay.getUserUID()};
                            Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columnsWorkDays, DbHelper.KEY_YEAR + " = ? AND " + DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_DAY + " = ? AND " + DbHelper.KEY_USER_UID + " = ?", selectionSectorsArgs, null, null, null);

                            ContentValues cv = new ContentValues();
                            cv.put(KEY_USER_UID, workDay.getUserUID());
                            cv.put(KEY_YEAR, workDay.getYear());
                            cv.put(KEY_MONTH, workDay.getMonth());
                            cv.put(KEY_DAY, workDay.getDay());
                            cv.put(KEY_IS_EXTRA_PAY, workDay.getExtraDay());
                            cv.put(KEY_SELECTION_OS, workDay.getSelectionOs());
                            cv.put(KEY_ALLOCATION_OS, workDay.getAllocationOs());
                            cv.put(KEY_SELECTION_MEZ, workDay.getSelectionMez());
                            cv.put(KEY_ALLOCATION_MEZ, workDay.getAllocationMez());
                            cv.put(KEY_PAY, workDay.getPay());

                            if (cursor.moveToNext()) {
                                int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                                int id = cursor.getInt(idIndex);
                                db.update(DbHelper.TABLE_WORKDAYS, cv, DbHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)});
                            } else {
                                db.insert(DbHelper.TABLE_WORKDAYS, null, cv);
                            }
                            cursor.close();
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                    dbHelper.close();
                }
            }
        });
    }

    public void saveUserImage(String UID, Bitmap userImage) {
        SQLiteDatabase db = getWritableDatabase();
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream);
        byte[] imageInBytes = byteOutputStream.toByteArray();

        while (imageInBytes.length > 500000){
            byteOutputStream.reset();
            userImage = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
            Bitmap resized = Bitmap.createScaledBitmap(userImage, (int)(userImage.getWidth()*0.2), (int)(userImage.getHeight()*0.2), true);
            resized.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream);
            imageInBytes = byteOutputStream.toByteArray();
        }

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_UID, UID);
        cv.put(KEY_AVATAR, imageInBytes);

        Cursor cursor = db.query(DbHelper.TABLE_USERS_AVATARS, new String[] {DbHelper.KEY_ID}, DbHelper.KEY_USER_UID + " = ?", new String[] {UID}, null, null, null);
        if (cursor.moveToNext())
            db.update(TABLE_USERS_AVATARS, cv, DbHelper.KEY_USER_UID + " = ?", new String[] {UID});
        else
            db.insert(TABLE_USERS_AVATARS, null, cv);

        cursor.close();
        db.close();
    }

    public Bitmap getUserImage(String UID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_USERS_AVATARS, new String[] {DbHelper.KEY_AVATAR}, DbHelper.KEY_USER_UID + " = ?", new String[] {UID}, null, null, null);
        Bitmap userImage = null;
        byte[] userImageInBytes = null;

        if (cursor.moveToNext()) {
            int imageIndex = cursor.getColumnIndex(DbHelper.KEY_AVATAR);
            userImageInBytes = cursor.getBlob(imageIndex);
        }

        if (userImageInBytes != null)
            userImage = BitmapFactory.decodeByteArray(userImageInBytes, 0, userImageInBytes.length);

        cursor.close();
        db.close();

        return userImage;
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
