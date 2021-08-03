package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.CurrentPassword)
    TextInputEditText CurrentPassword;

    @BindView(R.id.NewPassword)
    TextInputEditText NewPassword;


    @BindView(R.id.ConfirmPassword)
    TextInputEditText ConfirmPassword;

    @BindView(R.id.btnUpdate)
    Button Update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btnUpdate)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Change_Password_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ChangePasswordActivity.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangePasswordActivity.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_CURRENT_PASSWORD, CurrentPassword.getText().toString());
                map.put(JsonFields.TAG_NEW_PASSWORD, NewPassword.getText().toString());
                map.put(JsonFields.TAG_CONFIRM__PASSWORD, ConfirmPassword.getText().toString());
                map.put(JsonFields.TAG_USER_ID, getSharedPreferences("MyFile",0).getString(JsonFields.TAG_USER_ID,""));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
        requestQueue.add(stringRequest);
    }
}