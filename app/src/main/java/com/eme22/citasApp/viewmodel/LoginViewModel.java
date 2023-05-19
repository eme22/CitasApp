package com.eme22.citasApp.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.login.LoginFormState;
import com.eme22.citasApp.model.login.LoginResult;
import com.eme22.citasApp.model.pojo.Patient;
import com.eme22.citasApp.model.pojo.User;
import com.eme22.citasApp.util.StringUtil;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j2;

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

    public void login(String dni, String password) {

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            log.debug("");
        }

        String testPassword = "123456";

        if (dni.equals("75333323")){

            if (StringUtil.encodeToBase64(password).equals(StringUtil.encodeToBase64(testPassword))) {
                loginResult.setValue(LoginResult.Success(generateFakeUser()));
                return;
            }

            loginResult.setValue(LoginResult.Failed(1));

        }



        loginResult.setValue(LoginResult.Failed(2));

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

    private User generateFakeUser() {
        Patient patient = new Patient();
        patient.setHistoryList(new ArrayList<>());
        patient.setDni("75333323");
        patient.setName2("Jesus");
        patient.setName1("Manuel");
        patient.setLastName1("Salvatierra");
        patient.setLastName2("Bejar");
        patient.setAge(22);
        patient.setAddress("Jr. Atahualpa 658 - Callao, Callao");
        return patient;
    }
}