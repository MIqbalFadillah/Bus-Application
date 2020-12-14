package com.example.busapplication.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.busapplication.R;
import com.example.busapplication.data.api.ApiServices;
import com.example.busapplication.data.api.NetworkService;
import com.example.busapplication.data.model.SeatItems;
import com.example.busapplication.data.repository.SessionManager;
import com.example.busapplication.ui.home.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.busapplication.data.repository.SessionManager.USER_ID;
import static com.example.busapplication.data.repository.SessionManager.USER_TOKEN;

public class ScheduleActivity extends AppCompatActivity {

    EditText mEdtNameBus,mEdtPoliceBus,mEdtfromTo,mEdtGoTo
            , mEdtDateGo, mCountSeat;
    Button mBtnJadwalAdd;
    Spinner spinner;
    RecyclerView recyclerView;
    private HomeAdapter adapter;

    private String fetchToken;
    private int fetchId;

    private String getSeat;
    private List<SeatItems> seatItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initView();
        SessionManager sessionManager = new SessionManager(this);
        fetchId = sessionManager.sharedPreferences.getInt(USER_ID,0);
        fetchToken = sessionManager.sharedPreferences.getString(USER_TOKEN, "");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("22");
        categories.add("23");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(spinnerButton);


    }




    //==============Spinner Button==============
    AdapterView.OnItemSelectedListener spinnerButton = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            switch (position) {
                case 0:
                    getSeat = parent.getItemAtPosition(position).toString();
                    fetchDataSeat();
                    break;
                case 1:
                    getSeat = parent.getItemAtPosition(position).toString();

                    break;


            }

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + getSeat, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void fetchDataSeat(){

        ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);
        apiServices.getSeat(fetchId,fetchToken,getSeat).enqueue(new Callback<List<SeatItems>>() {
            @Override
            public void onResponse(Call<List<SeatItems>> call, Response<List<SeatItems>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    seatItems = response.body();


                }
                else{
                    Toast.makeText(ScheduleActivity.this,"Failed Load Data !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SeatItems>> call, Throwable t) {

            }
        });

    }

        private void initView(){
        mEdtNameBus = findViewById(R.id.edt_sc_name_bus);
        mEdtPoliceBus = findViewById(R.id.edt_sc_police_bus);
        mEdtfromTo = findViewById(R.id.edt_sc_from_to);
        mEdtGoTo = findViewById(R.id.edt_sc_go_to);
        mEdtDateGo = findViewById(R.id.edt_sc_date_bus_go);
        mBtnJadwalAdd = findViewById(R.id.btn_jadwal_add);
        mCountSeat = findViewById(R.id.edt_sc_seat_bus);
        spinner = findViewById(R.id.spinner_Seat);
    }
}