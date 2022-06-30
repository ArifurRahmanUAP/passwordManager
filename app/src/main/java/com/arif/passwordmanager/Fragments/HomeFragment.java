package com.arif.passwordmanager.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arif.passwordmanager.Adapters.UserDataAdapter;
import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    RecyclerView showDataRecyclerView;
    ImageView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showDataRecyclerView = view.findViewById(R.id.showDataRecyclerView);
        noData = view.findViewById(R.id.noData);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        ArrayList<HashMap<String, String>> userData = databaseAccess.getUserData();

        if (userData.isEmpty()){
            noData.setVisibility(View.VISIBLE);
            showDataRecyclerView.setVisibility(View.GONE);
        }
        else {
            noData.setVisibility(View.GONE);
            showDataRecyclerView.setVisibility(View.VISIBLE);
        }

        UserDataAdapter userDataAdapter = new UserDataAdapter(getActivity(), userData);
        showDataRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        showDataRecyclerView.setAdapter(userDataAdapter);


        return view;
    }
}