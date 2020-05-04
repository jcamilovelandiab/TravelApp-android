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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.adapters.PlaceListRecyclerViewAdapter;
import com.app.travelapp.ui.adapters.RecyclerViewClickListener;
import com.app.travelapp.ui.edit_post.EditPostActivity;
import com.app.travelapp.ui.ViewModelFactory;
import com.app.travelapp.utils.BasicResult;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private TextView et_full_name, et_username, et_email;
    private ImageView iv_picture;
    private RecyclerView rv_places;
    private RecyclerView.Adapter adapter_places;
    private ProgressBar pg_loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        connectModelWithView(root);
        prepareUserObserver();
        prepareDeletePlaceResultObserver();
        preparePlacesFromUserObserver();
        return root;
    }

    private void connectModelWithView(View view){
        et_full_name = view.findViewById(R.id.profile_et_full_name);
        et_username = view.findViewById(R.id.profile_et_username);
        et_email = view.findViewById(R.id.profile_et_email);
        iv_picture = view.findViewById(R.id.profile_iv_picture);
        rv_places = view.findViewById(R.id.profile_rv_places_posted);
        rv_places.setHasFixedSize(true);
        rv_places.setLayoutManager(new LinearLayoutManager(getActivity()));
        pg_loading = view.findViewById(R.id.profile_pg_loading);
    }

    private void prepareUserObserver(){
        profileViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null) return;
                if(user.getEmail()==null || user.getUsername()==null || user.getFull_name()==null){
                    Toast.makeText(getActivity(), "Whoops!!!. An error occurred trying to retrieve user information", Toast.LENGTH_LONG).show();
                    return;
                }
                et_full_name.setText(user.getFull_name());
                et_username.setText(user.getUsername());
                et_email.setText(user.getEmail());
            }
        });
    }

    private void preparePlacesFromUserObserver(){
        profileViewModel.getPlaces().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                if(places==null) return;
                adapter_places = new PlaceListRecyclerViewAdapter(getActivity(), places, new RecyclerViewClickListener() {
                    @Override
                    public void onMorePlaceClicked(Place place) {
                        configureDialog(place);
                    }
                });
                rv_places.setAdapter(adapter_places);
                adapter_places.notifyDataSetChanged();
                pg_loading.setVisibility(View.GONE);
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

    private void configureDialog(final Place place){
        final String[] options = {"Edit", "Delete"};
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

    private void editPost(String placeId){
        Intent intent = new Intent(getActivity(), EditPostActivity.class);
        intent.putExtra("placeId", placeId);
        startActivity(intent);
    }

    private void deletePost(String placeId){
        pg_loading.setVisibility(View.VISIBLE);
        profileViewModel.deletePost(placeId);
        profileViewModel.refreshUser();
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
