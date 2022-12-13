package com.example.vipiki.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.MainActivity;
import com.example.vipiki.database.DbHelper;
import com.example.vipiki.databinding.FragmentHomeBinding;
import com.example.vipiki.models.User;
import com.example.vipiki.models.WorkDay;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private int picsSelection = 0, picsAllocation = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        picsAllocation = picsSelection = 0;
        loadPics();

        int needPicsAllocation = 58400;
        float currentAllocationPercent = ((float)picsAllocation/ needPicsAllocation) * 100.0f;
        int needPicsSelection = 8320;
        float currentSelectionPercent = ((float)picsSelection/ needPicsSelection) * 100.0f;

        PieChart pieChartSelection = binding.pieChartSelection;
        PieChart pieChartAllocation = binding.pieChartAllocation;

        ArrayList<PieEntry> pieSelectionEntries = new ArrayList<>();
        ArrayList<PieEntry> pieAllocationEntries = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF000000"));
        colors.add(Color.parseColor("#d80c00"));

        PieEntry pieSelectionNeededData = new PieEntry(needPicsSelection - picsSelection);
        PieEntry pieSelectionCurrentData = new PieEntry(picsSelection);
        pieSelectionEntries.add(pieSelectionNeededData);
        pieSelectionEntries.add(pieSelectionCurrentData);

        PieDataSet pieSelectionDataSet = new PieDataSet(pieSelectionEntries, "Отбор");
        pieSelectionDataSet.setValueTextColor(Color.WHITE);
        pieSelectionDataSet.setValueTextSize(14);
        pieSelectionDataSet.setColors(colors);
        pieSelectionDataSet.setDrawValues(true);
        pieChartSelection.setData(new PieData(pieSelectionDataSet));
        pieChartSelection.animateXY(2500, 2500);
        pieChartSelection.setCenterText("Отбор (" + String.format(new Locale("ru"), "%(.0f", currentSelectionPercent) + "%)");
        pieChartSelection.getDescription().setEnabled(false);
        pieChartSelection.getLegend().setEnabled(false);


        PieEntry pieAllcationNeededData = new PieEntry(needPicsAllocation - picsAllocation);
        PieEntry pieAllcationCurrentData = new PieEntry(picsAllocation);
        pieAllocationEntries.add(pieAllcationNeededData);
        pieAllocationEntries.add(pieAllcationCurrentData);

        PieDataSet pieAllocationDataSet = new PieDataSet(pieAllocationEntries, "Размещение");
        pieAllocationDataSet.setValueTextColor(Color.WHITE);
        pieAllocationDataSet.setValueTextSize(14);
        pieAllocationDataSet.setColors(colors);
        pieAllocationDataSet.setDrawValues(true);
        pieChartAllocation.setData(new PieData(pieAllocationDataSet));
        pieChartAllocation.animateXY(2500, 2500);
        pieChartAllocation.setCenterText("Размещение (" + String.format(new Locale("ru"), "%(.0f", currentAllocationPercent) + "%)");
        pieChartAllocation.getDescription().setEnabled(false);
        pieChartAllocation.getLegend().setEnabled(false);

        return root;
    }

    private void loadPics() {
        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = { DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ };
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columns, null, null, null, null, null);
        if (cursor.moveToNext()) {
            int selectionOsIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_OS);
            int allocationOsIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_OS);
            int selectionMezIndex = cursor.getColumnIndex(DbHelper.KEY_SELECTION_MEZ);
            int allocationMezIndex = cursor.getColumnIndex(DbHelper.KEY_ALLOCATION_MEZ);

            do {
                picsSelection += cursor.getInt(selectionOsIndex) + cursor.getInt(selectionMezIndex);
                picsAllocation += cursor.getInt(allocationOsIndex) + cursor.getInt(allocationMezIndex);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}