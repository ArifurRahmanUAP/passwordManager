package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.arif.passwordmanager.Adapters.UserDataEditAdapter;
import com.arif.passwordmanager.Database.Constant;

import java.util.HashMap;

public class UserDataEditActivity extends AppCompatActivity {

    RecyclerView dataEditRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_edit);
        dataEditRecyclerView = findViewById(R.id.dataEditRecyclerView);
        Intent intent = getIntent();
        String id = intent.getStringExtra(Constant.ID);
        String name = intent.getStringExtra(Constant.APPLICATION_NAME);
        String url = intent.getStringExtra(Constant.APPLICATION_URL);
        String userName = intent.getStringExtra(Constant.USERNAME);
        String password = intent.getStringExtra(Constant.PASSWORD);
        String others = intent.getStringExtra(Constant.OTHERS);

        HashMap<String, String> map = new HashMap<>();

        map.put(Constant.ID, id);
        map.put(Constant.APPLICATION_NAME, name);
        map.put(Constant.APPLICATION_URL, url);
        map.put(Constant.USERNAME, userName);
        map.put(Constant.PASSWORD, password);
        map.put(Constant.OTHERS, others);


        UserDataEditAdapter userDataEditAdapter = new UserDataEditAdapter(getApplicationContext(), map);
        dataEditRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dataEditRecyclerView.setAdapter(userDataEditAdapter);

    }
}