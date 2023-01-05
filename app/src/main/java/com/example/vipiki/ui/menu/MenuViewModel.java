package com.example.vipiki.ui.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModel;

import com.example.vipiki.R;
import com.example.vipiki.ui.menu.menuUseCases.ChangeAvatarUseCase;
import com.example.vipiki.ui.menu.menuUseCases.GetMenuDataUseCase;
import com.example.vipiki.ui.menu.menuUseCases.SignOutUseCase;

public class MenuViewModel extends ViewModel {
    private final GetMenuDataUseCase getMenuDataUseCase;
    private final SignOutUseCase signOutUseCase;
    private final ChangeAvatarUseCase changeAvatarUseCase;

    public MenuViewModel(GetMenuDataUseCase getMenuDataUseCase, SignOutUseCase signOutUseCase, ChangeAvatarUseCase changeAvatarUseCase) {
        this.getMenuDataUseCase = getMenuDataUseCase;
        this.signOutUseCase = signOutUseCase;
        this.changeAvatarUseCase = changeAvatarUseCase;
    }

    public double getAvgMonthSalary() {
        return getMenuDataUseCase.getAvgMonthSalary();
    }

    public String getUserName() {
        return getMenuDataUseCase.getUserName();
    }

    public void signOut() {
        signOutUseCase.signOut();
    }

    public void openGallery(ActivityResultLauncher<Intent> launchGalleryActivity)
    {
        Intent photoChooserIntent = new Intent();
        photoChooserIntent.setType("image/*");
        photoChooserIntent.setAction(Intent.ACTION_GET_CONTENT);

        launchGalleryActivity.launch(photoChooserIntent);
    }

    public void saveImage(Bitmap userImage) {
        changeAvatarUseCase.saveImage(userImage);
    }

    public void setUserImage(ImageView userImageView) {
        Bitmap userImageBitmap = getUserImage();
        if (userImageBitmap != null)
            userImageView.setImageBitmap(userImageBitmap);
        else
            userImageView.setImageResource(R.mipmap.ic_empty_user);
    }

    private Bitmap getUserImage() {
        return changeAvatarUseCase.getUserImage();
    }
}