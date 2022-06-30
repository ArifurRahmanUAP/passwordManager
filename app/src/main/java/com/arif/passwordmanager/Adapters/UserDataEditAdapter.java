package com.arif.passwordmanager.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.MainActivity;
import com.arif.passwordmanager.R;

import java.util.HashMap;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class UserDataEditAdapter extends RecyclerView.Adapter<UserDataEditAdapter.ViewHolder> {
    Context context;
    HashMap<String, String> userData;

    public UserDataEditAdapter(Context context, HashMap<String, String> userData) {
        this.context = context;
        this.userData = userData;
    }

    @NonNull
    @Override
    public UserDataEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_add_data, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserDataEditAdapter.ViewHolder holder, int position) {






    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editApplicationName, editApplicationUrl, editUsername, editPassword, editOthers;
        Button editUserDataSave;
        TextView appbarTitle;
        AppCompatButton randomPassword;
        SeekBar passwordLength;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editApplicationName = itemView.findViewById(R.id.applicationName);
            editApplicationUrl = itemView.findViewById(R.id.applicationUrl);
            editUsername = itemView.findViewById(R.id.userName);
            editPassword = itemView.findViewById(R.id.password);
            editOthers = itemView.findViewById(R.id.others);
            editUserDataSave = itemView.findViewById(R.id.userDataSave);
            appbarTitle = itemView.findViewById(R.id.appbarTitle);
            randomPassword = itemView.findViewById(R.id.randomPassword);
            passwordLength = itemView.findViewById(R.id.sliderPassword);
        }
    }
}
