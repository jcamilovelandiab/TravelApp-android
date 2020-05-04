package com.app.travelapp.data.repositories;

import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.utils.Result;
import com.app.travelapp.data.datasources.DataSourceCache;
import com.app.travelapp.data.datasources.DataSourceFirebase;
import com.app.travelapp.data.model.LoggedInUser;

import java.util.List;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    private DataSourceCache dataSourceCache;
    private DataSourceFirebase dataSourceFirebase;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

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

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSourceCache.logout();
    }

    public LoggedInUser getLoggedInUser(){
        return user;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String email, String password) {
        // handle login
        Result<LoggedInUser> result = dataSourceCache.login(email, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public Result<LoggedInUser> signUp(User user){
        //Result<LoggedInUser> result = dataSourceCache.signUp(user);
        Result<LoggedInUser> result = dataSourceFirebase.signUp(user);
        if(result instanceof Result.Success){
            // the user could signed up and logged in successfully
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public List<Place> getPlacesFromUser(User user){
        return dataSourceCache.getPlacesFromUser(user);
    }

}
