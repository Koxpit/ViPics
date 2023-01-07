package com.example.vipiki.ui.settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vipiki.R;
import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.messages.success.SuccessHandler;
import com.example.vipiki.models.Bonus;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel settingsViewModel;
    private String post, schedule, sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsViewModel = new ViewModelProvider(this, new SettingsViewModelFactory(this)).get(SettingsViewModel.class);

        TextView editProfileTextView = findViewById(R.id.editProfileTextView);
        TextView editBonusesTextView = findViewById(R.id.editBonusesTextView);
        TextView editTaxesTextView = findViewById(R.id.editTaxesTextView);
        TextView syncDataTextView = findViewById(R.id.syncDataTextView);
        TextView recoverDataTextView = findViewById(R.id.recoverDataTextView);
        TextView changePasswordTextView = findViewById(R.id.changePasswordTextView);
        TextView changeEmailTextView = findViewById(R.id.changeEmailTextView);

        editProfileTextView.setOnClickListener(view -> showEditProfileWindow());
        editBonusesTextView.setOnClickListener(view -> showEditBonusesWindow());
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

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> dialogInterface.dismiss());

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

            SuccessHandler.sendProfileChangedSuccessMessage(SettingsActivity.this);
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
        double selectionOsTax, allocationOsTax;
        double selectionMezTax, allocationMezTax;
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

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> dialogInterface.dismiss());

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

            SuccessHandler.sendTaxChangedSuccessMessage(SettingsActivity.this);
            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void showEditBonusesWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменение премии");

        LayoutInflater inflater = LayoutInflater.from(this);
        View edit_bonuses_view = inflater.inflate(R.layout.activity_edit_bonuses, null);

        EditText selectionOsBonusEditText = edit_bonuses_view.findViewById(R.id.editTextSelectionOsBonus);
        EditText allocationOsBonusEditText = edit_bonuses_view.findViewById(R.id.editTextAllocationOsBonus);
        EditText selectionMezBonusEditText = edit_bonuses_view.findViewById(R.id.editTextSelectionMezBonus);
        EditText allocationMezBonusEditText = edit_bonuses_view.findViewById(R.id.editTextAllocationMezBonus);
        RadioGroup radioGroupBonuses = edit_bonuses_view.findViewById(R.id.bonusRadioGroup);
        RadioButton savedCheckedRadioButton = (RadioButton) radioGroupBonuses
                .getChildAt(settingsViewModel.getCheckedBonusIndex());
        savedCheckedRadioButton.setChecked(true);

        RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = (group, checkedId) -> {
            RadioButton checkedRadioButton = radioGroupBonuses
                    .findViewById(checkedId);
            int checkedIndex = radioGroupBonuses.indexOfChild(checkedRadioButton);
            settingsViewModel.setBonusIndex(checkedIndex);

            Bonus currentBonus = settingsViewModel.getBonus();
            double selectionOsBonus = currentBonus.getSelectionOsBonus();
            double allocationOsBonus = currentBonus.getAllocationOsBonus();
            double selectionMezBonus = currentBonus.getSelectionMezBonus();
            double allocationMezBonus = currentBonus.getAllocationMezBonus();
            selectionOsBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionOsBonus));
            allocationOsBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationOsBonus));
            selectionMezBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", selectionMezBonus));
            allocationMezBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", allocationMezBonus));
        };
        radioGroupBonuses.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);

        Bonus currentBonus = settingsViewModel.getBonus();
        selectionOsBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", currentBonus.getSelectionOsBonus()));
        allocationOsBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", currentBonus.getAllocationOsBonus()));
        selectionMezBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", currentBonus.getSelectionMezBonus()));
        allocationMezBonusEditText.setText(String.format(Locale.ENGLISH, "%(.2f", currentBonus.getAllocationMezBonus()));

        dialog.setView(edit_bonuses_view);

        dialog.setNegativeButton("Назад", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Изменить", (dialogInterface, i) -> {
            double selection_os_bonus = Double.parseDouble(selectionOsBonusEditText.getText().toString());
            double allocation_os_bonus = Double.parseDouble(allocationOsBonusEditText.getText().toString());
            double selection_mez_bonus = Double.parseDouble(selectionMezBonusEditText.getText().toString());
            double allocation_mez_bonus = Double.parseDouble(allocationMezBonusEditText.getText().toString());

            Bonus newBonus = new Bonus();
            newBonus.setSelectionOsBonus(selection_os_bonus);
            newBonus.setAllocationOsBonus(allocation_os_bonus);
            newBonus.setSelectionMezBonus(selection_mez_bonus);
            newBonus.setAllocationMezBonus(allocation_mez_bonus);
            settingsViewModel.updateBonuses(newBonus);

            SuccessHandler.sendBonusChangedSuccessMessage(SettingsActivity.this);
            dialogInterface.dismiss();
        });

        dialog.show();
    }

    private void syncData() {
        settingsViewModel.syncData();
        SuccessHandler.sendSyncSuccessMessage(SettingsActivity.this);
    }

    private void recoverData() {
        try {
            settingsViewModel.recoverData();
            SuccessHandler.sendRecoverSuccessMessage(SettingsActivity.this);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Сохранить", (dialogInterface, i) -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String email = settingsViewModel.getEmail();

            if (settingsViewModel.inputIncorrect(oldPassword, newPassword, confirmPassword)) {
                ErrorHandler.sendEmptyEntryError(SettingsActivity.this);
                return;
            }

            if (settingsViewModel.passwordIncorrect(newPassword)) {
                ErrorHandler.sendIncorrectPasswordError(SettingsActivity.this);
                return;
            }

            if (!settingsViewModel.passwordsMatch(newPassword, confirmPassword)) {
                ErrorHandler.sendPasswordsMatchError(SettingsActivity.this);
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

            user.reauthenticate(credential).addOnCompleteListener(reauthTask -> {
                if (reauthTask.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(editPasswordTask -> {
                        if (editPasswordTask.isSuccessful())
                            SuccessHandler.sendPasswordSuccessMessage(SettingsActivity.this);
                        else
                            ErrorHandler.sendUpdatePasswordError(SettingsActivity.this);
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

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Сохранить", (dialogInterface, i) -> {
            String currentPassword = currentPasswordEditText.getText().toString();
            String newEmail = newEmailEditText.getText().toString();
            String currentEmail = settingsViewModel.getEmail();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(reauthTask -> {
                if (reauthTask.isSuccessful()) {
                    user.updateEmail(newEmail).addOnCompleteListener(editEmailTask -> {
                        if (editEmailTask.isSuccessful()) {
                            SuccessHandler.sendEmailSuccessMessage(SettingsActivity.this);
                            settingsViewModel.saveNewEmail(newEmail);
                        }
                        else
                            ErrorHandler.sendUpdateEmailError(SettingsActivity.this);
                    });
                }
            });

            dialogInterface.dismiss();
        });

        dialog.show();
    }
}