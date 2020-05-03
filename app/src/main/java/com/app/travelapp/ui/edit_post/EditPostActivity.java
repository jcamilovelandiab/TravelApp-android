package com.app.travelapp.ui.edit_post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.travelapp.R;
import com.app.travelapp.ui.main.ViewModelFactory;
import com.app.travelapp.utils.BasicResult;

import org.w3c.dom.Text;

public class EditPostActivity extends AppCompatActivity {

    private EditText et_place_description, et_place_address;
    private TextView tv_place_name;
    private ImageView iv_place_picture;
    private Button btn_update;

    String picture_path="", current_picture_path="";
    Uri picture_uri;
    private static final int REQUEST_TAKE_PHOTO=1;
    private EditPostViewModel editPostViewModel;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        editPostViewModel = ViewModelProviders.of(this,  new ViewModelFactory(this)).get(EditPostViewModel.class);
        bundle = this.getIntent().getExtras();
        editPostViewModel.getPlaceById(bundle.getString("placeId"));

        connectModelWithView();
        prepareEditPostFormStateObserver();
        prepareEditPostResultObserver();
        configureTextWatchers();
        configureBtnUpdate();
    }

    private void connectModelWithView() {
        tv_place_name = findViewById(R.id.edit_post_tv_place_name);
        et_place_description = findViewById(R.id.edit_post_et_place_description);
        et_place_address = findViewById(R.id.edit_post_et_place_address);
        iv_place_picture = findViewById(R.id.edit_post_iv_place_picture);
        btn_update = findViewById(R.id.edit_post_btn_update);
    }

    private void configureTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editPostViewModel.dataChanged(et_place_description.getText().toString()+"",
                        et_place_address.getText().toString()+"");
            }
        };
        et_place_description.addTextChangedListener(textWatcher);
        et_place_address.addTextChangedListener(textWatcher);
    }

    private void prepareEditPostFormStateObserver(){
        editPostViewModel.getEditPostFormState().observe(this, new Observer<EditPostFormState>() {
            @Override
            public void onChanged(EditPostFormState editPostFormState) {
                if(editPostFormState == null) return;
                btn_update.setEnabled(editPostFormState.isDataValid());
                if(editPostFormState.getDescriptionError() != null){
                    et_place_description.setError(getString(editPostFormState.getDescriptionError()));
                }
                if(editPostFormState.getAddressError() != null){
                    et_place_address.setError(getString(editPostFormState.getAddressError()));
                }
            }
        });
    }

    private void prepareEditPostResultObserver(){
        editPostViewModel.getEditPostResult().observe(this, new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    showErrorMessage(getString(basicResult.getError()),0);
                }
                if(basicResult.getSuccess()!=null){
                    showSuccessMessage(basicResult.getSuccess());
                }
            }
        });
    }

    private void configureBtnUpdate(){
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPostViewModel.updatePlace(et_place_description.getText().toString()+"",
                        et_place_address.getText().toString()+"");
            }
        });
    }

    private void showErrorMessage(final String errorMsg, long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(EditPostActivity.this, errorMsg, Toast.LENGTH_LONG);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setGravity(Gravity.CENTER);
                text.setTextColor(Color.WHITE);
                toast.show();
            }
        }, delayMillis);
    }

    private void showSuccessMessage(final String msg){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(EditPostActivity.this, msg, Toast.LENGTH_SHORT);
                View view = toast.getView();
                //Gets the actual oval background of the Toast then sets the colour filter
                view.getBackground().setColorFilter(Color.parseColor("#00A600"), PorterDuff.Mode.SRC_IN);
                //Gets the TextView from the Toast so it can be edited
                TextView text = view.findViewById(android.R.id.message);
                text.setGravity(Gravity.CENTER);
                text.setTextColor(Color.BLACK);
                toast.show();
            }
        });
    }

}
