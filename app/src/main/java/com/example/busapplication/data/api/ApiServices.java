package com.example.busapplication.data.api;

import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.model.LoginRequest;
import com.example.busapplication.data.model.LoginResponse;
import com.example.busapplication.data.model.SeatItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {
    @POST("auth/login")
    Call<LoginResponse> getToken(@Body LoginRequest loginRequest);
    @GET("kursi/detail/{query}")
    Call<SeatItems> getSeat(@Header("User-ID") int id,
                                  @Header("Token") String token,
                                  @Path("query") String query);
    @GET("jadwal")
    Call<List<JadwalItems>> getJadwal(@Header("User-ID") int id,
                                      @Header("Token") String token);

    @POST("jadwal/create")
    Call<JadwalItems> createJadwal(@Header("User-ID") int id,
                                   @Header("Token") String token,
                                   @Body JadwalItems jadwalItems);

    @PUT("jadwal/update/{query}")
    Call<JadwalItems> updateJadwal(@Header("User-ID") int id,
                                   @Header("Token") String token,
                                   @Path("query") String query,
                                   @Body JadwalItems jadwalItems);

    @DELETE("jadwal/delete/{query}")
    Call<JadwalItems> deleteJadwal(@Header("User-ID") int id,
                                   @Header("Token") String token,
                                   @Path("query") String query);
}
