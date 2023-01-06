package com.example.vipiki.ui.settings;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;
    private String post, schedule, sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(this, getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(SettingsViewModel.class);

        TextView editProfileTextView = findViewById(R.id.editProfileTextView);
        TextView editTaxesTextView = findViewById(R.id.editTaxesTextView);
        TextView syncDataTextView = findViewById(R.id.syncDataTextView);
        TextView recoverDataTextView = findViewById(R.id.recoverDataTextView);
        TextView changePasswordTextView = findViewById(R.id.changePasswordTextView);
        TextView changeEmailTextView = findViewById(R.id.changeEmailTextView);

        editProfileTextView.setOnClickListener(view -> showEditProfileWindow());
        editTaxesTextView.setOnClickListener(view -> showEditTaxesWindow());
        syncDataTextView.setOnClickListener(v -> syncData());
        recoverDataTextView.setOnClickListener(v -> recoverData());
        changePasswordTextView.setOnClickListener(v -> showChangePasswordWindow());
        changeEmailTextView.setOnClickListener(v -> showChangeEmailWindow());
    }

    private void showEditProfileWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Редактирование профиля (UID)\n" + settingsViewModel.getUID());
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменение пароля");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_password_view = inflater.inflate(R.layout.activity_change_password, null);
        EditText oldPasswordEditText = edit_password_view.findViewById(R.id.editTextOldUserPassword);
        EditText newPasswordEditText = edit_password_view.findViewById(R.id.editTextUserPassword);
        EditText confirmPasswordEditText = edit_password_view.findViewById(R.id.confirmPasswordEditText);

        dialog.setView(edit_password_view);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Сохранить", (dialogInterface, i) -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String email = settingsViewModel.getEmail();

            if (settingsViewModel.inputIncorrect(oldPassword, newPassword, confirmPassword)) {
                Toast.makeText(SettingsActivity.this, "Заполнены не все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            if (settingsViewModel.passwordIncorrect(newPassword)) {
                Toast.makeText(SettingsActivity.this, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!settingsViewModel.passwordsMatch(newPassword, confirmPassword)) {
                Toast.makeText(SettingsActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

            user.reauthenticate(credential).addOnCompleteListener(reauthTask -> {
                if (reauthTask.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(editPasswordTask -> {
                        if (editPasswordTask.isSuccessful())
                            Toast.makeText(SettingsActivity.this, "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SettingsActivity.this, "Ошибка. Повторите попытку позже", Toast.LENGTH_SHORT).show();
                    });
                }
            });

            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void showChangeEmailWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменение почты");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_email_view = inflater.inflate(R.layout.activity_change_email, null);
        EditText currentPasswordEditText = edit_email_view.findViewById(R.id.editTextCurrentUserPassword);
        EditText newEmailEditText = edit_email_view.findViewById(R.id.editTextUserEmail);

        dialog.setView(edit_email_view);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        dialog.setPositiveButton("Сохранить", (dialogInterface, i) -> {
            String currentPassword = currentPasswordEditText.getText().toString();
            String newEmail = newEmailEditText.getText().toString();
            String currentEmail = settingsViewModel.getEmail();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updateEmail(newEmail).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(SettingsActivity.this, "Почтовый адрес успешно изменен", Toast.LENGTH_SHORT).show();
                            settingsViewModel.saveNewEmail(newEmail);
                        }
                        else
                            Toast.makeText(SettingsActivity.this, "Произошла ошибка при изменении пароля", Toast.LENGTH_SHORT).show();
                    });
                }
            });

            dialogInterface.dismiss();
        });

        dialog.show();
    }
}