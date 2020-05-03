package com.app.travelapp.ui.main.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.adapters.PlaceArrayAdapter;
import com.app.travelapp.ui.edit_post.EditPostActivity;
import com.app.travelapp.ui.ViewModelFactory;
import com.app.travelapp.utils.BasicResult;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    TextView et_full_name, et_username, et_email;
    ImageView iv_picture;
    ListView lv_places;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        connectModelWithView(root);
        prepareUserObserver();
        prepareDeletePlaceResultObserver();
        configureClickablePlaces();
        return root;
    }

    private void connectModelWithView(View view){
        et_full_name = view.findViewById(R.id.profile_et_full_name);
        et_username = view.findViewById(R.id.profile_et_username);
        et_email = view.findViewById(R.id.profile_et_email);
        iv_picture = view.findViewById(R.id.profile_iv_picture);
        lv_places = view.findViewById(R.id.profile_lv_places_posted);
    }

    private void prepareUserObserver(){
        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null) return;
                if(user.getEmail()==null || user.getUsername()==null || user.getFull_name()==null || user.getPlaces()==null){
                    Toast.makeText(getActivity(), "Whoops!!!. An error occurred trying to retrieve user information", Toast.LENGTH_LONG).show();
                    return;
                }
                et_full_name.setText(user.getFull_name());
                et_username.setText(user.getUsername());
                et_email.setText(user.getEmail());
                PlaceArrayAdapter placeArrayAdapter = new PlaceArrayAdapter(getActivity(), (ArrayList<Place>) user.getPlaces(), R.color.colorAccent);
                lv_places.setAdapter(placeArrayAdapter);
                placeArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void prepareDeletePlaceResultObserver(){
        profileViewModel.getPostDeleteResult().observe(ProcessLifecycleOwner.get(), new Observer<BasicResult>() {
            @Override
            public void onChanged(BasicResult basicResult) {
                if(basicResult==null);
                if(basicResult.getError()!=null){
                    showErrorMessage(getString(basicResult.getError()),0);
                }
                if(basicResult.getSuccess()!=null){
                    showSuccessMessage(basicResult.getSuccess());
                }
            }
        });
    }

    private void configureClickablePlaces(){
        final String[] options = {"Edit", "Delete"};
        lv_places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Place place = (Place)parent.getAdapter().getItem(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                editPost(place.getPlaceId());
                                break;
                            case 1:
                                deletePost(place.getPlaceId());
                                break;
                        }
                    }
                });
                Dialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });
    }

    private void editPost(String placeId){
        Intent intent = new Intent(getActivity(), EditPostActivity.class);
        intent.putExtra("placeId", placeId);
        startActivity(intent);
    }

    private void deletePost(String placeId){
        profileViewModel.deletePost(placeId);
    }

    @Override
    public void onResume() {
        profileViewModel.refreshUser();
        super.onResume();
    }

    private void showErrorMessage(final String errorMsg, long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
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
