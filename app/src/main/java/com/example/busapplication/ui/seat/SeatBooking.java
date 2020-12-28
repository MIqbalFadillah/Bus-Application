package com.example.busapplication.ui.seat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.busapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SeatBooking extends AppCompatActivity implements OnSeatSelected  {

    private static final int COLUMNS = 6;
    private TextView txtSeatSelected,txtSeatCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_booking);

        List<AbstractItem> items = new ArrayList<>();
        for (int i = 0; i<60; i++){
//            count = count + i;
            if (i%COLUMNS==0 || i%COLUMNS==4){
                items.add(new EdgeItem(String.valueOf(i)));
            }
            else if (i%COLUMNS==1 || i%COLUMNS==3){
                items.add(new CenterItem(String.valueOf(i)));
            }
            else if (i%COLUMNS==1 || i%COLUMNS==5){
                items.add(new CenterItem(String.valueOf(i)));
            }
            else if (i == 5){
                items.add(new CenterItem(String.valueOf(i)));
            }
            else{
                items.add(new EmptyItem(String.valueOf(i)));
            }

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,COLUMNS);
            RecyclerView recyclerView = findViewById(R.id.lst_items);
            recyclerView.setLayoutManager(gridLayoutManager);
            SeatBookingAdapter adapter = new SeatBookingAdapter(this, items);
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void onSeatSelected(int count){
        txtSeatSelected.setText("Book"+count+"Seat");
    }
}