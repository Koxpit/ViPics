package com.example.vipiki;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.viewModels.SettingsViewModel;
import com.example.vipiki.viewModels.StatisticViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;

    DbHelper dbHelper;
    TextView editProfileTextView;
    TextView editTaxesTextView;

    private Spinner spinnerPosts, spinnerSchedules, spinnerSectors;

    private String post, schedule, sector, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d("CLEAR_VM", "Activity Settings created");
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        dbHelper = new DbHelper(this);

        editProfileTextView = findViewById(R.id.editProfileTextView);
        editTaxesTextView = findViewById(R.id.editTaxesTextView);

        editProfileTextView.setOnClickListener(view -> showEditProfileWindow());
        editTaxesTextView.setOnClickListener(view -> showEditTaxesWindow());
    }

    private void showEditProfileWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Редактирование профиля");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_profile_view = inflater.inflate(R.layout.activity_edit_profile, null);

        EditText nameEditText = edit_profile_view.findViewById(R.id.editTextUserName);
        SharedPreferences settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        name = dbHelper.getUserName(settings);
        nameEditText.setText(name);
        initSpinners(edit_profile_view);

        dialog.setView(edit_profile_view);

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> {
            dbHelper.close();
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            name = nameEditText.getText().toString();
            updateFirebaseUser();
            updateSettingsUser(settings);

            dbHelper.close();

            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void updateFirebaseUser() {
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference firebaseDb = FirebaseDatabase.getInstance().getReference();
        firebaseDb.child("users").child(UID).child("name").setValue(name);
        firebaseDb.child("users").child(UID).child("post").setValue(post);
        firebaseDb.child("users").child(UID).child("schedule").setValue(schedule);
        firebaseDb.child("users").child(UID).child("sector").setValue(sector);
    }

    private void updateSettingsUser(SharedPreferences settings) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", name);
        editor.putString("post", post);
        editor.putString("schedule", schedule);
        editor.putString("sector", sector);
        editor.putInt("postIndex", spinnerPosts.getSelectedItemPosition());
        editor.putInt("scheduleIndex", spinnerSchedules.getSelectedItemPosition());
        editor.putInt("sectorIndex", spinnerSectors.getSelectedItemPosition());
        editor.apply();
    }

    private void initSpinners(View edit_profile_view) {
        SharedPreferences settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);

        spinnerPosts = dbHelper.getFilledPostsSpinner(edit_profile_view.findViewById(R.id.spinnerPosts), getBaseContext());
        spinnerPosts.setSelection(settings.getInt("postIndex", 0));
        AdapterView.OnItemSelectedListener itemSelectedPostListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                post = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerPosts.setOnItemSelectedListener(itemSelectedPostListener);

        spinnerSchedules = dbHelper.getFilledSchedulesSpinner(edit_profile_view.findViewById(R.id.spinnerSchedules), getBaseContext());
        spinnerSchedules.setSelection(settings.getInt("scheduleIndex", 0));
        AdapterView.OnItemSelectedListener itemSelectedScheduleListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schedule = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerSchedules.setOnItemSelectedListener(itemSelectedScheduleListener);

        spinnerSectors = dbHelper.getFilledSectorsSpinner(edit_profile_view.findViewById(R.id.spinnerSectors), getBaseContext());
        spinnerSectors.setSelection(settings.getInt("sectorIndex", 0));
        AdapterView.OnItemSelectedListener itemSelectedSectorListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sector = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerSectors.setOnItemSelectedListener(itemSelectedSectorListener);
    }

    private void showEditTaxesWindow() {
        double selectionOsTax = 0, allocationOsTax = 0;
        double selectionMezTax = 0, allocationMezTax = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменение ставки");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_taxes_view = inflater.inflate(R.layout.activity_edit_taxes, null);

        SharedPreferences settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText selectionOsTaxEditText = edit_taxes_view.findViewById(R.id.editTextSelectionOsTax);
        EditText allocationOsTaxEditText = edit_taxes_view.findViewById(R.id.editTextAllocationOsTax);
        EditText selectionMezTaxEditText = edit_taxes_view.findViewById(R.id.editTextSelectionMezTax);
        EditText allocationMezTaxEditText = edit_taxes_view.findViewById(R.id.editTextAllocationMezTax);

        int sector_id = dbHelper.getSectorId(settings, db);
        int schedule_id = dbHelper.getScheduleId(settings, db);

        String[] selectionArgs = {String.valueOf(sector_id), String.valueOf(schedule_id)};
        String[] columnsTaxes = {DbHelper.KEY_ID, DbHelper.KEY_SELECTION_OS_TAX, DbHelper.KEY_ALLOCATION_OS_TAX, DbHelper.KEY_SELECTION_MEZ_TAX, DbHelper.KEY_ALLOCATION_MEZ_TAX};

        Cursor cursor = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            int selectionOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS_TAX);
            int allocationOsTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS_TAX);
            int selectionMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ_TAX);
            int allocationMezTaxIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ_TAX);

            selectionOsTax = cursor.getDouble(selectionOsTaxIndex);
            allocationOsTax = cursor.getDouble(allocationOsTaxIndex);
            selectionMezTax = cursor.getDouble(selectionMezTaxIndex);
            allocationMezTax = cursor.getDouble(allocationMezTaxIndex);
        }
        cursor.close();

        selectionOsTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionOsTax));
        allocationOsTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationOsTax));
        selectionMezTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionMezTax));
        allocationMezTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationMezTax));

        dialog.setView(edit_taxes_view);

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> {
            dbHelper.close();
            db.close();
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            double selection_os_tax = Double.parseDouble(selectionOsTaxEditText.getText().toString());
            double allocation_os_tax = Double.parseDouble(allocationOsTaxEditText.getText().toString());
            double selection_mez_tax = Double.parseDouble(selectionMezTaxEditText.getText().toString());
            double allocation_mez_tax = Double.parseDouble(allocationMezTaxEditText.getText().toString());
            Cursor cur = db.query(DbHelper.TABLE_TAXES, columnsTaxes, DbHelper.KEY_SECTOR_ID + "=? AND " + DbHelper.KEY_SCHEDULE_ID + "=?", selectionArgs, null, null, null);

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.KEY_SELECTION_OS_TAX, selection_os_tax);
            contentValues.put(DbHelper.KEY_ALLOCATION_OS_TAX, allocation_os_tax);
            contentValues.put(DbHelper.KEY_SELECTION_MEZ_TAX, selection_mez_tax);
            contentValues.put(DbHelper.KEY_ALLOCATION_MEZ_TAX, allocation_mez_tax);

            if (cur.moveToNext()) {
                int idIndex = cur.getColumnIndex(DbHelper.KEY_ID);
                int id = cur.getInt(idIndex);
                db.update(DbHelper.TABLE_TAXES, contentValues, DbHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            }

            cur.close();
            dbHelper.close();
            db.close();
            dialogInterface.dismiss();
        });

        dialog.show();
    }
}