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
import com.example.luckywheeladmin.Models.RequestModel;
import com.example.luckywheeladmin.R;
import com.example.luckywheeladmin.Utils.CustomRequestInfoDialog;
import com.example.luckywheeladmin.Utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<RequestModel> requestModelArrayList;
    private Context context;
    RequestModel requestModel;
    private  static String TAG = "requests_adapter";
    // creating a constructor.
    public RequestsAdapter(ArrayList<RequestModel> requestsModelArrayList, Context context) {
        this.requestModelArrayList = requestsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        requestModel = requestModelArrayList.get(position);

        holder.userNameTV.setText(requestModel.getUser_name().trim());
        holder.emailTV.setText(requestModel.getUser_email().trim());
        holder.amountTV.setText(String.valueOf(requestModel.getAmount()));

        if(requestModel.isStatus()){
            holder.doneBtn.setEnabled(false);
            holder.delayBtn.setEnabled(true);
            holder.statusTV.setText(R.string.btn_make_request_done);
            holder.statusTV.setTextColor(0xff33980F);
        }else{
            holder.doneBtn.setEnabled(true);
            holder.delayBtn.setEnabled(false);
            holder.statusTV.setText(R.string.btn_make_request_delay);
            holder.statusTV.setTextColor(0xff670303);
        }
        holder.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.checkInternetConnection(context)){
                    ChangeRequestState(String.valueOf(requestModelArrayList.get(position).getId()),"done",position);
                }else{
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
                }            }
        });
        holder.delayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkUtils.checkInternetConnection(context)){
                    ChangeRequestState(String.valueOf(requestModelArrayList.get(position).getId()),"not_done",position);
                }else{
                    Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomRequestInfoDialog cd = new CustomRequestInfoDialog(context,requestModelArrayList.get(position));
                cd.show();
            }
        });



    }
    // status : done / not_done
    private void ChangeRequestState(String request_id, String status, int position) {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(context);
        // creating a variable for our json object request and passing our url to it.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.CHANGE_REQUEST_STATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, response);
                            JSONObject jsonObject = new JSONObject(response);
                            String error = jsonObject.getString("error");
                            String message = jsonObject.getString("message");
                            if(error.equals("false")){
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                if(status.equals("done")){
                                    requestModelArrayList.get(position).setStatus(true);
                                }else{
                                    requestModelArrayList.get(position).setStatus(false);
                                }
                                notifyItemChanged(position);
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }

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
                parameters.put("request_id", request_id);
                parameters.put("state", status);
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

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return requestModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView userNameTV, emailTV, amountTV,statusTV;
        private Button delayBtn, doneBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            // initializing our variables.
            userNameTV = itemView.findViewById(R.id.tv_item_name);
            emailTV = itemView.findViewById(R.id.tv_item_email);
            amountTV = itemView.findViewById(R.id.tv_item_amount);
            statusTV = itemView.findViewById(R.id.tv_item_request_status);
            delayBtn = itemView.findViewById(R.id.btn_delay);
            doneBtn = itemView.findViewById(R.id.btn_done);

        }
    }
}