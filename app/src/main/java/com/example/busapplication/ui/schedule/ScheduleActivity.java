package com.example.busapplication.ui.schedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busapplication.MainActivity;
import com.example.busapplication.R;
import com.example.busapplication.data.api.ApiServices;
import com.example.busapplication.data.api.NetworkService;
import com.example.busapplication.data.model.JadwalItems;
import com.example.busapplication.data.model.SeatItems;
import com.example.busapplication.data.repository.SessionManager;
import com.example.busapplication.ui.home.HomeAdapter;
import com.example.busapplication.ui.home.HomeFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.busapplication.data.repository.SessionManager.USER_ID;
import static com.example.busapplication.data.repository.SessionManager.USER_NAME;
import static com.example.busapplication.data.repository.SessionManager.USER_TOKEN;

public class ScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private AppBarConfiguration mAppBarConfiguration;

    EditText mEdtNameBus,mEdtPoliceBus,mEdtfromTo,mEdtGoTo
            , mEdtDateGo, mCountSeat, mIdJadwal;
    Button mBtnJadwal;
    Spinner spinner;
    TextView tst;

    Toolbar toolbar;

    private String id_jadwal,nama_bus,no_polisi,kota_asal,
            kota_tujuan, jadwal_perjalanan,fetchIdSeat,cnvrtJmlh_kursi;

    private String fetchToken,fetchName;
    private int fetchId,jumlah_kursi;
    private int fetchCountSeat;

    private String getSeat, fetchDate, fetchTime;
    private SeatItems seatItems;


    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_ALL = "extra_all";
    public static String EXTRA_NAME_BUS = "extra_model";
    public static String EXTRA_POLICE_BUS = "extra_police";
    public static String EXTRA_TIPE_BUS = "extra_tipe";
    public static int EXTRA_SEAT_BUS = 0;
    public static String EXTRA_FROM_BUS = "extra_from";
    public static String EXTRA_TO_BUS = "extra_to";
    public static String EXTRA_DATE_BUS = "extra_date";



    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        SessionManager sessionManager = new SessionManager(this);
        fetchId = sessionManager.sharedPreferences.getInt(USER_ID,0);
        fetchToken = sessionManager.sharedPreferences.getString(USER_TOKEN, "");
        fetchName = sessionManager.sharedPreferences.getString(USER_NAME, "");

        initView();
        initBindHolder();
        if (nama_bus != null || no_polisi != null){
            isEdit = true;
        }
        String actionBarTittle = null;

        if (isEdit){
            actionBarTittle = "Ubah";
            mBtnJadwal.setText("Ubah");
            bindView();
            mBtnJadwal.setOnClickListener(updateData);
            mIdJadwal.setFocusable(false);

        }else {
            actionBarTittle = "Tambah";
            mBtnJadwal.setText("Simpan");
            mIdJadwal.setText("ID akan Terinput Otomatis...");
            mIdJadwal.setFocusable(false);
            mBtnJadwal.setOnClickListener(saveData);

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// ...
// Display icon in the toolbar
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
////        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setTitle(actionBarTittle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pilih Jenis Kursi");
        categories.add("2-2");
        categories.add("2-3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(spinnerButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if (isEdit){

            getMenuInflater().inflate(R.menu.menu, menu);
//            MenuItem itemSearch = menu.findItem(R.id.action_setting);
//            itemSearch.setVisible(false);
//            getMenuInflater().inflate(R.menu.menu, menu);
            return true;

        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed(){
        showAlertDialog(ALERT_DIALOG_CLOSE);
        Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    final int ALERT_DIALOG_DELETE = 20;
    final int ALERT_DIALOG_CLOSE = 10;
    /*
    Konfirmasi dialog sebelum proses batal atau hapus
    close = 10
    delete = 20
     */

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle = null, dialogMessage = null;

        if (isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        }else {
            dialogMessage = "Apakah anda yakin ingin menghapus SN:"+mIdJadwal.getText()+" ini?";
            dialogTitle = "Hapus Data";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose){
                            finish();
                        }else {
                            DeletedData();
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




    //==============Spinner Button==============
    AdapterView.OnItemSelectedListener spinnerButton = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            switch (position) {
                case 0:
                    getSeat = parent.getItemAtPosition(position).toString();
                    break;
                case 1:
                    getSeat = parent.getItemAtPosition(position).toString();
                    fetchDataSeat();
                    break;
                case 2:
                    getSeat = parent.getItemAtPosition(position).toString();
                    fetchDataSeat();
                    break;

            }

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + getSeat, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

//======================= Mencari Tipe Kursi===================
    private void fetchDataSeat(){

        ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);
        apiServices.getSeat(fetchId,fetchToken,getSeat).enqueue(new Callback<SeatItems>() {
            @Override
            public void onResponse(Call<SeatItems> call, Response<SeatItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    seatItems = response.body();
                    fetchCountSeat = seatItems.getKapasitas_kursi();
                    fetchIdSeat = seatItems.getId_seat();
                    mCountSeat.setText(String.valueOf(fetchCountSeat));

                }
                else {
                    Toast.makeText(ScheduleActivity.this,"FAILED LOAD DATA SEAT!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SeatItems> call, Throwable t) {
                Toast.makeText(ScheduleActivity.this,"CONNECTION FAILED GET DATA SEAT!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

//===========================Updated Data Jadwal=======================
    View.OnClickListener updateData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

//===========================Simpan Data Jadwal===========================
    View.OnClickListener saveData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mEdtNameBus.getText() == null){
                mEdtNameBus.setSelectAllOnFocus(true);
                mEdtNameBus.selectAll();
                Toast.makeText(ScheduleActivity.this, "Data Kosong, Isi Terlebih Dahulu!!!", Toast.LENGTH_SHORT).show();
            }
            else {
                bindView();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created_at = df.format(c.getTime());
                String updated_at =  null;
//                jumlah_kursi = Integer.parseInt(cnvrtJmlh_kursi);



                JadwalItems items = new JadwalItems(fetchId,nama_bus,no_polisi,kota_asal,kota_tujuan,
                        fetchCountSeat,jadwal_perjalanan,fetchIdSeat,created_at,updated_at
                        ,fetchName);

                ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);
                apiServices.createJadwal(fetchId,fetchToken,items).enqueue(new Callback<JadwalItems>() {
                    @Override
                    public void onResponse(Call<JadwalItems> call, Response<JadwalItems> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ScheduleActivity.this, "DATA HAS BEN SAVED...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ScheduleActivity.this, "FAILED DATA SAVED...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JadwalItems> call, Throwable t) {
                        Toast.makeText(ScheduleActivity.this, "CONNECTION FAILURE....", Toast.LENGTH_SHORT).show();

                    }
                });



            }

        }
    };

//=====================Menghapus Data================
    private void DeletedData(){

    }


//=====================DateTimePicker================

    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        fetchTime = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;

    }


    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         fetchDate = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

    }
//====================DateTimeListener=======================
    View.OnClickListener fetchDateTime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    ScheduleActivity.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dpd.show(onTimeSet());
        }
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void initView(){
        mIdJadwal = findViewById(R.id.edt_sc_id_jadwal);
        mEdtNameBus = findViewById(R.id.edt_sc_name_bus);
        mEdtPoliceBus = findViewById(R.id.edt_sc_police_bus);
        mEdtfromTo = findViewById(R.id.edt_sc_from_to);
        mEdtGoTo = findViewById(R.id.edt_sc_go_to);
        mEdtDateGo = findViewById(R.id.edt_sc_date_bus_go);
        mBtnJadwal = findViewById(R.id.btn_jadwal_add);
        mCountSeat = findViewById(R.id.edt_sc_seat_bus);
        spinner = findViewById(R.id.spinner_Seat);

    }

    private void bindView(){
        id_jadwal = mIdJadwal.getText().toString().trim();
        nama_bus = mEdtNameBus.getText().toString().trim();
        no_polisi = mEdtPoliceBus.getText().toString().trim();
        kota_asal = mEdtfromTo.getText().toString().trim();
        kota_tujuan = mEdtGoTo.getText().toString().trim();
//        jadwal_perjalanan = mEdtDateGo.getText().toString().trim();
        mEdtDateGo.setOnClickListener(fetchDateTime);
        Integer i = jumlah_kursi;
        cnvrtJmlh_kursi = i.toString();
        cnvrtJmlh_kursi = mCountSeat.getText().toString().trim();

        return;
    }


    private void initBindHolder(){
        id_jadwal = getIntent().getStringExtra(EXTRA_ID);
        nama_bus = getIntent().getStringExtra(EXTRA_NAME_BUS);
        no_polisi = getIntent().getStringExtra(EXTRA_POLICE_BUS);
        jumlah_kursi = getIntent().getIntExtra(String.valueOf(EXTRA_SEAT_BUS),0);
        kota_asal = getIntent().getStringExtra(EXTRA_FROM_BUS);
        kota_tujuan = getIntent().getStringExtra(EXTRA_TO_BUS);
        jadwal_perjalanan = getIntent().getStringExtra(EXTRA_DATE_BUS);

        mIdJadwal.setText(id_jadwal);
        mEdtNameBus.setText(nama_bus);
        mEdtPoliceBus.setText(no_polisi);
        mEdtfromTo.setText(kota_asal);
        mEdtGoTo.setText(kota_tujuan);
        mEdtDateGo.setText(jadwal_perjalanan);

    }


}