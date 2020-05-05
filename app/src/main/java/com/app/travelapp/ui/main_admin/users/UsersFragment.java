package com.app.travelapp.ui.main_admin.users;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.travelapp.R;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.ViewModelFactory;
import com.app.travelapp.ui.adapters.UserArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private UsersViewModel usersViewModel;
    private ListView lv_users;
    private ProgressBar pg_loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        usersViewModel = ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(UsersViewModel.class);
        lv_users = root.findViewById(R.id.users_lv_users);
        pg_loading = root.findViewById(R.id.users_pg_loading);
        configureUsersObservable();
        return root;
    }

    private void configureUsersObservable(){
        usersViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users==null) return;
                UserArrayAdapter userArrayAdapter = new UserArrayAdapter(getActivity(), (ArrayList<User>) users);
                lv_users.setAdapter(userArrayAdapter);
                userArrayAdapter.notifyDataSetChanged();
                pg_loading.setVisibility(View.GONE);
            }
        });
    }

}
