package com.app.travelapp.ui.edit_post;

import androidx.annotation.Nullable;

class EditPostFormState {

    @Nullable
    private Integer nameError;

    @Nullable
    private Integer descriptionError;

    @Nullable
    private Integer addressError;

    private boolean isDataValid;

    public EditPostFormState(@Nullable Integer nameError, @Nullable Integer descriptionError, @Nullable Integer addressError) {
        this.nameError = nameError;
        this.descriptionError = descriptionError;
        this.addressError = addressError;
        this.isDataValid = false;
    }

    public EditPostFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.nameError = null;
        this.descriptionError = null;
        this.addressError = null;
    }

    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    @Nullable
    public Integer getDescriptionError() {
        return descriptionError;
    }

    @Nullable
    public Integer getAddressError() {
        return addressError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }

}
