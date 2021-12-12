package com.example.luckywheeladmin.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luckywheeladmin.Models.ContestModel;
import com.example.luckywheeladmin.R;
import com.example.luckywheeladmin.Utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContestsAdapter extends RecyclerView.Adapter<ContestsAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<ContestModel> contestModelArrayList;
    private Context context;
    final String TAG = "contests_ad";
    // creating a constructor.
    public ContestsAdapter(ArrayList<ContestModel> participantModelArrayList, Context context) {
        this.contestModelArrayList = participantModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_contests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ContestModel contestModel = contestModelArrayList.get(position);

        holder.prizeTV.setText(String.valueOf(contestModel.getPrize()));
        holder.drawDateTV.setText(String.valueOf(contestModel.getDraw_date()));
        holder.nameTV.setText(contestModel.getName());

        holder.deleteContestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater= LayoutInflater.from(context);

                final View view=inflater.inflate(R.layout.dialog_delete_contest,null);
                final AlertDialog.Builder alertDialogueBuilder= new AlertDialog.Builder(context);

                alertDialogueBuilder.setView(view);
                alertDialogueBuilder.setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteContest(contestModel);
                        contestModelArrayList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }

                });
                alertDialogueBuilder.setCancelable(true).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }

                });
                alertDialogueBuilder.create();
                alertDialogueBuilder.show();
            }
        });

    }

    private void deleteContest(ContestModel contestModel) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.DELETE_CONTEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("error");

                            if(code.equals("false")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                            }else if(code.equals("true")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();

                            Toast.makeText(context, " Error1! " + e.toString()
                                    + "\nCause " + e.getCause()
                                    + "\nmessage" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "Error2! " + error.toString()
                                + "\nCause: " + error.getCause()
                                + "\nmessage: " + error.getMessage()
                        );
                        Toast.makeText(context, "Error2! " + error.toString()
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Log.e(TAG, String.valueOf(contestModel.getId() ));
                params.put("contest_id", String.valueOf(contestModel.getId()));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return contestModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView prizeTV, drawDateTV, nameTV;
        private Button deleteContestBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            // initializing our variables.
            prizeTV = itemView.findViewById(R.id.tv_item_prize);
            drawDateTV = itemView.findViewById(R.id.tv_item_draw_date);
            deleteContestBtn = itemView.findViewById(R.id.btn_delete_contest);
            nameTV = itemView.findViewById(R.id.tv_item_contest_name);

        }
    }


}