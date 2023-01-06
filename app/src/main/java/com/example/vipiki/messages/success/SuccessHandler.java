package com.example.vipiki.messages.success;

import android.content.Context;
import android.widget.Toast;

public class SuccessHandler {
    private static final String SYNC_SUCCESS_MESSAGE = "Синхронизация завершена";
    private static final String RECOVER_SUCCESS_MESSAGE = "Восстановление данных завершено";
    private static final String EMAIL_UPDATE_SUCCESS_MESSAGE = "Почтовый адрес успешно изменен";
    private static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = "Пароль успешно изменен";

    public static void sendSyncSuccessMessage(Context context) {
        Toast.makeText(context, SYNC_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    public static void sendRecoverSuccessMessage(Context context) {
        Toast.makeText(context, RECOVER_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    public static void sendEmailSuccessMessage(Context context) {
        Toast.makeText(context, EMAIL_UPDATE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    public static void sendPasswordSuccessMessage(Context context) {
        Toast.makeText(context, PASSWORD_UPDATE_SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
    }
}
