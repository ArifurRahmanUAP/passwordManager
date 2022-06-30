package com.arif.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arif.passwordmanager.Adapters.UserDataEditAdapter;
import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;

import java.util.HashMap;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class UserDataEditActivity extends AppCompatActivity {

    EditText editApplicationName, editApplicationUrl, editUsername, editPassword, editOthers;
    Button editUserDataSave;
    ImageView backImage;
    TextView appbarTitle;
    AppCompatButton randomPassword;
    SeekBar passwordLength;
    int seekValue = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_data);

        editApplicationName = findViewById(R.id.applicationName);
        editApplicationUrl = findViewById(R.id.applicationUrl);
        editUsername = findViewById(R.id.userName);
        editPassword = findViewById(R.id.password);
        editOthers = findViewById(R.id.others);
        editUserDataSave = findViewById(R.id.userDataSave);
        appbarTitle = findViewById(R.id.appbarTitle);
        randomPassword = findViewById(R.id.randomPassword);
        passwordLength = findViewById(R.id.sliderPassword);
        backImage = findViewById(R.id.backImage);
        backImage.setVisibility(View.VISIBLE);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                UserDataEditActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra(Constant.ID);
        String name = intent.getStringExtra(Constant.APPLICATION_NAME);
        String url = intent.getStringExtra(Constant.APPLICATION_URL);
        String userName = intent.getStringExtra(Constant.USERNAME);
        String password = intent.getStringExtra(Constant.PASSWORD);
        String others = intent.getStringExtra(Constant.OTHERS);

        editUserDataSave.setText("Update");
        appbarTitle.setText("Update Data");
        editApplicationName.setText(name);
        editApplicationUrl.setText(url);
        editUsername.setText(userName);
        editPassword.setText(password);
        editOthers.setText(others);


        editUserDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editUsername.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty() || editApplicationName.getText().toString().isEmpty()) {
                    Toasty.warning(getApplicationContext(), "Fill Up Correctly").show();
                } else {


                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    boolean isSuccess = databaseAccess.updateData((id), editApplicationName.getText().toString(), editApplicationUrl.getText().toString(),
                            editUsername.getText().toString(), editPassword.getText().toString(), editOthers.getText().toString());
                    if (isSuccess) {
                        editApplicationName.setText("");
                        editApplicationUrl.setText("");
                        editUsername.setText("");
                        editPassword.setText("");
                        editOthers.setText("");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        UserDataEditActivity.this.finish();

                        Toasty.success(getApplicationContext(), "Data Updated Successfully").show();
                    } else Toasty.error(getApplicationContext(), "Something Wrong").show();

                }
            }
        });


        randomPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$&%#".toCharArray();
                StringBuilder stringBuilder = new StringBuilder();

                Random rand = new Random();

                for (int i = 0; i < seekValue; i++) {
                    char c = chars[rand.nextInt(chars.length)];
                    stringBuilder.append(c);
                }
                editPassword.setText(stringBuilder.toString());
            }
        });

        passwordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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


    }
}