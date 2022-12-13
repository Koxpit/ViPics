package com.example.vipiki.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.vipiki.Add_Picks_Activity;

public class AddPicksDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = 0, month = 0, day = 0;
        Bundle args = getArguments();

        if (args != null) {
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        }

        @SuppressLint("DefaultLocale") String title = String.format("%d.%d.%d", year, month, day);
        String message = "Хотите добавить пики?";
        String buttonYesString = "Да";
        String buttonNoString = "Нет";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(buttonYesString, (dialog, id) -> {
            Intent intent=new Intent(getContext(), Add_Picks_Activity.class);
            intent.putExtras(args);
            startActivity(intent);
        });

        builder.setNegativeButton(buttonNoString, (dialog, id) -> dialog.cancel());
        builder.setCancelable(true);

        return builder.create();
    }
}