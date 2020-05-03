package com.app.travelapp.ui.edit_post;

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

public class EditPostViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private MutableLiveData<BasicResult> editPostResult = new MutableLiveData<>();
    private MutableLiveData<EditPostFormState> editPostFormState = new MutableLiveData<>();
    private Place place;

    public EditPostViewModel(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public LiveData<BasicResult> getEditPostResult() {
        return editPostResult;
    }
    public LiveData<EditPostFormState> getEditPostFormState() {
        return editPostFormState;
    }

    public void dataChanged(String description, String address){
        if(!Validator.isStringValid(description)){
            editPostFormState.setValue(new EditPostFormState(null, R.string.invalid_place_description, null));
        } else if(!Validator.isStringValid(address)){
            editPostFormState.setValue(new EditPostFormState(null, null, R.string.invalid_place_address));
        } else {
            editPostFormState.setValue(new EditPostFormState(true));
        }
    }

    public void updatePlace(String description, String address){
        this.place.setDescription(description);
        this.place.setAddress(address);
        Result result = placeRepository.updatePlace(place);
        if(result instanceof  Result.Success){
            String data = (String) ((Result.Success) result).getData();
            editPostResult.setValue(new BasicResult(data));
        }else{
            editPostResult.setValue(new BasicResult(R.string.update_place_failed));
        }
    }

    public Place getPlaceById(String placeId){
        this.place = placeRepository.findById(placeId);
        return place;
    }

}
