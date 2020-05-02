package com.app.travelapp.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.repositories.PlaceRepository;
import com.app.travelapp.ui.main.home.HomeViewModel;
import com.app.travelapp.ui.main.search.SearchViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context context;
    public ViewModelFactory(Context context){
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DataSourceFirebase dataSourceFirebase = new DataSourceFirebase(this.context);
        DataSourceCache dataSourceCache = DataSourceCache.getInstance();
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
