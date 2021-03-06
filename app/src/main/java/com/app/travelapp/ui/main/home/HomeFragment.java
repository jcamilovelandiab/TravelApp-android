package com.app.travelapp.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.ui.adapters.PlaceArrayAdapter;
import com.app.travelapp.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView lv_places;
    ProgressBar pg_loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        lv_places = root.findViewById(R.id.home_lv_places);
        pg_loading = root.findViewById(R.id.home_pg_loading);
        configurePlaceListObserver();
        return root;
    }

    private void configurePlaceListObserver(){
        homeViewModel.getPlaces().observe(getViewLifecycleOwner(), places -> {
            pg_loading.setVisibility(View.GONE);
            PlaceArrayAdapter adapter = new PlaceArrayAdapter(getActivity(), (ArrayList) places);
            lv_places.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }

}
