package com.example.vipiki.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.databinding.FragmentHomeBinding;
import com.example.vipiki.messages.errors.ErrorHandler;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(getContext())).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            homeViewModel.setCurrentPics();
            homeViewModel.setNeedPics();
            initPieCharts();
        } catch (Exception e) {
            ErrorHandler.sendInitChartsError(getContext());
        }

        return root;
    }

    private void initPieCharts() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF000000"));
        colors.add(Color.parseColor("#d80c00"));

        initAllocationPieChart(colors);
        initSelectionPieChart(colors);
    }

    private void initAllocationPieChart(ArrayList<Integer> colors) {
        float currentAllocationPercent = homeViewModel.getCurrentAllocationPercent();
        PieChart pieChartAllocation = binding.pieChartAllocation;
        ArrayList<PieEntry> pieAllocationEntries = new ArrayList<>();

        PieEntry pieAllocationNeededData = new PieEntry(homeViewModel.getNeedPicsAllocation());
        PieEntry pieAllocationCurrentData = new PieEntry(homeViewModel.getPicsAllocation());
        pieAllocationEntries.add(pieAllocationNeededData);
        pieAllocationEntries.add(pieAllocationCurrentData);

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

    private void initSelectionPieChart(ArrayList<Integer> colors) {
        float currentSelectionPercent = homeViewModel.getCurrentSelectionPercent();
        PieChart pieChartSelection = binding.pieChartSelection;
        ArrayList<PieEntry> pieSelectionEntries = new ArrayList<>();

        PieEntry pieSelectionNeededData = new PieEntry(homeViewModel.getNeedPicsSelection());
        PieEntry pieSelectionCurrentData = new PieEntry(homeViewModel.getPicsSelection());
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}