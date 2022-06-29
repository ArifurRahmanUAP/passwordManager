package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;


import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    EditText userName, userEmail, userPassword, userConfirmPassword;
    TextView SignUpButton, userBirthday;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.userFullName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        userConfirmPassword = findViewById(R.id.userConfirmPassword);
        userBirthday = findViewById(R.id.userBirthDay);
        SignUpButton = findViewById(R.id.SignUpButton);

        userBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                userBirthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("") || userEmail.getText().toString().equals("") || userPassword.getText().toString().equals("") ||
                        userConfirmPassword.getText().toString().equals("") || userBirthday.getText().toString().equals("")) {
                    Toasty.error(getApplicationContext(), "FillUp with all Information").show();
                } else {
                    if (userPassword.getText().toString().equals(userConfirmPassword.getText().toString())) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();
                        boolean isSuccess = databaseAccess.signUpUser(userName.getText().toString(), userEmail.getText().toString(), userBirthday.getText().toString(), userPassword.getText().toString());
                        if (isSuccess){
                            Toasty.success(getApplicationContext(), "SignUp Successful").show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else Toasty.error(getApplicationContext(), "Error").show();
                    } else {
                        Toasty.error(getApplicationContext(), "Both Password Not Matched").show();
                    }
                }
            }
        });
    }



}