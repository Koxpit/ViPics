package com.example.vipiki.ui.statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vipiki.R;
import com.example.vipiki.models.Tax;
import com.example.vipiki.models.UserSettings;

import java.util.Locale;

public class StatisticActivity extends AppCompatActivity {
    private StatisticViewModel statisticViewModel;
    private TextView userNameTextView, userPostTextView, userScheduleTextView, userSectorTextView;
    private TextView userMonthSalaryTextView, userRealYearSalaryTextView, userNormYearSalaryTextView;
    private TextView taxAllocationOsTextView, taxSelectionOsTextView;
    private TextView taxAllocationMezTextView, taxSelectionMezTextView;
    private RadioGroup radioGroupBonuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        statisticViewModel = new ViewModelProvider(this, new StatisticViewModelFactory(this)).get(StatisticViewModel.class);

        initComponents();

        UserSettings userSettings = statisticViewModel.getProfileData();
        userNameTextView.setText(userSettings.getName());
        userPostTextView.setText(userSettings.getPost());
        userScheduleTextView.setText(userSettings.getSchedule());
        userSectorTextView.setText(userSettings.getSector());

        radioGroupBonuses.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);
        Tax tax = statisticViewModel.getCurrentTax();
        updateData(tax);
    }

    private void initComponents() {
        userNameTextView = findViewById(R.id.userNameTextView);
        userPostTextView = findViewById(R.id.userPostTextView);
        userScheduleTextView = findViewById(R.id.userScheduleTextView);
        userSectorTextView = findViewById(R.id.userSectorTextView);
        userMonthSalaryTextView = findViewById(R.id.userMonthSalaryTextView);
        userRealYearSalaryTextView = findViewById(R.id.userRealYearSalaryTextView);
        userNormYearSalaryTextView = findViewById(R.id.userNormYearSalaryTextView);
        taxAllocationOsTextView = findViewById(R.id.taxAllocationOsTextView);
        taxSelectionOsTextView = findViewById(R.id.taxSelectionOsTextView);
        taxAllocationMezTextView = findViewById(R.id.taxAllocationMezTextView);
        taxSelectionMezTextView = findViewById(R.id.taxSelectionMezTextView);
        radioGroupBonuses = findViewById(R.id.bonusRadioGroup);
        int checkedIndex = statisticViewModel.getCheckedBonusIndex();
        RadioButton savedCheckedRadioButton = (RadioButton) radioGroupBonuses
                .getChildAt(checkedIndex);
        savedCheckedRadioButton.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener radioGroupOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            RadioButton checkedRadioButton = radioGroupBonuses
                    .findViewById(checkedId);
            int checkedIndex = radioGroupBonuses.indexOfChild(checkedRadioButton);
            statisticViewModel.setBonusIndex(checkedIndex);
            Tax tax = statisticViewModel.getCurrentTax();
            updateData(tax);
        }
    };

    private void updateData(Tax tax) {
        taxSelectionOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax.getTax_selection_os()));
        taxAllocationOsTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax.getTax_allocation_os()));
        taxSelectionMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax.getTax_selection_mez()));
        taxAllocationMezTextView.setText(String.format(Locale.ENGLISH, "%(.2f", tax.getTax_allocation_mez()));

        userMonthSalaryTextView.setText(String.format(Locale.ENGLISH, "%(.2f", statisticViewModel.getMonthSalary(tax)));
        userRealYearSalaryTextView.setText(String.format(Locale.ENGLISH, "%(.2f", statisticViewModel.getRealYearSalary(tax)));
        userNormYearSalaryTextView.setText(String.format(Locale.ENGLISH, "%(.2f", statisticViewModel.getNormYearSalary(tax)));
    }
}