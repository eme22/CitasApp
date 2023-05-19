package com.eme22.citasApp.model.login;

import android.util.Log;

import androidx.annotation.Nullable;

import com.eme22.citasApp.model.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Authentication result : success (user details) or error message.
 */
@Data
@AllArgsConstructor
public class LoginResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    public static LoginResult Success(User success) {
        return new LoginResult(success, null);
    }

    public static LoginResult Failed(Integer errorCode) {
        return new LoginResult(null, errorCode);
    }

}