package com.example.busapplication.data.api;

import com.example.busapplication.data.model.LoginRequest;
import com.example.busapplication.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {
    @POST("auth/login")
    Call<LoginResponse> getToken(@Body LoginRequest loginRequest);
}
