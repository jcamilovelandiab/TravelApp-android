package com.app.travelapp.ui.main.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.repositories.PlaceRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private MutableLiveData<List<Place>> placeList = new MutableLiveData<>();

    public HomeViewModel(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        List<Place> places = placeRepository.findAll();
        placeList.setValue(places);
    }

    public LiveData<List<Place>> getPlaces() {
        return placeList;
    }

}