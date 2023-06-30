package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.eme22.citasApp.R;
import com.eme22.citasApp.databinding.ActivityLoginBinding;
import com.eme22.citasApp.model.pojo.User;
import com.eme22.citasApp.viewmodel.LoginViewModel;
import com.eme22.citasApp.viewmodel.MainViewModel;

import org.apache.commons.codec.binary.Base64;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    private static final String PREF_NAME = "userPrefs";
    private static final String PREFS_LOGIN_USER= "user";
    private static final String PREFS_LOGIN_PASSWORD= "passwordHash";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        //Create a preference file
        settings = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this));

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(binding.getRoot());

        initListeners();

        String userName = settings.getString(PREFS_LOGIN_USER, null);
        String password = settings.getString(PREFS_LOGIN_PASSWORD, null);

        if (userName != null && password != null ) {

            //password = new String(Base64.decodeBase64(password.getBytes()));
            binding.loginDni.getEditText().setText(userName);
            binding.loginPassword.getEditText().setText(password);
            binding.recordarme.setChecked(true);

        }

        //splashScreen.setKeepOnScreenCondition(() -> true);

    }

    private void initListeners() {

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }

            System.out.println("LFS: "+loginFormState);

            binding.login.setEnabled(loginFormState.isDataValid());
            binding.loginDni.setError(loginFormState.getUsernameError() == null ? null : getString(loginFormState.getUsernameError()));
            binding.loginPassword.setError(loginFormState.getPasswordError() == null ? null : getString(loginFormState.getPasswordError()));
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {

            if (loginResult == null) {
                return;
            }

            this.runOnUiThread(() -> binding.loading.setVisibility(View.GONE));


            if (loginResult.getError() != null) {

                if (loginResult.getError() == 1) {
                    showLoginFailed("Usuario No encontrado");
                    return;
                }

                showLoginFailed();
                return;

            }
            if (loginResult.getSuccess() != null) {
                goMainActivity(loginResult.getSuccess());
            }


        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(binding.loginDni.getEditText().getText().toString(),
                        binding.loginPassword.getEditText().getText().toString());
            }
        };
        binding.loginDni.getEditText().addTextChangedListener(afterTextChangedListener);
        binding.loginPassword.getEditText().addTextChangedListener(afterTextChangedListener);
        binding.loginPassword.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                this.runOnUiThread(() -> binding.getRoot().post(() -> {
                    binding.loading.setVisibility(View.GONE);
                    binding.loading.setVisibility(View.VISIBLE);
                }));

                binding.login.setEnabled(false);
                loginViewModel.login(binding.loginDni.getEditText().getText().toString(),
                        binding.loginPassword.getEditText().getText().toString());
            }
            return false;
        });

        binding.login.setOnClickListener(v -> {
            v.setEnabled(false);

            if (binding.recordarme.isChecked()) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREFS_LOGIN_USER, binding.loginDni.getEditText().getText().toString());
                editor.putString(PREFS_LOGIN_PASSWORD, binding.loginPassword.getEditText().getText().toString());
                editor.apply();
            } else {
                SharedPreferences.Editor editor = settings.edit();
                editor.remove(PREFS_LOGIN_USER);
                editor.remove(PREFS_LOGIN_PASSWORD);
            }

            this.runOnUiThread(() -> binding.getRoot().post(() -> {
                binding.loading.setVisibility(View.GONE);
                binding.loading.setVisibility(View.VISIBLE);
            }));
            loginViewModel.login(binding.loginDni.getEditText().getText().toString(),
                    binding.loginPassword.getEditText().getText().toString());
        });

        binding.forgetPassword.setOnClickListener(v -> goRecoverPasswordActivity());

    }

    private void goMainActivity(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_USER, user);
        startActivity(intent);
        finish();
    }

    private void goRecoverPasswordActivity() {
        Intent intent = new Intent(this, RecoverPasswordActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(String message) {

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    this.runOnUiThread(() -> binding.getRoot().post(() -> {
                        binding.loading.setVisibility(View.GONE);
                    }));
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showLoginFailed() {
        showLoginFailed("La contraseña que has ingresado es incorrecta");
    }


}