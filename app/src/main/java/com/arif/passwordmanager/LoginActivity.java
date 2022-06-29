package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    EditText userSignInEmail, userSignInPassword;
    TextView signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userSignInEmail = findViewById(R.id.userSignInEmail);
        userSignInPassword = findViewById(R.id.userSignInPassword);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSignInEmail.getText().toString().equals("") || userSignInPassword.getText().toString().equals("")) {
                    Toasty.error(getApplicationContext(), "Email or Password Can't be Empty").show();
                } else {
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    HashMap<String, String> data = databaseAccess.checkLogin();
                    if (userSignInEmail.getText().toString().equals(data.get(Constant.NAME)) && userSignInPassword.getText().toString().equals(data.get(Constant.PASSWORD))) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        LoginActivity.this.finish();

                    } else
                        Toasty.error(getApplicationContext(), "User Name or Password Wrong").show();

                }
            }
        });
    }
}