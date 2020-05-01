package com.app.travelapp.data.datasources;

import com.app.travelapp.data.model.User;
import com.app.travelapp.utils.Result;
import com.app.travelapp.data.model.LoggedInUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSourceCache {

    private static Map<String, User> usersMp;
    private User loggedUser;
    private static DataSourceCache instance;

    private DataSourceCache(){
        usersMp = new HashMap<>();
        User user = new User("camilo@mail.com", "camilo123", "juan camilo");
        usersMp.put(user.getEmail(), user);
    }

    public static DataSourceCache getInstance(){
        if(instance==null){
            instance = new DataSourceCache();
        }
        return instance;
    }

    // Users
    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            if(usersMp.containsKey(email) && usersMp.get(email).getPassword().equals(password)){
                loggedUser = usersMp.get(email);
                //String first_name = loggedUser.getFull_name().split(" ")[0];
                LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), loggedUser.getFull_name());
                return new Result.Success<>(loggedInUser);
            }
            return new Result.Error(new IOException("Invalid login"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> signUp(User user){
        if(usersMp.containsKey(user.getEmail())){
            return new Result.Error(new IOException("Email has been already taken!"));
        }
        loggedUser = user;
        usersMp.put(user.getEmail(), user);
        //String first_name = loggedUser.getFull_name().split(" ")[0];
        LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getEmail(), loggedUser.getFull_name());
        return new Result.Success<>(loggedInUser);
    }

    public void logout() {
        // TODO: revoke authentication
    }


}
