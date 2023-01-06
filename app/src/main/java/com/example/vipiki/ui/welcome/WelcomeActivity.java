package com.example.vipiki.ui.welcome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vipiki.messages.errors.ErrorHandler;
import com.example.vipiki.ui.loading.LoadingActivity;
import com.example.vipiki.ui.main.MainActivity;
import com.example.vipiki.R;
import com.example.vipiki.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {
    private WelcomeViewModel welcomeViewModel;
    private TextView welcomeMessage, agreementMessage;
    private String post, schedule, sector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeViewModel = new ViewModelProvider(this, new WelcomeViewModelFactory(this)).get(WelcomeViewModel.class);
        tryToLogIn();

        welcomeMessage = findViewById(R.id.welcomeMessage);
        agreementMessage = findViewById(R.id.agreementMessage);
        Button register_button = findViewById(R.id.register_button);
        Button auth_button = findViewById(R.id.auth_button);

        setWelcomeMessage();
        register_button.setOnClickListener(view -> showRegisterWindow());
        auth_button.setOnClickListener(view -> showAuthWindow());
    }

    private void tryToLogIn() {
        if (welcomeViewModel.isAlreadyAuthUser()) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
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
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (welcomeViewModel.authEntryIsEmpty(email, password)) {
                ErrorHandler.sendAuthEntryError(WelcomeActivity.this);
                return;
            }
            if (welcomeViewModel.passwordIsIncorrect(password)) {
                ErrorHandler.sendIncorrectPasswordError(WelcomeActivity.this);
                return;
            }

            startLoadActivity();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authTask -> {
                if (authTask.isSuccessful()) {
                    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                    String UID = welcomeViewModel.getUID();

                    users.child(UID).get().addOnCompleteListener(getTask -> {
                        if (getTask.isSuccessful()) {
                            User user = getTask.getResult().getValue(User.class);
                            welcomeViewModel.setUserSettings(user, UID, !welcomeViewModel.userIsEmpty());

                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        }
                    });
                } else
                    ErrorHandler.sendAuthError(WelcomeActivity.this);

                dialogInterface.dismiss();
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

        EditText nameEditText = register_view.findViewById(R.id.editTextUserName);
        EditText emailEditText = register_view.findViewById(R.id.editTextUserEmail);
        EditText passwordEditText = register_view.findViewById(R.id.editTextUserPassword);

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.setPositiveButton("ОК", (dialogInterface, i) -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (welcomeViewModel.regEntryIsEmpty(name, email, post, sector, schedule, password)) {
                ErrorHandler.sendEmptyEntryError(WelcomeActivity.this);
                return;
            }
            if (welcomeViewModel.passwordIsIncorrect(password)) {
                ErrorHandler.sendIncorrectPasswordError(WelcomeActivity.this);
                return;
            }

            startLoadActivity();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(regTask -> {
                if (regTask.isSuccessful()) {
                    if (welcomeViewModel.userIsEmpty())
                        return;

                    String UID  = welcomeViewModel.getUID();
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setPost(post);
                    newUser.setSector(sector);
                    newUser.setSchedule(schedule);
                    newUser.setEmail(email);

                    users.child(UID).setValue(newUser).addOnCompleteListener(addUserTask -> {
                        if (addUserTask.isSuccessful()) {
                            welcomeViewModel.setUserSettings(newUser, UID, !welcomeViewModel.userIsEmpty());
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        }
                        else
                            ErrorHandler.sendAddUserError(WelcomeActivity.this);
                    });
                } else
                    ErrorHandler.sendRegError(WelcomeActivity.this);

                dialogInterface.dismiss();
            });
        });

        dialog.show();
    }

    private void startLoadActivity() {
        Intent loadIntent = new Intent(this, LoadingActivity.class);
        startActivity(loadIntent);
    }

    private void initSpinners(View registerView) {
        initPostsSpinner(registerView.findViewById(R.id.spinnerPosts));
        initSchedulesSpinner(registerView.findViewById(R.id.spinnerSchedules));
        initSectorsSpinner(registerView.findViewById(R.id.spinnerSectors));
    }

    private void initPostsSpinner(Spinner spinnerPosts) {
        ArrayAdapter<String> adapterPosts = welcomeViewModel.getAdapterPosts();
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

    private void initSectorsSpinner(Spinner spinnerSectors) {
        ArrayAdapter<String> adapterSectors = welcomeViewModel.getAdapterSectors();
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

    private void initSchedulesSpinner(Spinner spinnerSchedules) {
        ArrayAdapter<String> adapterSchedules = welcomeViewModel.getAdapterSchedules();
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