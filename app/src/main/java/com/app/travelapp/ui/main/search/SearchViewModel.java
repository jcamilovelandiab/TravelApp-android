package com.app.travelapp.ui.main.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.repositories.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private MutableLiveData<List<Place>> placeList = new MutableLiveData<>();

    public SearchViewModel(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        List<Place> places = new ArrayList<>();
        placeList.setValue(places);
    }

    public LiveData<List<Place>> getPlaceList() {
        return placeList;
    }

    public void findPlacesByName(String search_name) {
        if(search_name==null || search_name.isEmpty()) return;
        List<Place> places = placeRepository.findByName(search_name);
        placeList.setValue(places);
    }

}