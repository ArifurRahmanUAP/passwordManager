package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    EditText userSignInEmail, userSignInPassword;
    TextView signInButton;
    LinearLayout loginLayout;
    LazyLoader progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSignInEmail = findViewById(R.id.userSignInEmail);
        userSignInPassword = findViewById(R.id.userSignInPassword);
        signInButton = findViewById(R.id.signInButton);
        loginLayout = findViewById(R.id.loginLayout);
        progressBar = findViewById(R.id.progressBar);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (userSignInEmail.getText().toString().equals("") || userSignInPassword.getText().toString().equals("")) {
                    Toasty.error(getApplicationContext(), "Email or Password Can't be Empty").show();
                    loginLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {


                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    HashMap<String, String> data = databaseAccess.checkLogin();
                    if (userSignInEmail.getText().toString().equals(data.get(Constant.NAME)) && userSignInPassword.getText().toString().equals(data.get(Constant.PASSWORD))) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        LoginActivity.this.finish();

                    } else {
                        loginLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "User Name or Password Wrong").show();
                    }
                }
            }
        });
    }
}