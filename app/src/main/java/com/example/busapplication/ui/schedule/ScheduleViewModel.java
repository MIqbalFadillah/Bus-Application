package com.example.busapplication.ui.schedule;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.busapplication.data.api.ApiServices;
import com.example.busapplication.data.api.NetworkService;
import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.repository.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends ViewModel {
    private MutableLiveData<List<JadwalItems>> listJadwal = new MutableLiveData<>();

    private SessionManager sessionManager;
    private List <JadwalItems> jadwalItemsList;
    private Context context;
    private String fetchToken;
    private int fetchId;
//    private HomeAdapter adapter;

    //    //MVVM
    void GetDataJadwal(String fetchToken, int fetchId){

//        SessionManager sessionManager = new SessionManager(context);

//        fetchToken = sessionManager.sharedPreferences.getString(USER_TOKEN,"");
//        fetchId = sessionManager.sharedPreferences.getInt(USER_ID,0);

        ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);
        apiServices.getJadwal(fetchId,fetchToken).enqueue(new Callback<List<JadwalItems>>() {
            @Override
            public void onResponse(Call<List<JadwalItems>> call, Response<List<JadwalItems>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    jadwalItemsList = response.body();
                    listJadwal.postValue(jadwalItemsList);
                }
                else{
                    Toast.makeText(context,"Failed Load Data !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JadwalItems>> call, Throwable t) {
                Toast.makeText(context,"Error\n"+toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    LiveData<List<JadwalItems>> getJadwalList() {
        return listJadwal;
    }
}
