package com.app.travelapp.data.repositories;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.model.Place;

import java.util.List;

public class PlaceRepository {

    private static volatile PlaceRepository instance;

    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;

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

    public List<Place> findAll(){
        return dataSourceCache.getPlaces();
    }

}
