package com.eme22.citasApp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.api.Api;
import com.eme22.citasApp.model.api.ApiClient;
import com.eme22.citasApp.model.login.LoginFormState;
import com.eme22.citasApp.model.login.LoginResult;
import com.eme22.citasApp.model.pojo.patients.Patient;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

import lombok.extern.log4j.Log4j2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Log4j2
public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    private final Api api = ApiClient.getSOService();

    public void login(String dni, String password) {

        password = new String(Base64.encodeBase64(password.getBytes(StandardCharsets.UTF_8)));

        String finalPassword = password;
        api.getPatientByDNI(Integer.parseInt(dni)).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Patient> call, @NonNull Response<Patient> response) {

                    if (response.isSuccessful()) {
                        Patient patient = response.body();

                        if (patient != null && finalPassword.equals(patient.getPasswordHash())) {
                            loginResult.setValue(LoginResult.Success(patient));
                            return;

                        }

                        loginResult.setValue(LoginResult.Failed(1));
                        return;
                    }

                    loginResult.setValue(LoginResult.Failed(2));
                }

                @Override
                public void onFailure(@NonNull Call<Patient> call, @NonNull Throwable t) {
                    System.out.println("Failed");
                    t.printStackTrace();
                    loginResult.setValue(LoginResult.Failed(2));
                }
        });

    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, false));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, false));
        } else {
            loginFormState.setValue(new LoginFormState(null, null, true));
        }
    }

    private boolean isUserNameValid(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(username);
        } catch (NumberFormatException ignored) {
            return false;
        }

        return username.length() == 8;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

}