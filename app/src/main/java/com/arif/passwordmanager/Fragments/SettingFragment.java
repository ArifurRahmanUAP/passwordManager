package com.arif.passwordmanager.Fragments;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.Database.DatabaseOpenHelper;
import com.arif.passwordmanager.backup.LocalBackup;
import com.arif.passwordmanager.LoginActivity;
import com.arif.passwordmanager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class SettingFragment extends Fragment {

    TextView profileName, profileMail, profileBirthDate;
    AppCompatButton profileSave, profileSignOut, backupData, profileImport;
    EditText oldPassword, newPassword;
    private LocalBackup localBackup;
    String[] storagePermission;

    private static final int STORAGE_REQUEST_CODE_EXPORT = 1;
    private static final int STORAGE_REQUEST_CODE_IMPORT = 1;
    DatabaseOpenHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        db = new DatabaseOpenHelper(getContext());
        localBackup = new LocalBackup(getActivity());
        storagePermission = new String[]{WRITE_EXTERNAL_STORAGE};

        profileName = view.findViewById(R.id.profileName);
        profileMail = view.findViewById(R.id.profileMail);
        profileBirthDate = view.findViewById(R.id.profileBirthDate);
        newPassword = view.findViewById(R.id.newPassword);
        oldPassword = view.findViewById(R.id.oldPassword);
        profileSave = view.findViewById(R.id.profileSave);
        profileSignOut = view.findViewById(R.id.profileSignOut);
        backupData = view.findViewById(R.id.profileBackup);
        profileImport = view.findViewById(R.id.profileImport);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        HashMap<String, String> profileData = databaseAccess.getProfileData();
        profileName.setText(profileData.get(Constant.NAME));
        profileMail.setText(profileData.get(Constant.EMAIL));
        profileBirthDate.setText(profileData.get(Constant.BIRTH_DATE));


        profileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newPassword.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Password can't be empty").show();
                } else {
                    if (oldPassword.getText().toString().equals(profileData.get(Constant.PASSWORD))) {
                        databaseAccess.open();
                        databaseAccess.updatePassword(profileMail.getText().toString(), newPassword.getText().toString());

                        oldPassword.setText("");
                        newPassword.setText("");

                        Toasty.success(getContext(), "Password Updated Successfully").show();
                    } else Toasty.error(getContext(), "Both Password not Match").show();
                }
            }
        });

        profileSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        backupData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()) {
                    localBackup.performBackup(db);
                } else requestExportPermission();

            }
        });

        profileImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOpenHelper db = new DatabaseOpenHelper(getContext());
                localBackup.performRestore(db);
            }
        });


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE_EXPORT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                localBackup.performBackup(db);
            } else Toasty.error(getContext(), "Storage Permission required").show();
        }
    }


    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestExportPermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermission, STORAGE_REQUEST_CODE_EXPORT);
    }

}