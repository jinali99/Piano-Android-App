package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uniconwebdesign.mia.adapter.EnrollmentAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewEnrollmentActivity extends AppCompatActivity {


    @BindView(R.id.rvEnrollment)
    RecyclerView rvEnrollment;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enrollment);
        ButterKnife.bind(this);
        setTitle("My Enrollments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchdata();
    }


    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Enrollment_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_ENROLLMENT);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_ENROLLMENT_ID, object1.getString(JsonFields.TAG_ENROLLMENT_ID));
                            hashMap.put(JsonFields.TAG_ENROLLMENT_DATE, object1.getString(JsonFields.TAG_ENROLLMENT_DATE));
                            hashMap.put(JsonFields.TAG_USER_ID, object1.getString(JsonFields.TAG_USER_ID));
                            hashMap.put(JsonFields.TAG_INSTRUMENT_PHOTO, object1.getString(JsonFields.TAG_INSTRUMENT_PHOTO));
                            hashMap.put(JsonFields.TAG_COURSE_NAME, object1.getString(JsonFields.TAG_COURSE_NAME));
                            hashMap.put(JsonFields.TAG_ENROLLMENT_AMOUNT, object1.getString(JsonFields.TAG_ENROLLMENT_AMOUNT));
                            arrayList.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewEnrollmentActivity.this);
                        rvEnrollment.setLayoutManager(linearLayoutManager);

                        EnrollmentAdapter enrollmentAdapter = new EnrollmentAdapter(ViewEnrollmentActivity.this, arrayList);
                        rvEnrollment.setAdapter(enrollmentAdapter);
                    } else {
                        Toast.makeText(ViewEnrollmentActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewEnrollmentActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewEnrollmentActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_USER_ID, getSharedPreferences("MyFile", 0).getString(JsonFields.TAG_USER_ID, ""));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ViewEnrollmentActivity.this);
        requestQueue.add(stringRequest);
    }

}
