package com.app.travelapp.data.datasources;

import android.content.Context;

public class DataSourceFirebase {

    private static DataSourceFirebase instance;
    private Context context;

    private DataSourceFirebase(Context context){
        this.context = context;
    }

    public static DataSourceFirebase getInstance(Context context){
        if(instance == null){
            instance = new DataSourceFirebase(context);
        }
        return instance;
    }

}
