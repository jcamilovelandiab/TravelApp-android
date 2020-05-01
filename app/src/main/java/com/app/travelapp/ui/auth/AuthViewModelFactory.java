package com.app.travelapp.ui.auth;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.ui.auth.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private Context context;

    public AuthViewModelFactory(Context context){
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DataSourceFirebase dataSourceFirebase = new DataSourceFirebase(this.context);
        DataSourceCache dataSourceCache = new DataSourceCache();
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(UserRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}
