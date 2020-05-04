package com.app.travelapp.data.datasources;


import android.content.Context;
import android.content.SharedPreferences;

import com.app.travelapp.R;
import com.app.travelapp.data.model.LoggedInUser;


public class Session {

    private final String TOKEN_KEY = "TOKEN_KEY";
    //private final SharedPreferences sharedPreferences;
    private static LoggedInUser loggedUser;

    /*public Session(Context context ){
        this.sharedPreferences =
                context.getSharedPreferences( context.getString(R.string.preference_file_key), Context.MODE_PRIVATE );
    }*/

    public static LoggedInUser getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(LoggedInUser loggedUser) {
        Session.loggedUser = loggedUser;
    }
}
