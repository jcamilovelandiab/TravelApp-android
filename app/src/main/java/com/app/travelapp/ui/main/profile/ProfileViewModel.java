package com.app.travelapp.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.UserRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> user = new MutableLiveData<>();

    public ProfileViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        LoggedInUser loggedInUser = userRepository.getLoggedInUser();
        User loggedUser = new User(loggedInUser.getUsername()+"",loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        List<Place> places = userRepository.getPlacesFromUser(loggedUser);
        loggedUser.setPlaces(places);
        user.setValue(loggedUser);
    }

    public LiveData<User> getUser() {
        return user;
    }
}