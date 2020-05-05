package com.app.travelapp.ui.main_admin.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.UserRepository;

import java.util.List;

public class UsersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private UserRepository userRepository;
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    public UsersViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        retrieveAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void retrieveAllUsers(){
        userRepository.findAll(users);
    }

}
