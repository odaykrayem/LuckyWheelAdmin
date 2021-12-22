package com.example.luckywheeladmin.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luckywheeladmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDialog extends Dialog  {


        public Activity c;
        public Dialog d;
        public Button addBtn, cancelBtn;
        public EditText nameET, prizeET, drawDateET ,drawTimeET;
        public ProgressBar progressBar;
        LinearLayout buttonsLayout;

        final Calendar myCalendar = Calendar.getInstance();
       final Calendar calenderTime = Calendar.getInstance();

        private static String TAG = "Dialog";
        public CustomDialog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_add_contest);
            addBtn = (Button) findViewById(R.id.btn_add);
            cancelBtn = (Button) findViewById(R.id.btn_cancel);
//            addBtn.setOnClickListener(this);
//            cancelBtn.setOnClickListener(this);

            progressBar = findViewById(R.id.progressBar);
            buttonsLayout = findViewById(R.id.buttons_layout);
            nameET = findViewById(R.id.contest_name);
            prizeET = findViewById(R.id.contest_prize);
            drawDateET = findViewById(R.id.contest_draw_date);
            drawTimeET = findViewById(R.id.contest_draw_time);

            buttonsLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validateInput()){
                        if(NetworkUtils.checkInternetConnection(getContext())){
                            addContestToDB(nameET.getText().toString().trim(),prizeET.getText().toString().trim(),drawDateET.getText().toString().trim(),drawTimeET.getText().toString().trim());
                        }else{
                            Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();

                }
            });
            drawDateET.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            drawTimeET.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int hour = calenderTime.get(Calendar.HOUR);

                    int minute = calenderTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            drawTimeET.setText( selectedHour + ":" + selectedMinute + String.valueOf(calenderTime.get(Calendar.AM_PM)));
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });
        }



    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        drawDateET.setText(sdf.format(myCalendar.getTime()));
    }
    private void addContestToDB(String name, String prize, String draw_date, String draw_time) {
            buttonsLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        Toast.makeText(getContext(), prize + "  "+ name, Toast.LENGTH_SHORT).show();
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        // creating a variable for our json object request and passing our url to it.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NetworkUtils.ADD_CONTEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, response);
                            JSONObject jsonObject = new JSONObject(response);
                            String error = jsonObject.getString("error");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            dismiss();
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            buttonsLayout.setVisibility(View.GONE);

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
                progressBar.setVisibility(View.GONE);
                buttonsLayout.setVisibility(View.GONE);

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
                parameters.put("prize", prize);
                parameters.put("draw_date", draw_date);
                parameters.put("draw_time", draw_time);
                parameters.put("name", name);

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

    private boolean validateInput() {
        if (nameET.getText().toString().trim().isEmpty()) {
            nameET.setError("من فضلك املأ هذا الحقل");
//            progressBar.setVisibility(View.VISIBLE);
            return false;
        }
        if (prizeET.getText().toString().trim().isEmpty()) {
            prizeET.setError("من فضلك املأ هذا الحقل");
            return false;
        } else {
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(prizeET.getText().toString().trim());
            if (matcher.find()) {
                prizeET.setError("من فضلك أدخل قيمة مناسبة للجائزة !!");
                return false;
            }
        }

        return true;
    }
}
