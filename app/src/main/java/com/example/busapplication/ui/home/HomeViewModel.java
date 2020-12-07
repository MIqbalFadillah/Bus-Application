package com.example.busapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.busapplication.data.model.JadwalItems;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<JadwalItems>> listJadwal = new MutableLiveData<>();

}