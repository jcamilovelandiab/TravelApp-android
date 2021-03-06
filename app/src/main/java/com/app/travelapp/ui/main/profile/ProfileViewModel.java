package com.app.travelapp.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.R;
import com.app.travelapp.data.datasources.Session;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.PlaceRepository;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.utils.BasicResult;
import com.app.travelapp.utils.Result;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<List<Place>> places = new MutableLiveData<>();
    private MutableLiveData<BasicResult> deletePlaceResult = new MutableLiveData<>();

    public ProfileViewModel(UserRepository userRepository, PlaceRepository placeRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        retrieveUserInformation();
    }

    public LiveData<User> getUser() {
        return user;
    }
    public LiveData<List<Place>> getPlaces(){
        return places;
    }

    public LiveData<BasicResult> getPostDeleteResult() {
        return deletePlaceResult;
    }

    private void retrieveUserInformation(){
        LoggedInUser loggedInUser = Session.getLoggedInUser();
        User loggedUser = new User(loggedInUser.getUsername()+"",loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        user.setValue(loggedUser);
        userRepository.getPlacesFromUser(loggedUser, places);
    }

    public void refreshUser(){
        retrieveUserInformation();
    }

    public void deletePost(String placeId){
        placeRepository.delete(placeId, deletePlaceResult);
    }

}