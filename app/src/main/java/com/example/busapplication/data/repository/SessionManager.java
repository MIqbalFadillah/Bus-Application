package com.example.busapplication.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.busapplication.data.model.LoginResponse;

public class SessionManager {

    public static String USER_PREF = "user_pref";
    public static String USER_TOKEN = "user_token";
    public static String USER_ID = "user_id" ;
    public static String USER_NAME = "user_name";


    public final SharedPreferences sharedPreferences;


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF,Context.MODE_PRIVATE);
    }

    public void saveAuthToken(LoginResponse mResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, mResponse.token );
        editor.putString(USER_NAME, mResponse.username );
        editor.putInt(USER_ID , mResponse.id );
        editor.apply();

    }


}
