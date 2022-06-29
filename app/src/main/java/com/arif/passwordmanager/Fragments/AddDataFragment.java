package com.arif.passwordmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.MainActivity;
import com.arif.passwordmanager.R;

import java.util.Random;

import es.dmoral.toasty.Toasty;

public class AddDataFragment extends Fragment {
    EditText applicationName, applicationUrl, username, password, others;
    Button userDataSave;
    AppCompatButton randomPasswordButton;
    SeekBar passwordLengthSeekBar;
    int seekValue = 8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_data, container, false);

        applicationName = view.findViewById(R.id.applicationName);
        applicationUrl = view.findViewById(R.id.applicationUrl);
        username = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);
        others = view.findViewById(R.id.others);
        userDataSave = view.findViewById(R.id.userDataSave);
        randomPasswordButton = view.findViewById(R.id.randomPassword);
        passwordLengthSeekBar = view.findViewById(R.id.sliderPassword);

        userDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || applicationName.getText().toString().isEmpty()) {
                    Toasty.warning(getContext(), "Fill Up Correctly").show();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                    databaseAccess.open();
                    boolean isSuccess = databaseAccess.addUserData(applicationName.getText().toString(), applicationUrl.getText().toString(), username.getText().toString(), password.getText().toString(), others.getText().toString());
                    if (isSuccess) {
                        startActivity(new Intent(getContext(), MainActivity.class));
                        Toasty.success(getContext(), "Data Saved Successfully").show();
                    } else Toasty.error(getContext(), "Something Error").show();
                }
            }
        });
        randomPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$&%#".toCharArray();
                StringBuilder stringBuilder = new StringBuilder();

                Random rand = new Random();

                for (int i = 0; i < seekValue; i++) {
                    char c = chars[rand.nextInt(chars.length)];
                    stringBuilder.append(c);
                }
                password.setText(stringBuilder.toString());
            }
        });

        passwordLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}