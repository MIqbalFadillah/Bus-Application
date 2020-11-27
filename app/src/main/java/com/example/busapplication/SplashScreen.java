package com.example.busapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.busapplication.ui.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private int time_load=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Login = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(Login);
                finish();
            }
        }, time_load);


    }
}