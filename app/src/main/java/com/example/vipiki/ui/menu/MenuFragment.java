package com.example.vipiki.ui.menu;

import android.app.Activity;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.ui.settings.SettingsActivity;
import com.example.vipiki.ui.statistic.StatisticActivity;
import com.example.vipiki.databinding.FragmentMenuBinding;

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
                new ViewModelProvider(this, new MenuViewModelFactory(requireContext())).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button settingsButton = binding.settingsButton;
        Button statisticButton = binding.statisticButton;
        Button exitButton = binding.exitButton;
        TextView textViewAverageMonthSalary = binding.averageMonthSalaryTextView;
        TextView textViewUserName = binding.userNameTextView;
        textViewAverageMonthSalary.setText(String.format(Locale.ENGLISH, "%(.2f", menuViewModel.getAvgMonthSalary()));
        textViewUserName.setText(menuViewModel.getUserName());
        userImageView = binding.avatarImageView;
        setGalleryLauncher();
        try {
            menuViewModel.setUserImage(userImageView);
        } catch(Exception e) {
            ErrorHandler.sendLoadImageError(getContext());
        }

        userImageView.setOnClickListener(v -> menuViewModel.openGallery(launchGalleryActivity));

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        statisticButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), StatisticActivity.class);
            startActivity(intent);
        });

        exitButton.setOnClickListener(view -> menuViewModel.signOut());

        return root;
    }

    private void setGalleryLauncher() {
        launchGalleryActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            Uri selectedImageUri = data.getData();
                            Bitmap selectedImageBitmap;
                            try {
                                if (getContext() != null) {
                                    selectedImageBitmap
                                            = MediaStore.Images.Media.getBitmap(
                                            getContext().getContentResolver(),
                                            selectedImageUri);

                                    userImageView.setImageBitmap(
                                            selectedImageBitmap);

                                    menuViewModel.saveImage(selectedImageBitmap);
                                }
                            }
                            catch (IOException e) {
                                ErrorHandler.sendUploadImageError(getContext());
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