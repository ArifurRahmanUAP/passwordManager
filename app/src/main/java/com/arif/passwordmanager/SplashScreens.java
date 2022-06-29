package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arif.passwordmanager.Database.DatabaseAccess;

@SuppressLint("CustomSplashScreen")
public class SplashScreens extends AppCompatActivity {

    public final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screens);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String userAvailable = databaseAccess.getUserAvailable();

                if (userAvailable.isEmpty()) {
                    startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                    SplashScreens.this.finish();
                } else {

                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    SplashScreens.this.finish();
                }
            }
        }, SPLASH_TIME);
    }
}