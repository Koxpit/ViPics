package com.example.vipiki.ui.settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vipiki.R;
import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;
import com.example.vipiki.ui.menu.MenuViewModel;
import com.example.vipiki.ui.menu.MenuViewModelFactory;
import com.example.vipiki.ui.settings.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;

    private TextView editProfileTextView;
    private TextView editTaxesTextView;
    private TextView syncDataTextView;
    private TextView recoverDataTextView;
    private TextView changePasswordTextView;
    private TextView changeEmailTextView;

    private String post;
    private String schedule;
    private String sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.d("CLEAR_VM", "Activity Settings created");
        settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(this, getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(SettingsViewModel.class);

        editProfileTextView = findViewById(R.id.editProfileTextView);
        editTaxesTextView = findViewById(R.id.editTaxesTextView);
        syncDataTextView = findViewById(R.id.syncDataTextView);
        recoverDataTextView = findViewById(R.id.recoverDataTextView);
        changePasswordTextView = findViewById(R.id.changePasswordTextView);
        changeEmailTextView = findViewById(R.id.changeEmailTextView);

        editProfileTextView.setOnClickListener(view -> showEditProfileWindow());
        editTaxesTextView.setOnClickListener(view -> showEditTaxesWindow());
        syncDataTextView.setOnClickListener(v -> syncData());
        recoverDataTextView.setOnClickListener(v -> recoverData());
        changePasswordTextView.setOnClickListener(v -> showChangePasswordWindow());
        changeEmailTextView.setOnClickListener(v -> showChangeEmailWindow());
    }

    private void showEditProfileWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Редактирование профиля");
        String name = settingsViewModel.getUserName();

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_profile_view = inflater.inflate(R.layout.activity_edit_profile, null);
        EditText nameEditText = edit_profile_view.findViewById(R.id.editTextUserName);
        Spinner spinnerPosts = edit_profile_view.findViewById(R.id.spinnerPosts);
        Spinner spinnerSchedules = edit_profile_view.findViewById(R.id.spinnerSchedules);
        Spinner spinnerSectors = edit_profile_view.findViewById(R.id.spinnerSectors);

        nameEditText.setText(name);
        initSpinners(spinnerPosts, spinnerSchedules, spinnerSectors);

        dialog.setView(edit_profile_view);

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            UserSettings userSettings = new UserSettings();
            userSettings.setName(nameEditText.getText().toString());
            userSettings.setPost(post);
            userSettings.setSchedule(schedule);
            userSettings.setSector(sector);
            userSettings.setPostIndex(spinnerPosts.getSelectedItemPosition());
            userSettings.setScheduleIndex(spinnerSchedules.getSelectedItemPosition());
            userSettings.setSectorIndex(spinnerSectors.getSelectedItemPosition());
            settingsViewModel.editProfile(userSettings);

            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void initSpinners(Spinner spinnerPosts, Spinner spinnerSchedules, Spinner spinnerSectors) {
        initPostsSpinner(spinnerPosts);
        initSchedulesSpinner(spinnerSchedules);
        initSectorsSpinner(spinnerSectors);
    }

    private void initPostsSpinner(Spinner spinnerPosts) {
        ArrayAdapter<String> adapterPosts = settingsViewModel.getAdapterPosts();
        spinnerPosts.setAdapter(adapterPosts);
        spinnerPosts.setSelection(settingsViewModel.getPostIndex());

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
    }

    private void initSectorsSpinner(Spinner spinnerSectors) {
        ArrayAdapter<String> adapterSectors = settingsViewModel.getAdapterSectors();
        spinnerSectors.setAdapter(adapterSectors);
        spinnerSectors.setSelection(settingsViewModel.getSectorIndex());

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

    private void initSchedulesSpinner(Spinner spinnerSchedules) {
        ArrayAdapter<String> adapterSchedules = settingsViewModel.getAdapterSchedules();
        spinnerSchedules.setAdapter(adapterSchedules);
        spinnerSchedules.setSelection(settingsViewModel.getScheduleIndex());

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
    }

    private void showEditTaxesWindow() {
        double selectionOsTax = 0, allocationOsTax = 0;
        double selectionMezTax = 0, allocationMezTax = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменение ставки");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_taxes_view = inflater.inflate(R.layout.activity_edit_taxes, null);

        EditText selectionOsTaxEditText = edit_taxes_view.findViewById(R.id.editTextSelectionOsTax);
        EditText allocationOsTaxEditText = edit_taxes_view.findViewById(R.id.editTextAllocationOsTax);
        EditText selectionMezTaxEditText = edit_taxes_view.findViewById(R.id.editTextSelectionMezTax);
        EditText allocationMezTaxEditText = edit_taxes_view.findViewById(R.id.editTextAllocationMezTax);

        Tax tax = settingsViewModel.getTax();
        selectionOsTax = tax.getTax_selection_os();
        allocationOsTax = tax.getTax_allocation_os();
        selectionMezTax = tax.getTax_selection_mez();
        allocationMezTax = tax.getTax_allocation_mez();

        selectionOsTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionOsTax));
        allocationOsTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationOsTax));
        selectionMezTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionMezTax));
        allocationMezTaxEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationMezTax));

        dialog.setView(edit_taxes_view);

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            double selection_os_tax = Double.parseDouble(selectionOsTaxEditText.getText().toString());
            double allocation_os_tax = Double.parseDouble(allocationOsTaxEditText.getText().toString());
            double selection_mez_tax = Double.parseDouble(selectionMezTaxEditText.getText().toString());
            double allocation_mez_tax = Double.parseDouble(allocationMezTaxEditText.getText().toString());

            Tax newTax = new Tax();
            newTax.setTax_selection_os(selection_os_tax);
            newTax.setTax_allocation_os(allocation_os_tax);
            newTax.setTax_selection_mez(selection_mez_tax);
            newTax.setTax_allocation_mez(allocation_mez_tax);
            settingsViewModel.updateTax(newTax);

            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void syncData() {
        settingsViewModel.syncData();
        Toast.makeText(this, "Синхронизация успешно пройдена", Toast.LENGTH_SHORT).show();
    }

    private void recoverData() {
        settingsViewModel.recoverData();
        Toast.makeText(this, "Восстановление завершено", Toast.LENGTH_SHORT).show();
    }

    private void showChangePasswordWindow() {

    }

    private void showChangeEmailWindow() {

    }
}