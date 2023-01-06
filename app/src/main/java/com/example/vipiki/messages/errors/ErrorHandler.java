package com.example.vipiki.messages.errors;

import android.content.Context;

import android.widget.Toast;


public class ErrorHandler {
    private static final String POST_NOT_FOUND_ERROR = "post_not_found";
    private static final String NAME_NOT_FOUND_ERROR = "name_not_found";
    private static final String UID_NOT_FOUND_ERROR = "uid_not_found";
    private static final String SECTOR_NOT_FOUND_ERROR = "sector_not_found";
    private static final String SCHEDULE_NOT_FOUND_ERROR = "schedule_not_found";
    private static final String EMAIL_NOT_FOUND_ERROR = "email_not_found";

    private static final String ADD_USER_ERROR = "Пользователь не добавлен";
    private static final String AUTH_ERROR = "Ошибка авторизации";
    private static final String REG_ERROR = "Ошибка регистрации. Повторите попытку позже";
    private static final String ENTRY_ERROR = "Не все поля заполнены";
    private static final String AUTH_ENTRY_ERROR = "Вы не ввели логин или пароль";
    private static final String INCORRECT_PASSWORD_ERROR = "Пароль должен содержать хотя бы 6 символов";
    private static final String INCORRECT_SELECTED_DATE_ERROR = "Нельзя выбрать дату больше текущей";
    private static final String INCORRECT_DATE_ERROR = "Неверный формат даты";
    private static final String PASSWORDS_MATCH_ERROR = "Пароли не совпадают";
    private static final String PASSWORD_UPDATE_ERROR = "Произошла ошибка при изменении пароля";
    private static final String EMAIL_UPDATE_ERROR = "Произошла ошибка при изменении почты";

    private static final String INIT_CHARTS_ERROR = "Не удалось загрузить графики";

    public static String getPostNotFoundError() {
        return POST_NOT_FOUND_ERROR;
    }

    public static String getNameNotFoundError() {
        return NAME_NOT_FOUND_ERROR;
    }

    public static String getUidNotFoundError() {
        return UID_NOT_FOUND_ERROR;
    }

    public static String getSectorNotFoundError() {
        return SECTOR_NOT_FOUND_ERROR;
    }

    public static String getScheduleNotFoundError() {
        return SCHEDULE_NOT_FOUND_ERROR;
    }

    public static String getEmailNotFoundError() {
        return EMAIL_NOT_FOUND_ERROR;
    }

    public static void sendAuthError(Context context) {
        Toast.makeText(context, AUTH_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendRegError(Context context) {
        Toast.makeText(context, REG_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendAddUserError(Context context) {
        Toast.makeText(context, ADD_USER_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendEmptyEntryError(Context context) {
        Toast.makeText(context, ENTRY_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendAuthEntryError(Context context) {
        Toast.makeText(context, AUTH_ENTRY_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendIncorrectPasswordError(Context context) {
        Toast.makeText(context, INCORRECT_PASSWORD_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendPasswordsMatchError(Context context) {
        Toast.makeText(context, PASSWORDS_MATCH_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendUpdatePasswordError(Context context) {
        Toast.makeText(context, PASSWORD_UPDATE_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendUpdateEmailError(Context context) {
        Toast.makeText(context, EMAIL_UPDATE_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendInitChartsError(Context context) {
        Toast.makeText(context, INIT_CHARTS_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendIncorrectDateError(Context context) {
        Toast.makeText(context, INCORRECT_DATE_ERROR, Toast.LENGTH_SHORT).show();
    }

    public static void sendIncorrectSelectedDateError(Context context) {
        Toast.makeText(context, INCORRECT_SELECTED_DATE_ERROR, Toast.LENGTH_SHORT).show();
    }
}
