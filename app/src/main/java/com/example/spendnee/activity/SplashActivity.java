package com.example.spendnee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadData();
    }

    private void loadData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!DataLocalManager.getFirstInstall()       ){
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }
}

//|| DataLocalManager.getCheckFromLogout()