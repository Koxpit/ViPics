package com.example.vipiki;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vipiki.database.DbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    DbHelper dbHelper;
    TextView editProfileTextView;
    TextView editTaxesTextView;

    private String post, schedule, sector, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        dialog.setNegativeButton("Выйти", (dialogInterface, i) -> {
            dbHelper.close();
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            name = nameEditText.getText().toString();
            updateFirebaseUser();
            updateSettingsUser(settings);

            dbHelper.close();

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
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
        editor.apply();
    }

    private void initSpinners(View edit_profile_view) {
        Spinner spinnerPosts = dbHelper.getFilledPostsSpinner(edit_profile_view.findViewById(R.id.spinnerPosts), getBaseContext());
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

        Spinner spinnerSchedules = dbHelper.getFilledSchedulesSpinner(edit_profile_view.findViewById(R.id.spinnerSchedules), getBaseContext());
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

        Spinner spinnerSectors = dbHelper.getFilledSectorsSpinner(edit_profile_view.findViewById(R.id.spinnerSectors), getBaseContext());
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

    }
}