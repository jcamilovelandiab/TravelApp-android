package com.app.travelapp.ui.main.add;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.travelapp.R;
import com.app.travelapp.ui.main.ViewModelFactory;
import com.app.travelapp.utils.BasicResult;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    EditText et_place_name, et_place_description, et_place_address;
    ImageView iv_place_picture;
    Button btn_save;

    String picture_path="testing", current_picture_path="";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        connectModelWithView(root);
        configureBtnSave();
        configureTextWatchers();
        prepareFormStateObserver();
        prepareResultObserver();
        return root;
    }

    private void connectModelWithView(View root) {
        et_place_name = root.findViewById(R.id.add_et_place_name);
        et_place_description = root.findViewById(R.id.add_et_place_description);
        et_place_address = root.findViewById(R.id.add_et_place_address);
        iv_place_picture = root.findViewById(R.id.add_iv_place_picture);
        btn_save = root.findViewById(R.id.add_btn_save);
    }

    private void configureBtnSave() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addViewModel.addPlace(et_place_name.getText().toString()+"",
                        et_place_description.getText().toString()+"",
                        et_place_address.getText().toString()+"",
                        picture_path+""
                        );
            }
        });
    }

    private void prepareFormStateObserver() {
        addViewModel.getAddFormState().observe(getViewLifecycleOwner(), new Observer<AddFormState>() {
            @Override
            public void onChanged(AddFormState addFormState) {
                if(addFormState==null) return;
                btn_save.setEnabled(addFormState.isDataValid());
                if(addFormState.getNameError()!=null){
                    et_place_name.setError(getString(addFormState.getNameError()));
                }
                if(addFormState.getDescriptionError()!=null){
                    et_place_description.setError(getString(addFormState.getDescriptionError()));
                }
                if(addFormState.getAddressError()!=null){
                    et_place_address.setError(getString(addFormState.getAddressError()));
                }
                if(addFormState.getPictureError()!=null){
                    showErrorMessage("Click on the camera and add a photo of the place to complete the registration", 2000);
                }
            }
        });
    }

    private void prepareResultObserver() {
        addViewModel.getAddResult().observe(getViewLifecycleOwner(), new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null) return;
                if(basicResult.getError()!=null){
                    showErrorMessage(getString(basicResult.getError()),0);
                }
                if(basicResult.getSuccess()!=null){
                    showSuccessMessage("Your place has been successfully posted");
                    cleanFields();
                }
            }
        });
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
                addViewModel.dataChanged(et_place_name.getText().toString()+"",
                        et_place_description.getText().toString()+"",
                        et_place_address.getText().toString()+"",
                        picture_path+"");
            }
        };
        et_place_name.addTextChangedListener(textWatcher);
        et_place_description.addTextChangedListener(textWatcher);
        et_place_address.addTextChangedListener(textWatcher);
    }

    private void showErrorMessage(final String errorMsg, long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG);
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
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
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

    private void cleanFields(){
        picture_path = "";
        et_place_name.setText("");
        et_place_description.setText("");
        et_place_address.setText("");
    }

}
