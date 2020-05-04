package com.app.travelapp.ui.auth.sign_up;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.travelapp.R;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.AuthViewModelFactory;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.ui.auth.login.LoginActivity;
import com.app.travelapp.ui.main.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    Button btn_login, btn_sign_up;
    EditText et_full_name, et_username, et_email, et_password, et_confirm_password;
    ProgressBar pg_loading;
    SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this, new AuthViewModelFactory(getApplicationContext())).get(SignUpViewModel.class);
        connectViewWithModel();

        configureTextWatchers();
        configureLoginButton();
        configureSignUpButton();
        configureSignUpFormStateObserver();
        configureSignUpResultObserver();
    }

    private void connectViewWithModel(){
        btn_login = findViewById(R.id.sign_up_btn_sign_in);
        btn_sign_up = findViewById(R.id.sign_up_btn_sign_up);
        et_full_name = findViewById(R.id.sign_up_et_full_name);
        et_username = findViewById(R.id.sign_up_et_username);
        et_email = findViewById(R.id.sign_up_et_email);
        et_password = findViewById(R.id.sign_up_et_password);
        et_confirm_password = findViewById(R.id.sign_up_et_confirm_password);
        pg_loading = findViewById(R.id.sign_up_pg_loading);
    }

    private void configureSignUpFormStateObserver(){
        signUpViewModel.getSignUpFormState().observe(this, new Observer<SignUpFormState>() {

            @Override
            public void onChanged(SignUpFormState signUpFormState) {
                if(signUpFormState == null) return;
                btn_sign_up.setEnabled(signUpFormState.isDataValid());

                if(signUpFormState.getFull_nameError()!=null){
                    et_full_name.setError(getString(signUpFormState.getFull_nameError()));
                }else if(!et_full_name.getText().toString().equals("")){
                    addGreenCheckIcon(et_full_name);
                }

                if(signUpFormState.getUsernameError()!=null){
                    et_username.setError(getString(signUpFormState.getUsernameError()));
                }else if(!et_username.getText().toString().equals("")){
                    addGreenCheckIcon(et_username);
                }

                if(signUpFormState.getEmailError()!=null){
                    et_email.setError(getString(signUpFormState.getEmailError()));
                }else if(!et_email.getText().toString().equals("")){
                    addGreenCheckIcon(et_email);
                }

                if(signUpFormState.getPasswordError()!=null){
                    et_password.setError(getString(signUpFormState.getPasswordError()));
                }else if(!et_password.getText().toString().equals("")){
                    addGreenCheckIcon(et_password);
                }

                if(signUpFormState.getConfirm_passwordError()!=null){
                    et_confirm_password.setError(getString(signUpFormState.getConfirm_passwordError()));
                }else if(!et_confirm_password.getText().toString().equals("")){
                    addGreenCheckIcon(et_confirm_password);
                }
            }
        });
    }

    private void configureSignUpResultObserver(){

        signUpViewModel.getSignUpResult().observe(this, new Observer<AuthResult>() {
            @Override
            public void onChanged(AuthResult authResult) {
                if(authResult==null) return;
                pg_loading.setVisibility(View.GONE);
                if(authResult.getError()!=null){
                    showSignUpFailed(authResult.getError());
                }
                if(authResult.getSuccess()!=null){
                    updateUiWithUser(authResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    //Complete and destroy signUp activity once successful
                    finish();
                }
            }
        });
    }

    private void showSignUpFailed(final String errorString){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(SignUpActivity.this, errorString, Toast.LENGTH_SHORT);
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

    private void configureTextWatchers() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                signUpViewModel.signUpDataChanged(
                        et_full_name.getText().toString()+"",
                        et_username.getText().toString()+"",
                        et_email.getText().toString()+"",
                        et_password.getText().toString()+"",
                        et_confirm_password.getText().toString()+""
                );
            }
        };
        et_full_name.addTextChangedListener(afterTextChangedListener);
        et_email.addTextChangedListener(afterTextChangedListener);
        et_password.addTextChangedListener(afterTextChangedListener);
        et_confirm_password.addTextChangedListener(afterTextChangedListener);
        et_confirm_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signUpViewModel.signUpDataChanged(
                            et_full_name.getText().toString()+"",
                            et_username.getText().toString()+"",
                            et_email.getText().toString()+"",
                            et_password.getText().toString()+"",
                            et_confirm_password.getText().toString()+""
                    );
                }
                return false;
            }
        });
    }

    private void configureLoginButton(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                setResult(Activity.RESULT_OK);
                //Complete and destroy signUp activity once successful
                finish();
            }
        });
    }

    private void configureSignUpButton(){
        btn_sign_up.setOnClickListener(v -> {
            pg_loading.setVisibility(View.VISIBLE);
            hideKeyboard(this);
            signUpViewModel.signUp(et_full_name.getText().toString()+"",
                    et_username.getText().toString()+"",
                    et_email.getText().toString()+"",
                    et_password.getText().toString()+"");
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + " "+model.getDisplayName()+"!";
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        setResult(Activity.RESULT_OK);
        //Complete and destroy login activity once successful
        finish();
    }

    private void addGreenCheckIcon(final EditText editText){
        editText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_green_24dp, 0);
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
