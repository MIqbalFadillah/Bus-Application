package com.example.busapplication.data.model;

import com.google.gson.annotations.SerializedName;

public class SeatItems {
    @SerializedName("id_seat")
    private String id_seat;
    @SerializedName("kapasitas_kursi")
    private int kapasitas_kursi;
    @SerializedName("jenis_kursi")
    private int jenis_kursi;
    @SerializedName("kode_seat")
    private String kode_seat;


    public String getId_seat() {
        return id_seat;
    }

    public void setId_seat(String id_seat) {
        this.id_seat = id_seat;
    }

    public int getKapasitas_kursi() {
        return kapasitas_kursi;
    }

    public void setKapasitas_kursi(int kapasitas_kursi) {
        this.kapasitas_kursi = kapasitas_kursi;
    }

    public int getJenis_kursi() {
        return jenis_kursi;
    }

    public void setJenis_kursi(int jenis_kursi) {
        this.jenis_kursi = jenis_kursi;
    }

    public String getKode_seat() {
        return kode_seat;
    }

    public void setKode_seat(String kode_seat) {
        this.kode_seat = kode_seat;
    }
}
