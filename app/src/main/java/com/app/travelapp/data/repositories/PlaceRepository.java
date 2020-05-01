package com.app.travelapp.data.repositories;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;

public class PlaceRepository {

    private static volatile PlaceRepository instance;

    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;

}
