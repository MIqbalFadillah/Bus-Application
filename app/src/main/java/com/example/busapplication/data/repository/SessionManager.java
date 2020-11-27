package com.example.busapplication.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.busapplication.data.model.LoginResponse;

public class SessionManager {

    public static String USER_PREF = "user_pref";
    public static String USER_TOKEN = "user_token";
    public static int USER_ID ;
    public static String USER_NAME = "user_name";


    public final SharedPreferences sharedPreferences;


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF,Context.MODE_PRIVATE);
    }

    public void saveAuthToken(LoginResponse loginResponse){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, loginResponse.token );
        editor.putString(USER_NAME, loginResponse.username );
        editor.putInt(String.valueOf(USER_ID), loginResponse.id );

    }


}
