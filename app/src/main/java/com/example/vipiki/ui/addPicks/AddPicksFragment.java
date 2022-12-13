package com.example.vipiki.ui.addPicks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.databinding.FragmentAddPicksBinding;
import com.example.vipiki.ui.AddPicksDialogFragment;

public class AddPicksFragment extends Fragment {

    private FragmentAddPicksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddPicksViewModel addPicksViewModel =
                new ViewModelProvider(this).get(AddPicksViewModel.class);

        binding = FragmentAddPicksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddPicks;
        addPicksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Bundle bundle = new Bundle();
                bundle.putInt("year", year);
                bundle.putInt("month", month);
                bundle.putInt("day", day);
                FragmentManager manager = getParentFragmentManager();
                AddPicksDialogFragment addPicksDialogFragment = new AddPicksDialogFragment();
                addPicksDialogFragment.setArguments(bundle);
                addPicksDialogFragment.show(manager, "addPicksDialog");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}