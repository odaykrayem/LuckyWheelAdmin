package com.example.luckywheeladmin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luckywheeladmin.Adapters.ParticipantAdapter;
import com.example.luckywheeladmin.Adapters.RequestsAdapter;
import com.example.luckywheeladmin.Models.RequestModel;
import com.example.luckywheeladmin.R;
import com.example.luckywheeladmin.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WithdrawalRequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView requestsRV;
    private NestedScrollView nestedSV;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout note;

    private ArrayList<RequestModel> requestsArrayList;
    private RequestsAdapter requestAdapter;

    final String TAG = "requests_FR";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawal_requests,container,false);
        requestsArrayList = new ArrayList<>();
        note = view.findViewById(R.id.note);
        requestsRV = view.findViewById(R.id.rv_requests);
        nestedSV = view.findViewById(R.id.nested_sV);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        return view;       }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getRequests();

            }
        });
    }

    @Override
    public void onRefresh() {
        getRequests();
    }

    private void getRequests() {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // creating a variable for our json object request and passing our url to it.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.GET_ALL_WITHDRAWAL_REQUESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestsArrayList.clear();
                        try {
                            Log.e(TAG, response);
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("error");
                            if (code.equals("false")) {
                                JSONArray operations = jsonObject.getJSONArray("list");
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                for (int i = 0; i < operations.length(); i++) {
                                    JSONObject object = operations.getJSONObject(i);
                                    Log.e(TAG, object.toString());
                                    boolean status = (object.getInt("status") == 0)?false:true;
                                    // on below line we are extracting data from our json object.
                                    requestsArrayList.add(new RequestModel(
                                            object.getInt("id"),
                                            object.getInt("user_id"),
                                            object.getString("user_name"),
                                            object.getString("email"),
                                            object.getString("bank_code"),
                                            object.getInt("amount"),
                                            object.getString("date"),
                                            status
                                            ));
                                    System.out.println("jsonObject" + object.toString());
                                }
                                if (requestsArrayList.size() > 0) {
                                    note.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            }
                            mSwipeRefreshLayout.setRefreshing(false);

                            // passing array list to our adapter class.
                            requestAdapter = new RequestsAdapter(requestsArrayList, getContext());

                            // setting layout manager to our recycler view.
                            requestsRV.setLayoutManager(new LinearLayoutManager(getContext()));

                            // setting adapter to our recycler view.
                            requestsRV.setAdapter(requestAdapter);

                        } catch (JSONException e) {
                            mSwipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(getContext(), "Fail to get data.." + e.toString()
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
                mSwipeRefreshLayout.setRefreshing(false);


                // handling on error listener method.
                Toast.makeText(getContext(), "Fail to get data.." + error.toString()

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
                return parameters;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding our string request to queue

        stringRequest.setShouldCache(false);
        queue.getCache().clear();
        queue.add(stringRequest);
    }
}
