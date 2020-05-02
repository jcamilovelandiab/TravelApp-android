package com.app.travelapp.ui.auth.sign_up;

import androidx.annotation.Nullable;


/**
 * This class was made to validate the sign up form.
 * If the form is invalid the registration will not proceed.
 */
class SignUpFormState {

    @Nullable
    private Integer full_nameError;

    @Nullable
    private Integer usernameError;

    @Nullable
    private Integer emailError;

    @Nullable
    private Integer passwordError;

    @Nullable
    private Integer confirm_passwordError;
    private boolean isDataValid;

    SignUpFormState(@Nullable Integer full_nameError,
                    @Nullable Integer usernameError,
                    @Nullable Integer emailError,
                    @Nullable Integer passwordError,
                    @Nullable Integer confirm_passwordError) {
        this.full_nameError = full_nameError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirm_passwordError = confirm_passwordError;
        this.isDataValid = false;
    }

    SignUpFormState(boolean isDataValid){
        this.full_nameError = null;
        this.usernameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirm_passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getFull_nameError() {
        return full_nameError;
    }

    void setFull_nameError(@Nullable Integer full_nameError) {
        this.full_nameError = full_nameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    void setEmailError(@Nullable Integer emailError) {
        this.emailError = emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    void setPasswordError(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
    }

    @Nullable
    Integer getConfirm_passwordError() {
        return confirm_passwordError;
    }

    void setConfirm_passwordError(@Nullable Integer confirm_passwordError) {
        this.confirm_passwordError = confirm_passwordError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(@Nullable Integer usernameError) {
        this.usernameError = usernameError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    void setDataValid(boolean dataValid) {
        isDataValid = dataValid;
    }
}
