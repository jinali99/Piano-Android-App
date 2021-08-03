package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class AddMusicalAcademy extends AppCompatActivity {

    @BindView(R.id.etMusicalAcademyname)
    TextInputEditText etMusicalAcademyname;

    @BindView(R.id.etMusicalAcademylogo)
    TextInputEditText etMusicalAcademylogo;

    @BindView(R.id.etMusicalAcademyMobileno)
    TextInputEditText etMusicalAcademyMobileno;

    @BindView(R.id.etMusicalAcademyEmail)
    TextInputEditText etMusicalAcademyEmail;

    @BindView(R.id.etMusicalAcademyPassword)
    TextInputEditText etMusicalAcademyPassword;

    @BindView(R.id.btnAddMusicalAcademy)
    TextInputEditText btnAddMusicalAcademy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_musical_academy);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnAddMusicalAcademy)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_MusicalAcademy_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(AddMusicalAcademy.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddMusicalAcademy.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddMusicalAcademy.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMusicalAcademy.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_ENROLLMENT_DATE,etMusicalAcademyname.getText().toString());
                map.put(JsonFields.TAG_ENROLLMENT_AMOUNT,etMusicalAcademylogo.getText().toString());
                map.put(JsonFields.TAG_USER_ID,etMusicalAcademyMobileno.getText().toString());
                map.put(JsonFields.TAG_COURSE_ID,etMusicalAcademyEmail.getText().toString());
                map.put(JsonFields.TAG_COURSE_ID,etMusicalAcademyPassword.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddMusicalAcademy.this);
        requestQueue.add(stringRequest);
    }


}
