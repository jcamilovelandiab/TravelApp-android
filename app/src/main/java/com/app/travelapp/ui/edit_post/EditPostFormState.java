package com.app.travelapp.ui.edit_post;

import androidx.annotation.Nullable;

class EditPostFormState {

    @Nullable
    private Integer descriptionError;

    @Nullable
    private Integer addressError;

    private boolean isDataValid;

    public EditPostFormState(Integer descriptionError, @Nullable Integer addressError) {
        this.descriptionError = descriptionError;
        this.addressError = addressError;
        this.isDataValid = false;
    }

    public EditPostFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.descriptionError = null;
        this.addressError = null;
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
