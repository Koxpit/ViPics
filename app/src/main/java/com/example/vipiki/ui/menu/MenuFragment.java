package com.example.vipiki.ui.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.R;
import com.example.vipiki.ui.main.MainActivity;
import com.example.vipiki.ui.settings.SettingsActivity;
import com.example.vipiki.ui.statistic.StatisticActivity;
import com.example.vipiki.databinding.FragmentMenuBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private MenuViewModel menuViewModel;
    private ImageView userImageView;
    private ActivityResultLauncher<Intent> launchGalleryActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                new ViewModelProvider(this, new MenuViewModelFactory(getContext(), getContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button settingsButton = (Button) binding.settingsButton;
        Button statisticButton = (Button) binding.statisticButton;
        Button exitButton = (Button) binding.exitButton;
        TextView textViewAverageMonthSalary = (TextView) binding.averageMonthSalaryTextView;
        TextView textViewUserName = (TextView) binding.userNameTextView;
        textViewAverageMonthSalary.setText(String.format(Locale.ENGLISH, "%(.2f", menuViewModel.getAvgMonthSalary()));
        textViewUserName.setText(menuViewModel.getUserName());
        userImageView = (ImageView) binding.avatarImageView;
        setGalleryLauncher();
        try {
            menuViewModel.setUserImage(userImageView);
        } catch(Exception e) {
            Toast.makeText(getContext(), "Произошла ошибка при загрузке изображения", Toast.LENGTH_SHORT).show();
        }

        userImageView.setOnClickListener(v -> menuViewModel.openGallery(launchGalleryActivity));

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
            menuViewModel.signOut();
        });

        return root;
    }

    private void setGalleryLauncher() {
        launchGalleryActivity
                = registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                result -> {
                    if (result.getResultCode()
                            == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null
                                && data.getData() != null) {
                            Uri selectedImageUri = data.getData();
                            Bitmap selectedImageBitmap = null;
                            try {
                                selectedImageBitmap
                                        = MediaStore.Images.Media.getBitmap(
                                        getContext().getContentResolver(),
                                        selectedImageUri);

                                userImageView.setImageBitmap(
                                        selectedImageBitmap);

                                menuViewModel.saveImage(selectedImageBitmap);
                            }
                            catch (IOException e) {
                                Snackbar.make(binding.rootRelativeLayout, "Произошла ошибка при загрузке изображения.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}