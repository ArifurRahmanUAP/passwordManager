package com.arif.passwordmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arif.passwordmanager.Database.Constant;
import com.arif.passwordmanager.Database.DatabaseAccess;
import com.arif.passwordmanager.R;
import com.arif.passwordmanager.UserDataEditActivity;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> userData;

    public UserDataAdapter(Context context, ArrayList<HashMap<String, String>> userData) {
        this.context = context;
        this.userData = userData;
    }

    @NonNull
    @Override
    public UserDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.userdataview, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserDataAdapter.ViewHolder holder, int position) {

        holder.applicationName.setText(userData.get(position).get(Constant.APPLICATION_NAME));
        holder.userName.setText(userData.get(position).get(Constant.USERNAME));

        holder.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
                databaseAccess.open();
                boolean isSuccess = databaseAccess.deleteData(userData.get(position).get(Constant.ID));
                if (isSuccess) {
                    userData.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    Toasty.success(context, "Delete Successfully").show();
                } else Toasty.error(context, "Something Error").show();
            }
        });


        holder.cardViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserDataEditActivity.class);
                intent.putExtra(Constant.ID, userData.get(position).get(Constant.ID));
                intent.putExtra(Constant.APPLICATION_NAME, userData.get(position).get(Constant.APPLICATION_NAME));
                intent.putExtra(Constant.APPLICATION_URL, userData.get(position).get(Constant.APPLICATION_URL));
                intent.putExtra(Constant.USERNAME, userData.get(position).get(Constant.USERNAME));
                intent.putExtra(Constant.PASSWORD, userData.get(position).get(Constant.PASSWORD));
                intent.putExtra(Constant.OTHERS, userData.get(position).get(Constant.OTHERS));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicationName, userName;
        ImageView deleteData;
        CardView cardViewId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            applicationName = itemView.findViewById(R.id.dataViewApplicationName);
            userName = itemView.findViewById(R.id.dataViewUserName);
            deleteData = itemView.findViewById(R.id.deleteData);
            cardViewId = itemView.findViewById(R.id.cardViewId);
        }
    }
}
