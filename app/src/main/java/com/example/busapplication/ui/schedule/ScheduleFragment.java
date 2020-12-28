package com.example.busapplication.ui.schedule;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.busapplication.R;
import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.repository.SessionManager;
import com.example.busapplication.ui.home.HomeAdapter;
import com.example.busapplication.ui.home.HomeViewModel;

import java.util.List;

import static com.example.busapplication.data.repository.SessionManager.USER_ID;
import static com.example.busapplication.data.repository.SessionManager.USER_TOKEN;

public class ScheduleFragment extends Fragment {

    private String fetchToken;
    private int fetchId;
    private Context context;

    private RecyclerView rv_jadwal_data;
    private View view;
    private List<JadwalItems> jadwalItemsList;
    private ProgressBar progressBar;

    private ScheduleViewModel scheduleViewModel;

    private HomeAdapter adapter;


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule,container,false);
        context = view.getContext();

        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager != null){
            fetchToken = sessionManager.sharedPreferences.getString(USER_TOKEN,"");
            fetchId = sessionManager.sharedPreferences.getInt(USER_ID,0);
        }
        scheduleViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ScheduleViewModel.class);


        rv_jadwal_data = view.findViewById(R.id.rv_jadwal_list);
        rv_jadwal_data.setLayoutManager(new LinearLayoutManager(getActivity()));

//        adapter = new HomeAdapter(jadwalItemsList,getActivity());
//        adapter.notifyDataSetChanged();
        rv_jadwal_data.setHasFixedSize(true);
        rv_jadwal_data.setAdapter(adapter);

        initView();
        showLoading(true);

        scheduleViewModel.GetDataJadwal(fetchToken,fetchId);
        viewModelJadwal();


        //loadDataUrl();

        return view;
    }

    private void viewModelJadwal(){
        scheduleViewModel.getJadwalList().observe(this, new Observer<List<JadwalItems>>() {
            @Override
            public void onChanged(List<JadwalItems> jadwalItems) {
                if (jadwalItems != null){
                    adapter = new HomeAdapter(jadwalItems, getActivity());
                    rv_jadwal_data.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    showLoading(false);
                }
                else {
                    Toast.makeText(getActivity(),"Failed Load Data !!!", Toast.LENGTH_SHORT).show();
                }
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