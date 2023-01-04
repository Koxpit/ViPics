package com.example.vipiki.ui.addPicks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.databinding.FragmentAddPicksBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddPicksFragment extends Fragment {

    private FragmentAddPicksBinding binding;
    private AddPicksViewModel addPicksViewModel;
    private boolean dateIsSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addPicksViewModel =
                new ViewModelProvider(this, new AddPicksViewModelFactory(getContext(), getContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(AddPicksViewModel.class);

        binding = FragmentAddPicksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.updateSpinnerButton.setOnClickListener(view -> fillWorkDaySpinner(binding.spinnerWorkDays));

        fillWorkDaySpinner(binding.spinnerWorkDays);

        CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            if (addPicksViewModel.isCorrectDate(day, month, year)) {
                Bundle bundle = addPicksViewModel.getWorkDayBundle(day, month+1, year);
                openWorkDayEditor(bundle);
            }
            else
                Snackbar.make(binding.rootRelativeLayout, "Нельзя выбрать дату больше текущей.", Snackbar.LENGTH_SHORT).show();
        });

        return root;
    }

    private void fillWorkDaySpinner(Spinner spinner) {
        ArrayAdapter<String> adapterWorkDays = addPicksViewModel.getAdapterWorkDays();
        spinner.setAdapter(adapterWorkDays);
        dateIsSelected = false;
        AdapterView.OnItemSelectedListener itemSelectedWorkDayListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {
                    if (dateIsSelected) {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd", new Locale("RU", "ru"));
                        Date theDate = dateFormatter.parse((String) adapterView.getItemAtPosition(position));
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(theDate);
                        binding.calendarView.setDate(calendar.getTimeInMillis());

                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = calendar.get(Calendar.YEAR);

                        Bundle bundle = addPicksViewModel.getWorkDayBundle(day, month, year);
                        openWorkDayEditor(bundle);
                    }
                    else dateIsSelected = true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedWorkDayListener);
    }

    private void openWorkDayEditor(Bundle bundle) {
        FragmentManager manager = getParentFragmentManager();

        if (bundle.getBoolean("workDayExist")) {
            EditPicsDialogFragment editPicsDialogFragment = new EditPicsDialogFragment();
            editPicsDialogFragment.setArguments(bundle);
            editPicsDialogFragment.show(manager, "editPicsDialogFragment");
            Snackbar.make(binding.rootRelativeLayout, String.valueOf(bundle.getDouble("pay", 0)), Snackbar.LENGTH_SHORT).show();
        } else {
            AddPicksDialogFragment addPicksDialogFragment = new AddPicksDialogFragment();
            addPicksDialogFragment.setArguments(bundle);
            addPicksDialogFragment.show(manager, "addPicksDialog");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}