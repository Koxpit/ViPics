package com.example.vipiki.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.vipiki.Add_Picks_Activity;

import java.util.Locale;

public class EditPicsDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = 0, month = 0, day = 0;
        int selectionOs = 0;
        int allocationOs = 0;
        int selectionMez = 0;
        int allocationMez = 0;
        int isExtraDay = 0;
        double pay = 0;

        Bundle args = getArguments();
        if (args != null) {
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
            selectionOs = args.getInt("selectionOs");
            allocationOs = args.getInt("allocationOs");
            selectionMez = args.getInt("selectionMez");
            allocationMez = args.getInt("allocationMez");
            isExtraDay = args.getInt("isExtraDay");
            pay = args.getDouble("pay");
        }

        @SuppressLint("DefaultLocale") String date = String.format("%d.%d.%d", year, month, day);
        String title = "Смена в дату " + date + " уже добавлена.";
        String message = "";
        if (isExtraDay == 0) {
            message = "Отбор основа: " + selectionOs + ";\n" + "Размещение основа: " + allocationOs + ";\n"
                    + "Отбор мезонин: " + selectionMez + ";\n" + "Размещение мезонин: " + allocationMez + ".\n" + "Оплата: " + pay;
        }
        else {
            message = "Это доп смена.\nОплата: " + String.format(Locale.ENGLISH, "%(.2f", pay);
        }
        String buttonEditText = "Изменить";
        String buttonBackText = "Назад";

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(buttonEditText, (dialog, id) -> {
            Intent intent=new Intent(getContext(), Add_Picks_Activity.class);
            intent.putExtras(args);
            startActivity(intent);
        });

        builder.setNegativeButton(buttonBackText, (dialog, id) -> dialog.cancel());
        builder.setCancelable(true);

        return builder.create();
    }
}
