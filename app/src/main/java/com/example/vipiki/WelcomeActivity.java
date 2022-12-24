package com.example.vipiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vipiki.database.DbHelper;
import com.example.vipiki.models.Post;
import com.example.vipiki.models.Schedule;
import com.example.vipiki.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    Button register_button, auth_button;
    TextView welcomeMessage, agreementMessage;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private RelativeLayout welcome_relativeLayout;
    private SharedPreferences settings;
    private String post, schedule, sector;
    private String[] postulatesTitles = new String[] {"Команда", "Клиент", "Эффективность"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        agreementMessage = findViewById(R.id.agreementMessage);
        settings = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        setWelcomeMessage();

        register_button = findViewById(R.id.register_button);
        auth_button = findViewById(R.id.auth_button);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("users");
        welcome_relativeLayout = findViewById(R.id.welcome_relativeLayout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String UID = user.getUid();
            if (settings.contains(UID)) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(UID, false);
                editor.apply();
                if (settings.getBoolean(UID, false)) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        }

        try {
            register_button.setOnClickListener(view -> showRegisterWindow());
            auth_button.setOnClickListener(view -> showAuthWindow());
        } catch (Exception e) {
            Snackbar.make(welcome_relativeLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setWelcomeMessage() {
        int postulateId = (int)Math.floor(Math.random() * postulatesTitles.length);

        if (Objects.equals(postulatesTitles[postulateId], "Команда")) {
            welcomeMessage.setText(postulatesTitles[postulateId]);
            agreementMessage.setText("Быть дружной и амбициозной командой, любящей свое дело!");
        }
        else if (Objects.equals(postulatesTitles[postulateId], "Клиент")) {
            welcomeMessage.setText(postulatesTitles[postulateId]);
            agreementMessage.setText("Наша цель - восхищать клиента искренним сервисом!");
        }
        else if (Objects.equals(postulatesTitles[postulateId], "Эффективность")) {
            welcomeMessage.setText(postulatesTitles[postulateId]);
            agreementMessage.setText("Быть самой прибыльной и быстрорастущей компанией!");
        }
    }

    private void showAuthWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Авторизация");

        LayoutInflater inflater = LayoutInflater.from(this);
        View auth_view = inflater.inflate(R.layout.activity_auth, null);
        dialog.setView(auth_view);

        EditText email = auth_view.findViewById(R.id.editTextUserEmail);
        EditText password = auth_view.findViewById(R.id.editTextUserPassword);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Войти", (dialogInterface, i) -> {
            if (authEntryIsEmpty(email, password))
                return;

            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        try {
                            if (userIsEmpty())
                                return;

                            String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean(UID, true);
                            editor.apply();

                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                        } catch (Exception e) {
                            Snackbar.make(welcome_relativeLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        sendAuthError(welcome_relativeLayout, e);
                    });
        });

        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_view = inflater.inflate(R.layout.activity_register, null);
        initSpinners(register_view);
        dialog.setView(register_view);

        EditText name = register_view.findViewById(R.id.editTextUserName);
        EditText email = register_view.findViewById(R.id.editTextUserEmail);
        EditText password = register_view.findViewById(R.id.editTextUserPassword);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("ОК", (dialogInterface, i) -> {
            if (regEntryIsEmpty(name, email, post, schedule, password))
                return;

            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        if (userIsEmpty())
                            return;

                        String UID  = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        User newUser = new User();
                        newUser.setName(name.getText().toString());
                        newUser.setPost(post);
                        newUser.setSector(sector);
                        newUser.setSchedule(schedule);
                        newUser.setEmail(email.getText().toString());

                        users.child(UID).setValue(newUser)
                                .addOnSuccessListener(unused -> {
                                    DbHelper dbHelper = new DbHelper(this);
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_WORKDAYS);
                                    db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_SECTORS);
                                    db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_SCHEDULES);
                                    db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_POSTS);
                                    db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_TAXES);
                                    dbHelper.onCreate(db);
                                    dbHelper.close();
                                    db.close();

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean(UID, true);
                                    editor.putString("name", name.getText().toString());
                                    editor.putString("sector", sector);
                                    editor.putString("schedule", schedule);
                                    editor.putString("post", post);
                                    editor.apply();

                                    Snackbar.make(welcome_relativeLayout, "Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    sendRegError(welcome_relativeLayout, e);
                                });
                    }).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("REG_COMPLETE_SUCCESS", "createUserWithEmail:success");
                        } else {
                            sendRegError(welcome_relativeLayout, task);
                        }
                    });
        });

        dialog.show();
    }

    private void sendAuthError(ViewGroup layout, Exception e) {
        Snackbar.make(layout, "Ошибка авторизации." + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        Log.d("AUTH_FAIL", e.getMessage());
    }

    private void sendRegError(ViewGroup layout, Task<AuthResult> task) {
        Snackbar.make(layout, "Ошибка: регистрация не пройдена." + task.getException(), Snackbar.LENGTH_SHORT).show();
        Log.d("REG_COMPLETE_FAIL", "createUserWithEmail:failure" + task.getException(), task.getException());
    }

    private void sendRegError(ViewGroup layout, Exception e) {
        Snackbar.make(layout, "Ошибка. Пользователь не добавлен. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        Log.d("SET_USER_VALUE_FAIL", e.getMessage());
    }

    private void initSpinners(View register_view) {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        initPostsSpinner(register_view, db);
        initSectorsSpinner(register_view, db);
        initSchedulesSpinner(register_view, db);
        db.close();
    }

    private void initPostsSpinner(View register_view, SQLiteDatabase db) {
        List<String> posts = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_POSTS, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
                String value = cursor.getString(columnIndex);
                posts.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        Spinner spinnerPosts = register_view.findViewById(R.id.spinnerPosts);
        ArrayAdapter<String> adapterPosts = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, posts);
        adapterPosts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosts.setAdapter(adapterPosts);
        AdapterView.OnItemSelectedListener itemSelectedPostListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                post = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerPosts.setOnItemSelectedListener(itemSelectedPostListener);
    }

    private void initSectorsSpinner(View register_view, SQLiteDatabase db) {
        List<String> sectors = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_SECTORS, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
                String value = cursor.getString(columnIndex);
                sectors.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        Spinner spinnerSectors = register_view.findViewById(R.id.spinnerSectors);
        ArrayAdapter<String> adapterSectors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, sectors);
        adapterSectors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSectors.setAdapter(adapterSectors);
        AdapterView.OnItemSelectedListener itemSelectedSectorListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sector = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerSectors.setOnItemSelectedListener(itemSelectedSectorListener);
    }

    private void initSchedulesSpinner(View register_view, SQLiteDatabase db) {
        List<String> schedules = new ArrayList<>();

        Cursor cursor = db.query(DbHelper.TABLE_SCHEDULES, new String[] {DbHelper.KEY_NAME}, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                int columnIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
                String value = cursor.getString(columnIndex);
                schedules.add(value);
            } while(cursor.moveToNext());
        }
        cursor.close();

        Spinner spinnerSchedules = register_view.findViewById(R.id.spinnerSchedules);
        ArrayAdapter<String> adapterSchedules = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, schedules);
        adapterSchedules.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchedules.setAdapter(adapterSchedules);
        AdapterView.OnItemSelectedListener itemSelectedScheduleListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schedule = (String)adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerSchedules.setOnItemSelectedListener(itemSelectedScheduleListener);
    }

    private boolean userIsEmpty() {
        boolean userIsEmpty = false;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
            userIsEmpty = true;

        return userIsEmpty;
    }

    private boolean authEntryIsEmpty(EditText email, EditText password) {
        boolean entryIsEmpty = false;

        if (TextUtils.isEmpty(email.getText().toString())) {
            Snackbar.make(welcome_relativeLayout, "Вы не ввели почту", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
        }
        if (password.getText().toString().length() < 6) {
            Snackbar.make(welcome_relativeLayout, "Пароль должен содержать хотя бы 6 символов", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
        }

        return entryIsEmpty;
    }

    private boolean regEntryIsEmpty(EditText name, EditText email, String post, String schedule, EditText password) {
        boolean entryIsEmpty = false;

        if (TextUtils.isEmpty(name.getText().toString())) {
            Snackbar.make(welcome_relativeLayout, "Вы не ввели имя", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            Snackbar.make(welcome_relativeLayout, "Вы не ввели почту", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }
        if (password.getText().toString().length() < 6) {
            Snackbar.make(welcome_relativeLayout, "Введите пароль хотя бы из 6 символов", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }
        if (TextUtils.isEmpty(post)) {
            Snackbar.make(welcome_relativeLayout, "Вы не выбрали должность", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }
        if (TextUtils.isEmpty(sector)) {
            Snackbar.make(welcome_relativeLayout, "Вы не выбрали сектор", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }
        if (TextUtils.isEmpty(schedule)) {
            Snackbar.make(welcome_relativeLayout, "Вы не выбрали график работы", Snackbar.LENGTH_SHORT).show();
            entryIsEmpty = true;
            return entryIsEmpty;
        }

        return entryIsEmpty;
    }
}