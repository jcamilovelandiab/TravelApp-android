package com.app.travelapp.ui.main.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    ListView lv_places;
    EditText et_search_text;
    //Button btn_search;
    ProgressBar pg_loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this, new ViewModelFactory(getActivity())).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        connectModelWithView(root);
        configureSearch();
        configurePlaceListObserver();
        return root;
    }

    private void connectModelWithView(View view){
        lv_places = view.findViewById(R.id.search_lv_places);
        et_search_text = view.findViewById(R.id.search_et_search_text);
        pg_loading = view.findViewById(R.id.search_pg_loading);
        //btn_search = view.findViewById(R.id.search_btn_search);
    }

    private void configureSearch(){
        et_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchViewModel.findPlacesByName(et_search_text.getText().toString());
                    pg_loading.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    private void configurePlaceListObserver(){
        searchViewModel.getPlaceList().observe(getViewLifecycleOwner(), new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                PlaceArrayAdapter adapter = new PlaceArrayAdapter(getActivity(), (ArrayList) places);
                lv_places.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                pg_loading.setVisibility(View.GONE);
            }
        });
    }
}
