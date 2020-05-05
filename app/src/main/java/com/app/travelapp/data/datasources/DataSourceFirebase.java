package com.app.travelapp.data.datasources;

import android.content.Context;
import android.util.Log;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    public void getPlaces(MutableLiveData<List<Place>> placesQuery){
        db.collection("/Places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Place> placeList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Place place = documentToPlace(document);
                        //if(place.getAuthor().getEmail().equals(Session.getLoggedInUser().getEmail())) continue;
                        placeList.add(place);
                    }
                    placesQuery.setValue(placeList);
                } else {
                    Log.d("Error", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private Place documentToPlace(QueryDocumentSnapshot document){
        Place place = document.toObject(Place.class);
        HashMap<String, Object> authorHashMap = (HashMap<String, Object>) document.get("author");
        User author = new User(authorHashMap.get("username")+"",
                authorHashMap.get("email")+"",
                authorHashMap.get("full_name")+"");
        place.setAuthor(author);
        place.setPlaceId(document.getId());
        return place;
    }

    public void getPlacesFromUser(User user, MutableLiveData<List<Place>> placesQuery){
        db.collection("/Places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Place> placeList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Place place = documentToPlace(document);
                        if(place.getAuthor().getEmail().equals(Session.getLoggedInUser().getEmail())){
                            placeList.add(place);
                        }
                    }
                    placesQuery.setValue(placeList);
                } else {
                    Log.d("Error", "Error getting documents: ", task.getException());
                }
            }
        });
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

    public void savePlace(Place place, MutableLiveData<BasicResult> addResult){
        //
        Map<String, Object> placeData = new HashMap<>();
        placeData.put("name", place.getName());
        placeData.put("description", place.getDescription());
        placeData.put("address", place.getAddress());
        placeData.put("images", place.getImages());
        Map<String, Object> authorData = new HashMap<>();
        authorData.put("email", place.getAuthor().getEmail());
        authorData.put("username", place.getAuthor().getUsername());
        authorData.put("full_name", place.getAuthor().getFull_name());
        placeData.put("author", authorData);
        db.collection("Places").document().set(placeData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addResult.setValue(new BasicResult("Place was successfully saved"));
                        Log.d("Place saved", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        addResult.setValue(new BasicResult(R.string.save_place_failed));
                        Log.w("Error saving place", "Error writing document", e);
                    }
                });
    }

    public Place getPlaceById(String placeId){
        /*if(placesMap.containsKey(placeId)){
            return placesMap.get(placeId);
        }*/
        return null;
    }

    public void updatePlace(Place place, MutableLiveData<BasicResult> updatePlaceResult){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("address", place.getAddress());
        hashMap.put("description", place.getDescription());
        db.collection("/Places").document(place.getPlaceId()).update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updatePlaceResult.setValue(new BasicResult("Place was successfully updated!"));
                }else{
                    updatePlaceResult.setValue(new BasicResult(R.string.update_place_failed));
                }
            }
        });
    }

    public void deletePlace(String placeId, MutableLiveData<BasicResult> deletePlaceResult) {
        db.collection("/Places").document(placeId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                deletePlaceResult.setValue(new BasicResult("Place was deleted successfully!"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deletePlaceResult.setValue(new BasicResult(R.string.delete_place_failed));
            }
        });
    }

    public void getPlaceById(String placeId, MutableLiveData<Place> placeQuery) {
        db.collection("/Places").document(placeId).get().addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Place place = document.toObject(Place.class);
                    HashMap<String, Object> authorHashMap = (HashMap<String, Object>) document.get("author");
                    User author = new User(authorHashMap.get("username")+"",
                            authorHashMap.get("email")+"",
                            authorHashMap.get("full_name")+"");
                    place.setAuthor(author);
                    place.setPlaceId(document.getId());
                    placeQuery.setValue(place);
                }
            }
        });
    }
}