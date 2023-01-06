package com.example.vipiki.ui.addPicks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class EditPicsDialogFragment extends DialogFragment {
    private int year = 0, month = 0, day = 0;
    private ChangePicksViewModel changePicksViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        changePicksViewModel = new ViewModelProvider(this, new ChangePicksViewModelFactory(getActivity())).get(ChangePicksViewModel.class);
        String title = "Нет информации", message = "Нет информации";

        Bundle args = getArguments();
        if (args != null) {
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
            int isExtraDay = args.getInt("isExtraDay");
            double pay = args.getDouble("pay");

            title = "Результат смены " + changePicksViewModel.getDate(year, month, day);

            if (isExtraDay == 0)
                message = changePicksViewModel.getMessageWorkDay(args);
            else
                message = changePicksViewModel.getMessageExtraDay(pay);
        }

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Изменить", (dialog, id) -> {
            Intent intent=new Intent(getContext(), Add_Picks_Activity.class);
            intent.putExtras(args);
            startActivity(intent);
        });

        builder.setNeutralButton("Назад", (dialog, i) -> dialog.cancel());

        builder.setNegativeButton("Удалить", (dialog, id) -> {
            changePicksViewModel.deleteWorkDay(day, month, year);
            dialog.dismiss();
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
