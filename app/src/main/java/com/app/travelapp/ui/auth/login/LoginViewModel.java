package com.app.travelapp.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.datasources.Session;
import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.utils.Result;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.R;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.utils.Validator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> loginResult = new MutableLiveData<>();
    private UserRepository userRepository;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthResult> getLoginResult(){
        return loginResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        /*FOR FIREBASE*/
        userRepository.login(email, password, loginResult);
    }

    public void loginDataChanged(String email, String password) {
        if (!Validator.isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        } else if (!Validator.isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

}
