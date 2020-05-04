package com.app.travelapp.data.datasources;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.app.travelapp.R;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.utils.BasicResult;
import com.app.travelapp.utils.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFirebase {


    private static DataSourceFirebase instance;
    private Context context;
    FirebaseFirestore db;

    private DataSourceFirebase(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    private DataSourceFirebase(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static DataSourceFirebase getInstance(Context context){
        if(instance == null){
            instance = new DataSourceFirebase(context);
        }
        return instance;
    }

    public FirebaseFirestore getDatabase(){
        return this.db;
    }

    public static DataSourceFirebase getInstance(){
        if(instance==null){
            instance = new DataSourceFirebase();
        }
        return instance;
    }

    // Users
    public void login(String email,String password, MutableLiveData<AuthResult> loginResult) {
        // TODO: handle loggedInUser authentication
        MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
        DocumentReference docRef = DataSourceFirebase.getInstance().getDatabase().collection("/Users").document(email);
        docRef.get().addOnCompleteListener( task ->{
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document.exists()){
                    String email_query, username_query, password_query, full_name_query;
                    email_query = document.getString("email");
                    username_query = document.getString("username");
                    password_query = document.getString("password");
                    full_name_query = document.getString("full_name");
                    User user = new User(username_query+"", email_query+"",password_query+"",full_name_query+"");
                    if(user.getPassword().equals(password)){
                        LoggedInUser loggedInUser = new LoggedInUser(username_query+"", email_query+"", full_name_query+"");
                        Session.setLoggedInUser(loggedInUser);
                        loginResult.setValue(new AuthResult(new LoggedInUserView(loggedInUser.getFull_name())));
                    }else{
                        loginResult.setValue(new AuthResult("Invalid login"));
                    }
                }else{
                    loginResult.setValue(new AuthResult("Invalid login"));
                }
            }else{
                loginResult.setValue(new AuthResult("Error in logging in"));
            }
        });
    }

    public void signUp(User user, MutableLiveData<AuthResult> signUpResult){
        //INSERTING DATA INTO FIREBASE
        Map<String, Object> userMp = new HashMap<>();
        userMp.put("email", user.getEmail());
        userMp.put("username", user.getUsername());
        userMp.put("password", user.getPassword());
        userMp.put("full_name",user.getFull_name());
        try{
            db.collection("/Users").document(user.getEmail()).set(userMp);
            LoggedInUser loggedInUser = new LoggedInUser(user.getUsername()+"",
                    user.getEmail()+"", user.getFull_name()+"");
            Session.setLoggedInUser(loggedInUser);
            signUpResult.setValue(new AuthResult(new LoggedInUserView(user.getFull_name())));
        }catch(Exception ex){
            signUpResult.setValue(new AuthResult("An error occurred while the user was signing up"));
        }
    }

    private boolean checkUsernameAlreadyExists(String username){
        /*for(Map.Entry<String,User> entry: usersMap.entrySet()){
            if(entry.getValue().getUsername().equals(username)){
                return true;
            }
        }*/
        return false;
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public List<Place> getPlaces(){
        List<Place> places = new ArrayList<>();
        /*for (Map.Entry<String, Place> entry: placesMap.entrySet()){
            places.add(entry.getValue());
        }*/
        return places;
    }

    public List<Place> getPlacesByName(String name){
        List<Place> places = new ArrayList<>();
        /*for(Map.Entry<String, Place> entry: placesMap.entrySet()){
            if(entry.getValue().getName().toLowerCase().equals(name.toLowerCase())){
                places.add(entry.getValue());
            }
        }*/
        return places;
    }

    public List<Place> getPlacesFromUser(User user) {
        List<Place> places = new ArrayList<>();
        /*for(Map.Entry<String, Place> entry: placesMap.entrySet()){
            if(entry.getValue().getAuthor().getEmail().equals(user.getEmail())){
                places.add(entry.getValue());
            }
        }*/
        return places;
    }

    public Result savePlace(Place place) {
        try{
            //lacesMap.put(place.getPlaceId(), place);
            return new Result.Success<>("Place was successfully saved");
        }catch(Exception ex){
            return new Result.Error(new IOException("Whoops!!!. An error occurred while saving place."));
        }
    }

    public Place getPlaceById(String placeId){
        /*if(placesMap.containsKey(placeId)){
            return placesMap.get(placeId);
        }*/
        return null;
    }

    public Result updatePlace(Place place){
        /*if(placesMap.containsKey(place.getPlaceId())){
            placesMap.put(place.getPlaceId(), place);
            return new Result.Success<>("Place was successfully updated");
        }else{
            return new Result.Error(new IOException("Whoops!!!. There is no place registered with the specified id"));
        }*/
        return null;
    }

    public Result deletePlace(String placeId) {
        /*if(placesMap.containsKey(placeId)){
            placesMap.remove(placeId);
            return new Result.Success<>("Place was successfully deleted");
        }else{
            return new Result.Error(new IOException("Whoops!!!. There is no place registered with the specified id"));
        }*/
        return null;
    }

}
