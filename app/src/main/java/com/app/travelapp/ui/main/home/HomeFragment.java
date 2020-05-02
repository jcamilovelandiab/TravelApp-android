package com.app.travelapp.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.app.travelapp.R;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.ui.adapters.PlaceArrayAdapter;
import com.app.travelapp.ui.main.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ListView lv_places;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        lv_places = root.findViewById(R.id.home_lv_places);
        configurePlaceListObserver();
        return root;
    }

    private void configurePlaceListObserver(){
        homeViewModel.getPlaces().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                PlaceArrayAdapter adapter = new PlaceArrayAdapter(getActivity(), (ArrayList) places);
                lv_places.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
