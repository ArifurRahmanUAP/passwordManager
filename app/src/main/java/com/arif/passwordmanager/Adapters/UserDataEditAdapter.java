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

    int seekValue = 8;

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserDataEditAdapter.ViewHolder holder, int position) {

        holder.editUserDataSave.setText("Update");
        holder.appbarTitle.setText("Update Data");
        holder.editApplicationName.setText(userData.get(Constant.APPLICATION_NAME));
        holder.editApplicationUrl.setText(userData.get(Constant.APPLICATION_URL));
        holder.editUsername.setText(userData.get(Constant.USERNAME));
        holder.editPassword.setText(userData.get(Constant.PASSWORD));
        holder.editOthers.setText(userData.get(Constant.OTHERS));

        holder.editUserDataSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.editUsername.getText().toString().isEmpty() || holder.editPassword.getText().toString().isEmpty() || holder.editApplicationName.getText().toString().isEmpty()) {
                    Toasty.warning(context, "Fill Up Correctly").show();
                } else {


                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                    databaseAccess.open();
                    boolean isSuccess = databaseAccess.updateData(userData.get(Constant.ID), holder.editApplicationName.getText().toString(), holder.editApplicationUrl.getText().toString(),
                            holder.editUsername.getText().toString(), holder.editPassword.getText().toString(), holder.editOthers.getText().toString());
                    if (isSuccess) {
                        holder.editApplicationName.setText("");
                        holder.editApplicationUrl.setText("");
                        holder.editUsername.setText("");
                        holder.editPassword.setText("");
                        holder.editOthers.setText("");

                        context.startActivity(new Intent(context, MainActivity.class));
                        notifyItemChanged(holder.getAdapterPosition());
                        Toasty.success(context, "Data Updated Successfully").show();
                    } else Toasty.error(context, "Something Wrong").show();

                }
            }
        });


        holder.randomPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$&%#".toCharArray();
                StringBuilder stringBuilder = new StringBuilder();

                Random rand = new Random();

                for (int i = 0; i < seekValue; i++) {
                    char c = chars[rand.nextInt(chars.length)];
                    stringBuilder.append(c);
                }
                holder.editPassword.setText(stringBuilder.toString());
            }
        });

        holder.passwordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
