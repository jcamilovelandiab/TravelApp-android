package com.app.travelapp.ui.auth.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.travelapp.R;
import com.app.travelapp.data.datasources.Session;
import com.app.travelapp.data.model.Role;
import com.app.travelapp.ui.auth.sign_up.SignUpActivity;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.AuthViewModelFactory;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.ui.main.MainActivity;
import com.app.travelapp.ui.main_admin.MainAdminActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    EditText usernameEditText, passwordEditText;
    Button loginButton, signUpButton;
    ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new AuthViewModelFactory(getApplicationContext()))
                .get(LoginViewModel.class);

        connectViewWithModel();
        configureLoginFormStateObserver();
        configureLoginResultObserver();
        configureTextWatcher();
        configureLoginButton();
        configureSignUpButton();
    }

    private void connectViewWithModel(){
        usernameEditText = findViewById(R.id.login_et_username);
        passwordEditText = findViewById(R.id.login_et_password);
        loginButton = findViewById(R.id.login_btn_sign_in);
        signUpButton = findViewById(R.id.login_btn_sign_up);
        loadingProgressBar = findViewById(R.id.login_pb_loading);
    }

    private void configureLoginFormStateObserver() {
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if(loginFormState!=null){
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
    }

    private void configureLoginResultObserver(){
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if(loginResult==null) return;
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
                setResult(Activity.RESULT_OK);
                //Complete and destroy login activity once successful
                finish();
            }
        });
    }

    private void configureTextWatcher(){
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
    }

    private void configureLoginButton(){
        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            hideKeyboard(this);
            runOnUiThread(() -> loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString()));
        });
    }

    private void configureSignUpButton(){
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            setResult(Activity.RESULT_OK);
            //Complete and destroy login activity once successful
            finish();
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome);
        // TODO : initiate successful logged in experience

        Intent intent;
        if(Session.getRole().equals(Role.user)){
            welcome+= " "+ model.getDisplayName() +"!";
            intent = new Intent(this, MainActivity.class);
        }else{
            welcome+=" admin "+ model.getDisplayName()+"!";
            intent = new Intent(this, MainAdminActivity.class);
        }
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        //Complete and destroy login activity once successful
        finish();
    }

    private void showLoginFailed(final String errorString) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(LoginActivity.this, errorString, Toast.LENGTH_SHORT);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
