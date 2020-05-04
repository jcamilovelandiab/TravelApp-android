package com.app.travelapp.data.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.app.travelapp.data.datasources.Session;
import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.ui.auth.AuthResult;
import com.app.travelapp.ui.auth.LoggedInUserView;
import com.app.travelapp.utils.Result;
import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.model.LoggedInUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;

    // private constructor : singleton access
    private UserRepository(DataSourceCache dataSourceCache, DataSourceFirebase dataSourceFirebase) {
        this.dataSourceCache = dataSourceCache;
        this.dataSourceFirebase = dataSourceFirebase;
    }

    public static UserRepository getInstance(DataSourceCache dataSourceCache, DataSourceFirebase dataSourceFirebase) {
        if (instance == null) {
            instance = new UserRepository(dataSourceCache, dataSourceFirebase);
        }
        return instance;
    }


    public Result<LoggedInUser> login(String email, String password) {
        // handle login
        // FOR CACHE
        Result<LoggedInUser> result = dataSourceCache.login(email, password);
        if (result instanceof Result.Success) {
            LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>) result).getData();
            Session.setLoggedInUser(loggedInUser);
        }
        //return dataSourceFirebase.login(email, password);
        return result;
    }

    public void login(String email, String password, MutableLiveData<AuthResult> loginResult){
        dataSourceFirebase.login(email, password, loginResult);
    }

    public Result<LoggedInUser> signUp(User user){
        Result<LoggedInUser> result = dataSourceCache.signUp(user);
        if(result instanceof Result.Success){
            // the user could signed up and logged in successfully
            LoggedInUser loggedInUser = ((Result.Success<LoggedInUser>) result).getData();
            Session.setLoggedInUser(loggedInUser);
        }
        return result;
    }

    public void signUp(User user, MutableLiveData<AuthResult> signUpResult){
        dataSourceFirebase.signUp(user, signUpResult);
    }

    public List<Place> getPlacesFromUser(User user){
        return dataSourceCache.getPlacesFromUser(user);
    }

    public void getPlacesFromUser(User user, MutableLiveData<List<Place>> placesQuery){
        dataSourceFirebase.getPlacesFromUser(user, placesQuery);
    }

}
