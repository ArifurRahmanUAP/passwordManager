package com.arif.passwordmanager;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arif.passwordmanager.Fragments.HomeFragment;
import com.arif.passwordmanager.Fragments.AddDataFragment;
import com.arif.passwordmanager.Fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AddDataFragment addDataFragment = new AddDataFragment();
    SettingFragment settingsFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(androidx.constraintlayout.widget.R.id.horizontal_only);

        Bundle extras = getIntent().getExtras();
        int jobFragment;

        if (extras != null) {
            jobFragment = extras.getInt("jobFragment");
            if (jobFragment == 1) {
                bottomNavigationView.setSelectedItemId(R.id.addData);
            } else bottomNavigationView.setSelectedItemId(R.id.home);
        } else bottomNavigationView.setSelectedItemId(R.id.home);


        getSupportFragmentManager().beginTransaction().add(R.id.flFragment, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;
            case R.id.addData:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, addDataFragment).commit();
                return true;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                return true;
        }
        return false;
    }

}