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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.luckywheeladmin.Models.ParticipantModel;
import com.example.luckywheeladmin.R;
import com.example.luckywheeladmin.Utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<ParticipantModel> list;
    private Context context;
    private  static String TAG = "Participants_adapter";
    // creating a constructor.
    public ParticipantAdapter(ArrayList<ParticipantModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_participants, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ParticipantModel participantModel = list.get(position);

        holder.userNameTV.setText(participantModel.getUser_name().trim());
        holder.emailTV.setText(participantModel.getEmail().trim());
        holder.prizeTV.setText(String.valueOf(participantModel.getPrize()));

        if(participantModel.isIs_winner()){
            holder.makeWinnerBtn.setEnabled(false);
            holder.makeWinnerBtn.setText(R.string.winner_p_txt);
            holder.makeWinnerBtn.setBackgroundColor(0xff1e4b99);
        }
        holder.makeWinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeUserWinner(String.valueOf(participantModel.getUser_id()),position, String.valueOf(participantModel.getContest_id()));
//                makeUserW(String.valueOf(participantModel.getUser_id()), String.valueOf(participantModel.getContest_id()), position);


            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater= LayoutInflater.from(context);

                final View view=inflater.inflate(R.layout.dialog_delete_participant,null);
                final AlertDialog.Builder alertDialogueBuilder= new AlertDialog.Builder(context);

                alertDialogueBuilder.setView(view);
                alertDialogueBuilder.setCancelable(false).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(NetworkUtils.checkInternetConnection(context)){
                            deleteParticipant(participantModel, position);
//                            deleteP( String.valueOf(participantModel.getId()),position);
                        }else{
                            Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
                        }

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

//    private void deleteP(String id , int position){
//        AndroidNetworking.post(NetworkUtils.DELETE_PARTICIPANT_URL).setPriority(Priority.MEDIUM)
//                .addBodyParameter("participant_id", id)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        try {
//                            //converting response to json object
//                            JSONObject obj = response;
//                            //if no error in response
//                            if (obj.getInt("error") == 1) {
//                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("data");
//                                Log.e("data", userJson.toString());
//                                list.get(position).setIs_winner(true);
//                                notifyItemChanged(position);
//                                list.remove(position);
//                                notifyItemRemoved(position);
//                                notifyDataSetChanged();
//                            } else if(obj.getInt("status") == 0){
//                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(context, anError.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
    private void deleteParticipant(ParticipantModel participantModel, int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.DELETE_PARTICIPANT_URL,
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
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
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
                Log.e(TAG, String.valueOf(participantModel.getId()));
                params.put("participant_id", String.valueOf(participantModel.getId()));
                return params;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    void makeUserW(String id, String contest_id, int position){
        AndroidNetworking.post(NetworkUtils.CHANGE_USER_WINNER_STATE_URL).setPriority(Priority.MEDIUM)
                .addBodyParameter("id", id)
                .addBodyParameter("contest_id", contest_id)
                .addBodyParameter("state", "winner")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            //if no error in response
                            if (obj.getInt("error") == 1) {
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("data");
                                Log.e("data", userJson.toString());
                                list.get(position).setIs_winner(true);
                                notifyItemChanged(position);

                            } else if(obj.getInt("status") == 0){
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public int getItemCount() {
        // returning the size of array list.
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView userNameTV, emailTV, prizeTV;
        private Button deleteBtn, makeWinnerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            // initializing our variables.
            userNameTV = itemView.findViewById(R.id.tv_item_name);
            emailTV = itemView.findViewById(R.id.tv_item_email);
            prizeTV = itemView.findViewById(R.id.tv_item_prize);
            deleteBtn = itemView.findViewById(R.id.btn_delete_participant);
            makeWinnerBtn = itemView.findViewById(R.id.btn_make_participant_winner);

        }
    }



    private void makeUserWinner(String user_id, int position, String contest_id) {
        // creating a new variable for our request queue
        Log.e(TAG,"user id" + user_id);
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
                            if(error.equals("false")){
                                list.get(position).setIs_winner(true);
                                notifyItemChanged(position);
                            }
                            String message = jsonObject.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

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
                parameters.put("state", "winner");
                return parameters;
            }
        };
        stringRequest.setShouldCache(false);
        queue.getCache().clear();
        //adding our string request to queue
        queue.add(stringRequest);

    }

}