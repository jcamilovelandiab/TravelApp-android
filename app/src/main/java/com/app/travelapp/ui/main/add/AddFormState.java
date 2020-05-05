package com.app.travelapp.ui.main.add;
import androidx.annotation.Nullable;

class AddFormState {
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer descriptionError;
    @Nullable
    private Integer addressError;
    @Nullable
    private Integer pictureError;
    private boolean isDataValid;
    public AddFormState(@Nullable Integer nameError, @Nullable Integer descriptionError,
                        @Nullable Integer addressError, @Nullable Integer pictureError) {
        this.nameError = nameError;
        this.descriptionError = descriptionError;
        this.addressError = addressError;
        this.pictureError = pictureError;
        this.isDataValid = false;
    }
    public AddFormState(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.nameError = null;
        this.descriptionError = null;
        this.addressError = null;
        this.pictureError = null;
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

    @Nullable
    public Integer getPictureError() {
        return pictureError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
