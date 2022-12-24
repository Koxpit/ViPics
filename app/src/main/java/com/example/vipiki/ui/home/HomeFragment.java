package com.example.vipiki.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private int picsSelection = 0, picsAllocation = 0;
    private int needPicsAllocation = 50000, needPicsSelection = 5000;
    private String post, schedule, sector;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        try {

        picsAllocation = picsSelection = 0;


            loadNeedPics();
            loadCurrentPics();

        float currentAllocationPercent = ((float)picsAllocation/ needPicsAllocation) * 100.0f;
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

        }
        catch (Exception e) {
            Snackbar.make(binding.homeConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        return root;
    }

    private void loadCurrentPics() {
        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = { DbHelper.KEY_SELECTION_OS, DbHelper.KEY_ALLOCATION_OS, DbHelper.KEY_SELECTION_MEZ, DbHelper.KEY_ALLOCATION_MEZ };
        Cursor cursor = db.query(DbHelper.TABLE_WORKDAYS, columns, DbHelper.KEY_MONTH + " = ?", new String[] {String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)}, null, null, null);
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

    private void loadNeedPics() {
        try {
            DbHelper dbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            SharedPreferences settings = getContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE);
            post = dbHelper.getUserPost(settings);
            int sector_id = dbHelper.getSectorId(settings, db);
            int schedule_id = dbHelper.getScheduleId(settings, db);

            if (Objects.equals(post, "Отборщик мезонина") || Objects.equals(post, "Размещенец мезонина")) {
                int post_selector_mez = dbHelper.getPostId("Отборщик мезонина", db);
                int post_allocator_mez = dbHelper.getPostId("Размещенец мезонина", db);

                needPicsSelection = dbHelper.getMonthNorm(db, post_selector_mez, sector_id, schedule_id);
                needPicsAllocation = dbHelper.getMonthNorm(db, post_allocator_mez, sector_id, schedule_id);
            } else if (Objects.equals(post, "Отборщик основы") || Objects.equals(post, "Размещенец основы")) {
                int post_selector_os = dbHelper.getPostId("Отборщик основы", db);
                int post_allocator_os = dbHelper.getPostId("Размещенец основы", db);

                needPicsSelection = dbHelper.getMonthNorm(db, post_selector_os, sector_id, schedule_id);
                needPicsAllocation = dbHelper.getMonthNorm(db, post_allocator_os, sector_id, schedule_id);
            }
            else {
                needPicsSelection = 5000;
                needPicsAllocation = 50000;
            }

            //ConstraintLayout layout = binding.homeConstraintLayout;

            //Snackbar.make(layout, "Сектор: " + sector_id + ", График " + schedule_id + ", Отбор " + needPicsSelection + ", Размещение " + needPicsAllocation, Snackbar.LENGTH_LONG).show();
        }    catch (Exception e) {
            //Snackbar.make(binding.homeConstraintLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}