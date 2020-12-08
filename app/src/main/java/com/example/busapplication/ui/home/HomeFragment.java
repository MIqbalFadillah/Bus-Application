package com.example.busapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busapplication.R;
import com.example.busapplication.data.api.ApiServices;
import com.example.busapplication.data.api.NetworkService;
import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.repository.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.busapplication.data.repository.SessionManager.USER_ID;
import static com.example.busapplication.data.repository.SessionManager.USER_TOKEN;

public class HomeFragment extends Fragment {

    private String fetchToken;
    private int fetchId;
    private Context context;

    private RecyclerView rv_jadwal_data;
    private View view;
    private List <JadwalItems> jadwalItemsList;
    private ProgressBar progressBar;

    private HomeViewModel homeViewModel;

    private HomeAdapter adapter;



    public HomeFragment(){
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        context = view.getContext();

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager != null){
            fetchToken = sessionManager.sharedPreferences.getString(USER_TOKEN,"");
            fetchId = sessionManager.sharedPreferences.getInt(USER_ID,0);
        }
        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);


        rv_jadwal_data = view.findViewById(R.id.rv_jadwal_list);
        rv_jadwal_data.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new HomeAdapter(jadwalItemsList,getActivity());
        adapter.notifyDataSetChanged();
        rv_jadwal_data.setHasFixedSize(true);
        rv_jadwal_data.setAdapter(adapter);

        initView();
        showLoading(true);

        homeViewModel.setFetchToken(fetchToken,fetchId);
        homeViewModel.GetDataJadwal(jadwalItemsList);
        getJadwal();


        //loadDataUrl();



        return view;
    }

    private void getJadwal(){
        homeViewModel.getJadwalList().observe(this, new Observer<List<JadwalItems>>() {
            @Override
            public void onChanged(List<JadwalItems> jadwalItems) {
                if (jadwalItems != null){
                    adapter.setDataJadwal(jadwalItems);
                    showLoading(false);
                }
                else {
                    Toast.makeText(getActivity(),"Failed Load Data !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadDataUrl() {

        ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);
        apiServices.getJadwal(fetchId,fetchToken).enqueue(new Callback<List<JadwalItems>>() {
            @Override
            public void onResponse(Call<List<JadwalItems>> call, Response<List<JadwalItems>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    showLoading(false);
                    jadwalItemsList = response.body();
//                    adapter = new HomeAdapter(jadwalItemsList, getActivity());
                    rv_jadwal_data.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(getActivity(),"Failed Load Data !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JadwalItems>> call, Throwable t) {
                Toast.makeText(getActivity(),"Error\n"+toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void initView(){
        progressBar = view.findViewById(R.id.progressBar);
    }
}