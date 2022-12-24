package com.example.vipiki.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.MainActivity;
import com.example.vipiki.R;
import com.example.vipiki.SettingsActivity;
import com.example.vipiki.StatisticActivity;
import com.example.vipiki.WelcomeActivity;
import com.example.vipiki.database.DbHelper;
import com.example.vipiki.databinding.FragmentMenuBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel menuViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DbHelper dbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Button settingsButton = (Button) binding.settingsButton;
        Button statisticButton = (Button) binding.statisticButton;
        Button exitButton = (Button) binding.exitButton;
        TextView textView = (TextView) binding.averageMonthSalaryTextView;
        textView.setText(String.format(Locale.ENGLISH, "%(.2f", dbHelper.getAverageMonthSalary(db)));
        ImageView userImage = (ImageView) binding.avatarImageView;
        userImage.setImageResource(R.mipmap.ic_empty_user);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
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
            FirebaseAuth.getInstance().signOut();
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