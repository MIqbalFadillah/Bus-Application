package com.example.busapplication.data.model;

import com.google.gson.annotations.SerializedName;

public class JadwalItems {
    @SerializedName("id_jadwal")
    private String id_jadwal;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("nama_admin")
    private String nama_admin;
    @SerializedName("nama_bus")
    private String nama_bus;
    @SerializedName("no_polisi")
    private String no_polisi;
    @SerializedName("id_seat")
    private String id_seat;
    @SerializedName("jmlh_kursi")
    private int jmlh_kursi;
    @SerializedName("tgl_perjalanan")
    private String tgl_perjalanan;
    @SerializedName("kota_asal")
    private String kota_asal;
    @SerializedName("kota_tujuan")
    private String kota_tujuan;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public JadwalItems(Integer id_user,String nama_bus,String no_polisi,
                       String kota_asal,String kota_tujuan,Integer jmlh_kursi,
                       String tgl_perjalanan,String id_seat,
                       String created_at,String updated_at,String nama_admin){

        this.id_user =  id_user;
        this.nama_bus = nama_bus;
        this.id_seat = id_seat;
        this.nama_admin = nama_admin;
        this.no_polisi = no_polisi;
        this.jmlh_kursi = jmlh_kursi;
        this.tgl_perjalanan = tgl_perjalanan;
        this.kota_asal = kota_asal;
        this.kota_tujuan = kota_tujuan;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }


    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_admin() {
        return nama_admin;
    }

    public void setNama_admin(String nama_admin) {
        this.nama_admin = nama_admin;
    }

    public String getNama_bus() {
        return nama_bus;
    }

    public void setNama_bus(String nama_bus) {
        this.nama_bus = nama_bus;
    }

    public String getNo_polisi() {
        return no_polisi;
    }

    public void setNo_polisi(String no_polisi) {
        this.no_polisi = no_polisi;
    }

    public String getId_seat() {
        return id_seat;
    }

    public void setId_seat(String id_seat) {
        this.id_seat = id_seat;
    }

    public int getJmlh_kursi() {
        return jmlh_kursi;
    }

    public void setJmlh_kursi(int jmlh_kursi) {
        this.jmlh_kursi = jmlh_kursi;
    }

    public String getKota_asal() {
        return kota_asal;
    }

    public void setKota_asal(String kota_asal) {
        this.kota_asal = kota_asal;
    }

    public String getKota_tujuan() {
        return kota_tujuan;
    }

    public void setKota_tujuan(String kota_tujuan) {
        this.kota_tujuan = kota_tujuan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTgl_perjalanan() {
        return tgl_perjalanan;
    }

    public void setTgl_perjalanan(String tgl_perjalanan) {
        this.tgl_perjalanan = tgl_perjalanan;
    }

//=============Public Input Class=================

}
