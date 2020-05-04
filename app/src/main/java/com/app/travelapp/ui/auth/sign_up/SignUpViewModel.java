package com.app.travelapp.ui.auth.sign_up;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.R;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.utils.Result;
import com.app.travelapp.utils.Validator;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> signUpResult = new MutableLiveData<>();
    private UserRepository userRepository;

    public SignUpViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<SignUpFormState> getSignUpFormState() {
        return signUpFormState;
    }

    LiveData<AuthResult> getSignUpResult() {
        return signUpResult;
    }

    void signUpDataChanged(String full_name, String username, String email, String password,  String confirm_password){
        if(!Validator.isNameValid(full_name)) {
            signUpFormState.setValue(new SignUpFormState(R.string.invalid_name, null, null, null, null));
        } else if(!Validator.isUsernameValid(username)){
            signUpFormState.setValue(new SignUpFormState(null, R.string.invalid_username, null, null, null));
        }else if(!Validator.isEmailValid(email)){
            signUpFormState.setValue(new SignUpFormState(null, null, R.string.invalid_email, null, null));
        }else if(!Validator.isPasswordValid(password)){
            signUpFormState.setValue(new SignUpFormState(null, null, null, R.string.invalid_password, null));
        }else if(!Validator.isConfirmPasswordValid(password, confirm_password)){
            signUpFormState.setValue(new SignUpFormState(null, null, null, null, R.string.invalid_confirmation_password));
        }else{
            signUpFormState.setValue(new SignUpFormState(true));
        }
    }

    public void signUp(String full_name, String username,  String email, String password){
        User user = new User(username+"",email+"", password+"", full_name+"");
        userRepository.signUp(user, signUpResult);
    }

}
