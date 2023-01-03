package com.example.vipiki.ui.welcome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vipiki.ui.main.MainActivity;
import com.example.vipiki.R;
import com.example.vipiki.models.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {
    private WelcomeViewModel welcomeViewModel;
    private TextView welcomeMessage, agreementMessage;
    private RelativeLayout welcome_relativeLayout;
    private String post, schedule, sector;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d("CLEAR_VM", "Activity Welcome created");

        welcomeViewModel = new ViewModelProvider(this, new WelcomeViewModelFactory(this, getSharedPreferences("app_settings", Context.MODE_PRIVATE))).get(WelcomeViewModel.class);
        if (welcomeViewModel.isAlreadyAuthUser()) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }

        welcomeMessage = findViewById(R.id.welcomeMessage);
        agreementMessage = findViewById(R.id.agreementMessage);
        welcome_relativeLayout = findViewById(R.id.welcome_relativeLayout);
        progressBar = findViewById(R.id.progressBar);
        Button register_button = findViewById(R.id.register_button);
        Button auth_button = findViewById(R.id.auth_button);

        try {
            setWelcomeMessage();
            register_button.setOnClickListener(view -> showRegisterWindow());
            auth_button.setOnClickListener(view -> showAuthWindow());
        } catch (Exception e) {
            Snackbar.make(welcome_relativeLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setWelcomeMessage() {
        String postulateTitle = welcomeViewModel.getPostulateTitle();

        if (Objects.equals(postulateTitle, "Команда")) {
            welcomeMessage.setText(postulateTitle);
            agreementMessage.setText("Быть дружной и амбициозной командой, любящей свое дело!");
        }
        else if (Objects.equals(postulateTitle, "Клиент")) {
            welcomeMessage.setText(postulateTitle);
            agreementMessage.setText("Наша цель - восхищать клиента искренним сервисом!");
        }
        else if (Objects.equals(postulateTitle, "Эффективность")) {
            welcomeMessage.setText(postulateTitle);
            agreementMessage.setText("Быть самой прибыльной и быстрорастущей компанией!");
        }
    }

    private void showAuthWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Авторизация");

        LayoutInflater inflater = LayoutInflater.from(this);
        View auth_view = inflater.inflate(R.layout.activity_auth, null);
        dialog.setView(auth_view);

        EditText emailEditText = auth_view.findViewById(R.id.editTextUserEmail);
        EditText passwordEditText = auth_view.findViewById(R.id.editTextUserPassword);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Войти", (dialogInterface, i) -> {
            try {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (welcomeViewModel.authEntryIsEmpty(email, password)) {
                    sendMessageAuthEntryIsEmpty(welcome_relativeLayout);
                    return;
                }
                if (welcomeViewModel.passwordIsIncorrect(password)) {
                    sendMessageIncorrectPassword(welcome_relativeLayout);
                    return;
                }

                progressBar.setVisibility(ProgressBar.VISIBLE);
                welcomeViewModel.authUser(email, password);
                CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        if (welcomeViewModel.getAuthStatus()) {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                        }
                        else
                            sendMessageAuthError(welcome_relativeLayout);
                    }
                }.start();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            } catch (Exception e) {
                sendAuthError(welcome_relativeLayout, e);
            }
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

        EditText nameEditText = register_view.findViewById(R.id.editTextUserName);
        EditText emailEditText = register_view.findViewById(R.id.editTextUserEmail);
        EditText passwordEditText = register_view.findViewById(R.id.editTextUserPassword);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("ОК", (dialogInterface, i) -> {
            try {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (welcomeViewModel.regEntryIsEmpty(name, email, post, sector, schedule, password)) {
                    sendMessageRegEntryIsEmpty(welcome_relativeLayout);
                    return;
                }
                if (welcomeViewModel.passwordIsIncorrect(password)) {
                    sendMessageIncorrectPassword(welcome_relativeLayout);
                    return;
                }

                User newUser = new User();
                newUser.setName(name);
                newUser.setPost(post);
                newUser.setSector(sector);
                newUser.setSchedule(schedule);
                newUser.setEmail(email);

                progressBar.setVisibility(ProgressBar.VISIBLE);
                welcomeViewModel.registerUser(newUser, password);
                CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        if (welcomeViewModel.getAuthStatus()) {
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();
                        }
                        else
                            sendMessageRegError(welcome_relativeLayout);
                    }
                }.start();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            } catch (Exception e) {
                sendRegError(welcome_relativeLayout, e);
            }
        });

        dialog.show();
    }

    private void sendMessageAuthError(ViewGroup layout) {
        Snackbar.make(layout, "Ошибка авторизации. Повторите попытку позже.", Snackbar.LENGTH_SHORT).show();
    }

    private void sendMessageRegError(ViewGroup layout) {
        Snackbar.make(layout, "Ошибка регистрации. Повторите попытку позже.", Snackbar.LENGTH_SHORT).show();
    }

    private void sendMessageRegEntryIsEmpty(ViewGroup layout) {
        Snackbar.make(layout, "Не все поля заполнены.", Snackbar.LENGTH_SHORT).show();
    }

    private void sendMessageAuthEntryIsEmpty(ViewGroup layout) {
        Snackbar.make(layout, "Вы не ввели почту или пароль", Snackbar.LENGTH_SHORT).show();
    }

    private void sendMessageIncorrectPassword(ViewGroup layout) {
        Snackbar.make(layout, "Пароль должен содержать хотя бы 6 символов.", Snackbar.LENGTH_SHORT).show();
    }

    private void sendAuthError(ViewGroup layout, Exception e) {
        Snackbar.make(layout, "Ошибка авторизации." + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        Log.d("AUTH_FAIL", e.getMessage());
    }

    private void sendRegError(ViewGroup layout, Exception e) {
        Snackbar.make(layout, "Ошибка. Пользователь не добавлен. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        Log.d("SET_USER_VALUE_FAIL", e.getMessage());
    }

    private void initSpinners(View registerView) {
        initPostsSpinner(registerView);
        initSchedulesSpinner(registerView);
        initSectorsSpinner(registerView);
    }

    private void initPostsSpinner(View register_view) {
        Spinner spinnerPosts = register_view.findViewById(R.id.spinnerPosts);
        List<String> posts = welcomeViewModel.getPosts();

        ArrayAdapter<String> adapterPosts = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, posts);
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

    private void initSectorsSpinner(View registerView) {
        Spinner spinnerSectors = registerView.findViewById(R.id.spinnerSectors);
        List<String> sectors = welcomeViewModel.getSectors();

        ArrayAdapter<String> adapterSectors = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectors);
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

    private void initSchedulesSpinner(View registerView) {
        Spinner spinnerSchedules = registerView.findViewById(R.id.spinnerSchedules);
        List<String> schedules = welcomeViewModel.getSchedules();

        ArrayAdapter<String> adapterSchedules = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schedules);
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

}