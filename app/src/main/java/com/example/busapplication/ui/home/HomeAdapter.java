package com.example.busapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busapplication.R;
import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.ui.schedule.ScheduleActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<JadwalItems> jadwalItemsList;
    private Context context;
//
    public HomeAdapter(List jadwalItemsList, Context context){
        this.jadwalItemsList = jadwalItemsList;
        this.context = context;
    }


    public void setDataJadwal(List<JadwalItems> dataItemJadwal){
        jadwalItemsList.clear();
        jadwalItemsList.addAll(dataItemJadwal);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_jadwal_list,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        holder.kota_tujuan.setText(jadwalItemsList.get(position).getKota_tujuan());
        holder.nama_bus.setText(jadwalItemsList.get(position).getNama_bus());
        holder.no_polisi.setText(jadwalItemsList.get(position).getNo_polisi());
        holder.tgl_perjalanan.setText(jadwalItemsList.get(position).getTgl_perjalanan());
//        holder.bind(jadwalItemsList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScheduleActivity.class);
                intent.putExtra(ScheduleActivity.EXTRA_NAME_BUS,jadwalItemsList.get(position).getNama_bus());
                intent.putExtra(ScheduleActivity.EXTRA_POLICE_BUS,jadwalItemsList.get(position).getNo_polisi());
                intent.putExtra(String.valueOf(ScheduleActivity.EXTRA_SEAT_BUS),jadwalItemsList.get(position).getJmlh_kursi());
                intent.putExtra(ScheduleActivity.EXTRA_FROM_BUS,jadwalItemsList.get(position).getKota_asal());
                intent.putExtra(ScheduleActivity.EXTRA_TO_BUS,jadwalItemsList.get(position).getKota_tujuan());
                intent.putExtra(ScheduleActivity.EXTRA_DATE_BUS,jadwalItemsList.get(position).getTgl_perjalanan());
                intent.putExtra(ScheduleActivity.EXTRA_TO_BUS,jadwalItemsList.get(position).getKota_tujuan());
                intent.putExtra(ScheduleActivity.EXTRA_ID,jadwalItemsList.get(position).getId_jadwal());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jadwalItemsList.size() ;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView kota_tujuan, nama_bus, no_polisi, tgl_perjalanan;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            kota_tujuan = itemView.findViewById(R.id.mTv_from_to);
            nama_bus = itemView.findViewById(R.id.mTv_jadwal_bus_name);
            no_polisi = itemView.findViewById(R.id.mTv_jadwal_bus_police);
            tgl_perjalanan = itemView.findViewById(R.id.mTv_jadwal_bus_due_date);
        }

//        void bind (JadwalItems jadwalItems){
//            kota_tujuan.setText(jadwalItems.getKota_tujuan());
//            nama_bus.setText(jadwalItems.getNama_bus());
//            no_polisi.setText(jadwalItems.getNama_bus());
//            tgl_perjalanan.setText(jadwalItems.getTgl_perjalanan());
//        }
    }
}
