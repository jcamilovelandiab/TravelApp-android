package com.app.travelapp.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.adapters.PlaceArrayAdapter;
import com.app.travelapp.ui.main.ViewModelFactory;

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

}
