package com.app.travelapp.data.datasources;


import android.content.Context;
import android.content.SharedPreferences;

import com.app.travelapp.R;
import com.app.travelapp.data.model.LoggedInUser;
import com.app.travelapp.data.model.Role;


public class Session {

    private final String TOKEN_KEY = "TOKEN_KEY";
    //private final SharedPreferences sharedPreferences;
    private static LoggedInUser loggedInUser;
    private static Role role;

    /*public Session(Context context ){
        this.sharedPreferences =
                context.getSharedPreferences( context.getString(R.string.preference_file_key), Context.MODE_PRIVATE );
    }*/


    public static LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(LoggedInUser loggedInUser) {
        Session.loggedInUser = loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static Role getRole() {
        return role;
    }

    public static void setRole(Role role) {
        Session.role = role;
    }
}
