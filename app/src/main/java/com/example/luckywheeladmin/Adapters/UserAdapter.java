package com.example.luckywheeladmin.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luckywheeladmin.Models.UserModel;
import com.example.luckywheeladmin.R;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<UserModel> userModelArrayList;
    private Context context;

    // creating a constructor.
    public UserAdapter(ArrayList<UserModel> userModelArrayList, Context context) {
        this.userModelArrayList = userModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModel operationModel = userModelArrayList.get(position);

        holder.userNameTV.setText(operationModel.getUser_name().trim());
        holder.emailTV.setText(operationModel.getEmail().trim());
        holder.refCodeTV.setText(operationModel.getReferral_code().trim());
        holder.balanceTV.setText(String.valueOf(operationModel.getBalance()));
        holder.pointsTV.setText(String.valueOf(operationModel.getPoints()));


    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return userModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView userNameTV, emailTV, refCodeTV, balanceTV, pointsTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            // initializing our variables.
            userNameTV = itemView.findViewById(R.id.tv_item_name);
            emailTV = itemView.findViewById(R.id.tv_item_email);
            refCodeTV = itemView.findViewById(R.id.tv_item_referral_code);
            balanceTV = itemView.findViewById(R.id.tv_item_balance);
            pointsTV = itemView.findViewById(R.id.tv_item_points);

        }
    }
}