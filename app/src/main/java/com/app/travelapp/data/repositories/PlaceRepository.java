package com.app.travelapp.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.utils.BasicResult;
import com.app.travelapp.utils.Result;

import java.util.List;

public class PlaceRepository {
    private static volatile PlaceRepository instance;
    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;
    // private constructor : singleton access
    private PlaceRepository(DataSourceCache dataSourceCache, DataSourceFirebase dataSourceFirebase) {
        this.dataSourceCache = dataSourceCache;
        this.dataSourceFirebase = dataSourceFirebase;
    }
    public static PlaceRepository getInstance(DataSourceCache dataSourceCache, DataSourceFirebase dataSourceFirebase){
        if(instance==null){
            instance = new PlaceRepository(dataSourceCache, dataSourceFirebase);
        }
        return instance;
    }
    public void findAll(MutableLiveData<List<Place>> placesQuery){
        dataSourceFirebase.getPlaces(placesQuery);
    }
    public void findPlaceById(String placeId, MutableLiveData<Place> placeQuery){
        dataSourceFirebase.getPlaceById(placeId, placeQuery);
    }
    public void findPlacesByName(String name, MutableLiveData<List<Place>> places){
        dataSourceFirebase.findPlacesByName(name, places);
    }
    public void savePlace(Place place, MutableLiveData<BasicResult> addResult){
        dataSourceFirebase.savePlace(place, addResult);
    }
    public void updatePlace(Place place, MutableLiveData<BasicResult> updatePlaceResult){
        dataSourceFirebase.updatePlace(place, updatePlaceResult);
    }
    public void delete(String placeId, MutableLiveData<BasicResult> deletePlaceResult){
        dataSourceFirebase.deletePlace(placeId, deletePlaceResult);
    }

    public Place findById(String placeId){
        return dataSourceCache.getPlaceById(placeId);
    }

    public List<Place> findByName(String name){
        return dataSourceCache.getPlacesByName(name);
    }

    public List<Place> findAll(){
        return dataSourceCache.getPlaces();
    }

    public Result savePlace(Place place){
        return dataSourceCache.savePlace(place);
    }



    public Result updatePlace(Place place) {
        return dataSourceCache.updatePlace(place);
    }



    public Result delete(String placeId) {
        return dataSourceCache.deletePlace(placeId);
    }



}
