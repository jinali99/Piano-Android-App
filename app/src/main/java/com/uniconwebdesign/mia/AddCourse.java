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

public class AddCourse extends AppCompatActivity {


    @BindView(R.id.etCourseName)
    TextInputEditText etCourseName;

    @BindView(R.id.etInstrumentId)
    TextInputEditText etInstrumentId;

    @BindView(R.id.etAcademyId)
    TextInputEditText etAcademyId;

    @BindView(R.id.etCourseCost)
    TextInputEditText etCourseCost;

    @BindView(R.id.btnAddCourse)
    Button btnAddCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnAddCourse)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_Course_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(AddCourse.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCourse.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddCourse.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddCourse.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_COURSE_NAME,etCourseName.getText().toString());
                map.put(JsonFields.TAG_INSTRUMENT_ID,etInstrumentId.getText().toString());
                map.put(JsonFields.TAG_ACADEMY_ID,etAcademyId.getText().toString());
                map.put(JsonFields.TAG_COURSE_COST,etCourseCost.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddCourse.this);
        requestQueue.add(stringRequest);
    }











}
