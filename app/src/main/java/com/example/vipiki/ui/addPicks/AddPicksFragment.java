package com.example.vipiki.ui.addPicks;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.databinding.FragmentAddPicksBinding;
import com.example.vipiki.ui.AddPicksDialogFragment;
import com.example.vipiki.ui.EditPicsDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Locale ruLocal = new Locale("ru", "RU");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", ruLocal);
            Date currentDate = null;
            Date selectedDate = null;
            try {
                month += 1;
                Calendar calendar = Calendar.getInstance();
                currentDate = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
                selectedDate = simpleDateFormat.parse(year + "-" + month + "-" + day);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (selectedDate.after(currentDate)) {
                Snackbar.make(binding.rootRelativeLayout, "Нельзя выбрать дату больше текущей.", Snackbar.LENGTH_SHORT).show();
            }
            else {
                DbHelper dbHelper = new DbHelper(getActivity());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String[] columnsWorkDays = {DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ, DbHelper.KEY_IS_EXTRA_PAY, DbHelper.KEY_PAY};
                String[] selectionSectorsArgs = {String.valueOf(year), String.valueOf(month), String.valueOf(day)};
                Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columnsWorkDays, DbHelper.KEY_YEAR + " = ? AND " + DbHelper.KEY_MONTH + " = ? AND " + DbHelper.KEY_DAY + " = ?", selectionSectorsArgs, null, null, null);
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

                    Bundle bundle = new Bundle();
                    bundle.putInt("year", year);
                    bundle.putInt("month", month);
                    bundle.putInt("day", day);
                    bundle.putInt("selectionOs", selectionOs);
                    bundle.putInt("allocationOs", allocationOs);
                    bundle.putInt("selectionMez", selectionMez);
                    bundle.putInt("allocationMez", allocationMez);
                    bundle.putInt("isExtraDay", isExtraPay);
                    bundle.putDouble("pay", pay);
                    FragmentManager manager = getParentFragmentManager();
                    EditPicsDialogFragment editPicsDialogFragment = new EditPicsDialogFragment();
                    editPicsDialogFragment.setArguments(bundle);
                    editPicsDialogFragment.show(manager, "editPicsDialogFragment");
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("year", year);
                    bundle.putInt("month", month);
                    bundle.putInt("day", day);
                    bundle.putInt("selectionOs", 0);
                    bundle.putInt("allocationOs", 0);
                    bundle.putInt("selectionMez", 0);
                    bundle.putInt("allocationMez", 0);
                    bundle.putInt("isExtraDay", 0);
                    bundle.putDouble("pay", 0);
                    FragmentManager manager = getParentFragmentManager();
                    AddPicksDialogFragment addPicksDialogFragment = new AddPicksDialogFragment();
                    addPicksDialogFragment.setArguments(bundle);
                    addPicksDialogFragment.show(manager, "addPicksDialog");
                }
                cursor.close();
                dbHelper.close();
                db.close();
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