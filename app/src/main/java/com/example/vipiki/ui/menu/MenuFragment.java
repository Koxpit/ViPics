package com.example.vipiki.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.MainActivity;
import com.example.vipiki.StatisticActivity;
import com.example.vipiki.WelcomeActivity;
import com.example.vipiki.databinding.FragmentMenuBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel menuViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button settingsButton = (Button) binding.settingsButton;
        Button statisticButton = (Button) binding.statisticButton;
        Button exitButton = (Button) binding.exitButton;


        settingsButton.setOnClickListener(v -> {

        });

        statisticButton.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(getActivity(), StatisticActivity.class);
                startActivity(intent);
            }
            catch (Exception e) {
                Snackbar.make(binding.rootRelativeLayout, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

        exitButton.setOnClickListener(view -> {
            SharedPreferences settings = getContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            editor.putBoolean(UID, false);
            editor.apply();

            Intent intent = new Intent(getContext(), WelcomeActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}