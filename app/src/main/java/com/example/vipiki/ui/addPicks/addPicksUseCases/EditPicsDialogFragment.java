package com.example.vipiki.ui.addPicks.addPicksUseCases;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.vipiki.ui.addPicks.Add_Picks_Activity;
import com.example.vipiki.database.DbHelper;

import java.util.Locale;

public class EditPicsDialogFragment extends DialogFragment {
    private int year = 0, month = 0, day = 0;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
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
        String title = "Результат смены " + date;
        String message = "";
        if (isExtraDay == 0) {
            message = "Отбор основа: " + selectionOs + ";\n" + "Размещение основа: " + allocationOs + ";\n"
                    + "Отбор мезонин: " + selectionMez + ";\n" + "Размещение мезонин: " + allocationMez + ".\n" + "Минимальная оплата: "
                    + String.format(Locale.ENGLISH, "%(.2f", pay) + "руб.";
        }
        else {
            message = "Это доп смена.\nОплата: " + String.format(Locale.ENGLISH, "%(.2f", pay);
        }
        String buttonEditText = "Изменить";
        String buttonDeleteText = "Удалить";
        String buttonBackText = "Назад";

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(buttonEditText, (dialog, id) -> {
            Intent intent=new Intent(getContext(), Add_Picks_Activity.class);
            intent.putExtras(args);
            startActivity(intent);
        });

        builder.setNeutralButton(buttonBackText, (dialog, i) -> dialog.cancel());

        builder.setNegativeButton(buttonDeleteText, (dialog, id) -> {
            DbHelper dbHelper = new DbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            dbHelper.deleteWorkDay(db, year, month, day);
            dbHelper.close();
            db.close();
            dialog.dismiss();
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
