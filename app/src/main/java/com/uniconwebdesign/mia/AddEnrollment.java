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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEnrollment extends AppCompatActivity {

    @BindView(R.id.etEnrollmentDate)
    TextInputEditText etEnrollmentDate;

    @BindView(R.id.etEnrollmentAmount)
    TextInputEditText etEnrollmentAmount;

    @BindView(R.id.etUserId)
    TextInputEditText etUserId;

    @BindView(R.id.etCourseId)
    TextInputEditText etCourseId;

    @BindView(R.id.btnAddEnrollment)
    Button btnAddEnrollment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enrollment);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnAddEnrollment)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_Enrollment_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(AddEnrollment.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEnrollment.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddEnrollment.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddEnrollment.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_ENROLLMENT_DATE,etEnrollmentDate.getText().toString());
                map.put(JsonFields.TAG_ENROLLMENT_AMOUNT,etEnrollmentAmount.getText().toString());
                map.put(JsonFields.TAG_USER_ID,etUserId.getText().toString());
                map.put(JsonFields.TAG_COURSE_ID,etCourseId.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddEnrollment.this);
        requestQueue.add(stringRequest);
    }
}