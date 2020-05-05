package com.app.travelapp.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.repositories.PlaceRepository;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.ui.edit_post.EditPostViewModel;
import com.app.travelapp.ui.main.add.AddViewModel;
import com.app.travelapp.ui.main.home.HomeViewModel;
import com.app.travelapp.ui.main.profile.ProfileViewModel;
import com.app.travelapp.ui.main.search.SearchViewModel;
import com.app.travelapp.ui.main_admin.users.UsersViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Context context;
    
    public ViewModelFactory(Context context){
        this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DataSourceFirebase dataSourceFirebase = DataSourceFirebase.getInstance(context);
        DataSourceCache dataSourceCache = DataSourceCache.getInstance();
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        }  else if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel(UserRepository.getInstance(dataSourceCache, dataSourceFirebase),
                    PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else if(modelClass.isAssignableFrom(AddViewModel.class)){
            return (T) new AddViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else if(modelClass.isAssignableFrom(EditPostViewModel.class)){
            return (T) new EditPostViewModel(PlaceRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else if(modelClass.isAssignableFrom(UsersViewModel.class)){
            return (T) new UsersViewModel(UserRepository.getInstance(dataSourceCache, dataSourceFirebase));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
