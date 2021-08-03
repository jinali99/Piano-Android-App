package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.Email)
    TextInputEditText Email;

    @BindView(R.id.Password)
    TextInputEditText Password;

    @BindView(R.id.btnLogIn)
    Button btnLogIn;

    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
        setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.tvRegister)
    public void setTvRegister() {
        Intent i = new Intent(LogInActivity.this, AddUserActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.tvForgotPassword)
    public void setTvForgotPassword() {
        Intent i = new Intent(LogInActivity.this, ForgetPasswordActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btnLogIn)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Login_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(LogInActivity.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONObject object1 = jsonObject.getJSONArray(JsonFields.TAG_USER).getJSONObject(0);
                        SharedPreferences preferences = getSharedPreferences("MyFile", 0);
                        SharedPreferences.Editor e = preferences.edit();
                        e.putString(JsonFields.TAG_USER_ID, object1.getString(JsonFields.TAG_USER_ID));
                        e.putString(JsonFields.TAG_USER_NAME, object1.getString(JsonFields.TAG_USER_NAME));
                        e.putString(JsonFields.TAG_USER_GENDER, object1.getString(JsonFields.TAG_USER_GENDER));
                        e.putString(JsonFields.TAG_USER_MAIL, object1.getString(JsonFields.TAG_USER_MAIL));
                        e.commit();
                        Intent i = new Intent(LogInActivity.this, HomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(LogInActivity.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(LogInActivity.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogInActivity.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_USER_MAIL, Email.getText().toString());
                map.put(JsonFields.TAG_USER_PASSWORD, Password.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LogInActivity.this);
        requestQueue.add(stringRequest);
    }
}