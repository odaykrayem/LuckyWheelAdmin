package com.example.luckywheeladmin.Utils;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luckywheeladmin.BuildConfig;
import com.example.luckywheeladmin.Models.RequestModel;
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
public class CustomRequestInfoDialog extends Dialog  {


//    public Activity c;
    public Context c;
    public Dialog d;
    public Button copyBtn, cancelBtn;
    public TextView nameTV, emailTV, amountTV, dateTV, statusTV, bankCodeTV;

    RequestModel requestModel;
    private static String TAG = " Request Info Dialog";
    public CustomRequestInfoDialog(Context c, RequestModel requestModel) {
        super(c);
        // TODO Auto-generated constructor stub
        this.c = c;
        this.requestModel = requestModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_request_info);
        copyBtn = (Button) findViewById(R.id.btn_request_info_copy_code);
        cancelBtn = (Button) findViewById(R.id.btn_request_info_cancel);

        nameTV = findViewById(R.id.request_info_user_name);
        emailTV = findViewById(R.id.request_info_user_email);
        amountTV = findViewById(R.id.request_info_amount);
        dateTV = findViewById(R.id.request_info_date);
        statusTV = findViewById(R.id.request_info_request_status);
        bankCodeTV = findViewById(R.id.request_info_bank_code);

        nameTV.setText(requestModel.getUser_name());
        emailTV.setText(requestModel.getUser_email());
        amountTV.setText(String.valueOf(requestModel.getAmount()));
        dateTV.setText(requestModel.getDate());
        if(requestModel.isStatus()){
            statusTV.setText(R.string.btn_make_request_done);
        }else{
            statusTV.setText(R.string.btn_make_request_delayed);
        }
        bankCodeTV.setText(requestModel.getBank_code());

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               shareBankCode(bankCodeTV.getText().toString().trim());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

    }

    public void shareBankCode(String bankCode){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = bankCode;
        String shareSubject = "";

        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);


        getContext().startActivity(Intent.createChooser(sharingIntent, "النسخ إلى :"));
    }
}
