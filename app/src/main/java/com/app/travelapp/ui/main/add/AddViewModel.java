package com.app.travelapp.ui.main.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.travelapp.R;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.data.repositories.PlaceRepository;
import com.app.travelapp.data.repositories.UserRepository;
import com.app.travelapp.utils.BasicResult;
import com.app.travelapp.utils.Result;
import com.app.travelapp.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private UserRepository userRepository;

    private MutableLiveData<BasicResult> addResult = new MutableLiveData<>();
    private MutableLiveData<AddFormState> addFormState = new MutableLiveData<>();

    public AddViewModel(PlaceRepository placeRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    public LiveData<BasicResult> getAddResult() {
        return addResult;
    }

    public LiveData<AddFormState> getAddFormState() {
        return addFormState;
    }

    public void dataChanged(String name, String description, String address, String picture_path){
        if(!Validator.isStringValid(name)){
            addFormState.setValue(new AddFormState(R.string.invalid_place_name, null, null, null));
        }else if(!Validator.isStringValid(description)){
            addFormState.setValue(new AddFormState(null, R.string.invalid_place_description, null, null));
        }else if(!Validator.isStringValid(address)){
            addFormState.setValue(new AddFormState(null, null, R.string.invalid_place_address, null));
        }else if(picture_path.trim().isEmpty()){
            addFormState.setValue(new AddFormState(null, null, null, R.string.invalid_place_picture));
        }else{
            addFormState.setValue(new AddFormState(true));
        }
    }

    public void addPlace(String name, String description, String address, String picture_path){
        List<String> images = new ArrayList<>(); images.add(picture_path);
        LoggedInUser loggedInUser = userRepository.getLoggedInUser();
        User author = new User(loggedInUser.getUsername()+"", loggedInUser.getEmail()+"", loggedInUser.getFull_name()+"");
        Place place = new Place(UUID.randomUUID().toString()+"",
                name+"", description+"",
                address+"", images, author);
        Result result = placeRepository.savePlace(place);
        if(result instanceof  Result.Success){
            String data = (String) ((Result.Success) result).getData();
            addResult.setValue(new BasicResult(data));
        }else{
            addResult.setValue(new BasicResult(R.string.save_place_failed));
        }
    }

}