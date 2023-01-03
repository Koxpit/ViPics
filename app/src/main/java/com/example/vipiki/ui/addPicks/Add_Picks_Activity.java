package com.example.vipiki.ui.addPicks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.R;
import com.example.vipiki.models.Pics;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class Add_Picks_Activity extends AppCompatActivity {
    private AddPicksViewModel addPicksViewModel;

    RelativeLayout rootRelativeLayout;
    Button add_button, back_button;
    EditText selection_os_editText;
    EditText allocation_os_editText;
    EditText selection_mez_editText;
    EditText allocation_mez_editText;
    EditText extraShiftPay_editText;
    CheckBox isExtraShift_checkBox;

    int day, month, year;
    private int isExtraDay = 0;
    private double pay = 0;
    private int selectionOs = 0, allocationOs = 0;
    private int selectionMez = 0, allocationMez = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picks);

        Log.d("CLEAR_VM", "Activity AddPicks created");
        addPicksViewModel = new ViewModelProvider(this, new AddPicksViewModelFactory(this,
                getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(AddPicksViewModel.class);

        getBundleValues();
        initComponents();
        initValues();
    }

    private void getBundleValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        day = bundle.getInt("day");
        month = bundle.getInt("month");
        year = bundle.getInt("year");
        selectionOs = bundle.getInt("selectionOs");
        allocationOs = bundle.getInt("allocationOs");
        selectionMez = bundle.getInt("selectionMez");
        allocationMez = bundle.getInt("allocationMez");
        isExtraDay = bundle.getInt("isExtraDay");
        pay = bundle.getDouble("pay");
    }

    private void initComponents() {
        rootRelativeLayout = findViewById(R.id.rootRelativeLayout);
        add_button = findViewById(R.id.save_button);
        back_button = findViewById(R.id.back_button);
        selection_os_editText = findViewById(R.id.selectionOS_editText);
        allocation_os_editText = findViewById(R.id.allocationOS_editText);
        selection_mez_editText = findViewById(R.id.selectionMez_editText);
        allocation_mez_editText = findViewById(R.id.allocationMez_editText);
        extraShiftPay_editText = findViewById(R.id.extraShiftPay_editText);
        isExtraShift_checkBox = findViewById(R.id.isExtraShift_checkBox);

        setListeners();
    }

    private void setListeners() {
        selection_os_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                    selectionOs = 0;
                else
                    selectionOs = Integer.parseInt(selection_os_editText.getText().toString());
            }
        });

        selection_mez_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                    selectionMez = 0;
                else
                    selectionMez = Integer.parseInt(selection_mez_editText.getText().toString());
            }
        });

        allocation_os_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                    allocationOs = 0;
                else
                    allocationOs = Integer.parseInt(allocation_os_editText.getText().toString());
            }
        });

        allocation_mez_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s))
                    allocationMez = 0;
                else
                    allocationMez = Integer.parseInt(allocation_mez_editText.getText().toString());
            }
        });

        extraShiftPay_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(extraShiftPay_editText.getText().toString()))
                    pay = 0;
                else
                    pay = Double.parseDouble(extraShiftPay_editText.getText().toString());
            }
        });

        add_button.setOnClickListener(view -> {
            if (addPicksViewModel.getSectorId() == -1) {
                sendSectorNotFoundError();
                return;
            }
            if (addPicksViewModel.getScheduleId() == -1) {
                sendScheduleNotFoundError();
                return;
            }

            if (isExtraDay == 0) {
                Pics pics = new Pics(allocationOs, selectionOs, allocationMez, selectionMez);
                addPicksViewModel.addWorkDay(day, month, year, pics);
            }
            else
                addPicksViewModel.addExtraDay(day, month, year, pay);
        });

        isExtraShift_checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isExtraDay == 0) {
                isExtraDay = 1;
                extraShiftPay_editText.setEnabled(true);
            }
            else if (isExtraDay == 1) {
                isExtraDay = 0;
                extraShiftPay_editText.setEnabled(false);
            }
        });
    }

    private void initValues() {
        selection_os_editText.setText(String.valueOf(selectionOs));
        allocation_os_editText.setText(String.valueOf(allocationOs));
        selection_mez_editText.setText(String.valueOf(selectionMez));
        allocation_mez_editText.setText(String.valueOf(allocationMez));

        if (isExtraDay == 1) {
            isExtraShift_checkBox.setChecked(true);
            extraShiftPay_editText.setEnabled(true);
        }
        else {
            isExtraShift_checkBox.setChecked(false);
            extraShiftPay_editText.setEnabled(false);
        }

        extraShiftPay_editText.setText(String.format(Locale.ENGLISH, "%(.2f", pay));
    }

    private void sendSectorNotFoundError() {
        Snackbar.make(rootRelativeLayout, "Ошибка: сектор не найден.", Snackbar.LENGTH_SHORT).show();
        Log.d("SECTOR_NOT_FOUND", "Сектор не найден в БД");
    }

    private void sendScheduleNotFoundError() {
        Snackbar.make(rootRelativeLayout, "Ошибка: график работы не найден.", Snackbar.LENGTH_SHORT).show();
        Log.d("SCHEDULE_NOT_FOUND", "График работы не найден в БД");
    }
}