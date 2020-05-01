package com.app.travelapp.ui.auth;

import androidx.annotation.Nullable;

import com.app.travelapp.ui.auth.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 */
public class AuthResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    public AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    public AuthResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    public LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
