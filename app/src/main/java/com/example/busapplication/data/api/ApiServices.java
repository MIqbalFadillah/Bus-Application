package com.example.busapplication.data.api;

import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.model.LoginRequest;
import com.example.busapplication.data.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServices {
    @POST("auth/login")
    Call<LoginResponse> getToken(@Body LoginRequest loginRequest);
    @GET("jadwal")
    Call<List<JadwalItems>> getJadwal(@Header("User-ID") int id,
                                     @Header("Token") String token);
}
