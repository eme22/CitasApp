package com.eme22.citasApp.model.login;

import androidx.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data validation state of the login form.
 */
@Data
@AllArgsConstructor
public class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

}