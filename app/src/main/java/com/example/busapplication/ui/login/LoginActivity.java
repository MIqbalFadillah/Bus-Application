package com.example.busapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.busapplication.MainActivity;
import com.example.busapplication.R;
import com.example.busapplication.data.api.ApiServices;
import com.example.busapplication.data.api.NetworkService;
import com.example.busapplication.data.model.LoginRequest;
import com.example.busapplication.data.model.LoginResponse;
import com.example.busapplication.data.repository.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText mEdtEmail, mEdtPassword;
    private Button mBtnLogin, mBtnRegister;
    private ConstraintLayout bottom_sheet;
    private SessionManager sessionManager;
    private LoginResponse loginResponse;

    //Shareprefered Declare
    private String sToken;
    private int sId = 0;
    private String sUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        showLoading(false);
        mBtnLogin.setOnClickListener(LoginProcess);

    }

    View.OnClickListener LoginProcess =(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoading(true);
            loginAPI();

        }
    });


    private void loginAPI(){
        final String user_email = mEdtEmail.getText().toString();
              String user_password = mEdtPassword.getText().toString();

        sessionManager = new SessionManager(this);

        if (user_email.isEmpty() || user_password.isEmpty()){
            showLoading(false);
            Toast.makeText(LoginActivity.this, "email user or password blank!!!", Toast.LENGTH_SHORT).show();
        }else {
            LoginRequest loginRequest = new LoginRequest(user_email, user_password);
            ApiServices apiServices = NetworkService.getRetrofit().create(ApiServices.class);

            apiServices.getToken(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null){
                        loginResponse = response.body();
                        sId = loginResponse.getId();
                        sUsername = loginResponse.getUsername();
                        sToken = loginResponse.getToken();
                        saveToken();
                        showLoading(false);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        showLoading(false);

                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login Timed Out!", Toast.LENGTH_SHORT).show();
                    showLoading(false);


                }
            });



        }

    }


    //preferencesSaveToken
    private void saveToken(){
        SessionManager session = new SessionManager(this);
        loginResponse.setId(sId);
        loginResponse.setToken(sToken);
        loginResponse.setUsername(sUsername);
        session.saveAuthToken(loginResponse);
        Toast.makeText(this, "Data Token Tersimpan: \n"+sToken,Toast.LENGTH_SHORT).show();
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

        mEdtEmail = findViewById(R.id.edt_username);
        mEdtPassword = findViewById(R.id.edt_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);


    }
}