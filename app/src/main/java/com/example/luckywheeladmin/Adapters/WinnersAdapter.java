package com.example.luckywheeladmin.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luckywheeladmin.Models.ParticipantModel;
import com.example.luckywheeladmin.R;
import com.example.luckywheeladmin.Utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WinnersAdapter extends RecyclerView.Adapter<WinnersAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<ParticipantModel> participantModelArrayList;
    private Context context;

    private static String TAG = "winners_adapter";

    // creating a constructor.
    public WinnersAdapter(ArrayList<ParticipantModel> participantModelArrayList, Context context) {
        this.participantModelArrayList = participantModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_winners, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ParticipantModel participantModel = participantModelArrayList.get(position);

        holder.userNameTV.setText(participantModel.getUser_name().trim());
        holder.emailTV.setText(participantModel.getEmail().trim());
        holder.prizeTV.setText(String.valueOf(participantModel.getPrize()));
        Log.e(TAG, String.valueOf(position) + "  "+String.valueOf(participantModel.getUser_id()));
        holder.makeNotWinnerBtn.setOnClickListener(
                v -> makeUserNotWinner(String.valueOf(participantModel.getUser_id()), position, String.valueOf(participantModel.getContest_id())));

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return participantModelArrayList.size();
    }

    private void makeUserNotWinner(String user_id, int position, String contest_id) {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(context);
        // creating a variable for our json object request and passing our url to it.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.CHANGE_USER_WINNER_STATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, response);
                            JSONObject jsonObject = new JSONObject(response);
                            String error = jsonObject.getString("error");
                            String message = jsonObject.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            participantModelArrayList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                        } catch (JSONException e) {

                            Toast.makeText(context, "Fail to get data.." + e.toString()
                                    + "\nCause " + e.getCause()
                                    + "\nmessage" + e.getMessage(), Toast.LENGTH_LONG).show();
                            System.out.println("error1:::" + e.toString()
                                    + "\nCause " + e.getCause()
                                    + "\nmessage" + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // handling on error listener method.
                Toast.makeText(context, "Fail to get data.." + error.toString()

                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("error2" + error.toString()

                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                parameters.put("contest_id", contest_id);
                parameters.put("state", "not_winner");
                return parameters;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our string request to queue
        queue.add(stringRequest);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView userNameTV, emailTV, prizeTV;
        private Button makeNotWinnerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            // initializing our variables.
            userNameTV = itemView.findViewById(R.id.tv_item_name);
            emailTV = itemView.findViewById(R.id.tv_item_email);
            prizeTV = itemView.findViewById(R.id.tv_item_prize);
            makeNotWinnerBtn = itemView.findViewById(R.id.btn_make_participant_not_winner);

        }

    }
}