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
import com.uniconwebdesign.mia.adapter.CourseAdapter;
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

public class ViewCourseActivity extends AppCompatActivity {

    @BindView(R.id.rvCourse)
    RecyclerView rvCourse;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        ButterKnife.bind(this);
        fetchdata();
        setTitle("Course List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Course_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_COURSE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_COURSE_ID, object1.getString(JsonFields.TAG_COURSE_ID));
                            hashMap.put(JsonFields.TAG_COURSE_NAME, object1.getString(JsonFields.TAG_COURSE_NAME));
                            hashMap.put(JsonFields.TAG_COURSE_COST, object1.getString(JsonFields.TAG_COURSE_COST));
                            hashMap.put(JsonFields.TAG_ACADEMY_ID, object1.getString(JsonFields.TAG_ACADEMY_ID));
                            hashMap.put(JsonFields.TAG_INSTRUMENT_ID, object1.getString(JsonFields.TAG_INSTRUMENT_ID));
                            hashMap.put(JsonFields.TAG_INSTRUMENT_PHOTO, object1.getString(JsonFields.TAG_INSTRUMENT_PHOTO));
                            arrayList.add(hashMap);

                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCourseActivity.this);
                        rvCourse.setLayoutManager(linearLayoutManager);

                        CourseAdapter courseAdapter = new CourseAdapter(ViewCourseActivity.this, arrayList);
                        rvCourse.setAdapter(courseAdapter);
                    } else {
                        Toast.makeText(ViewCourseActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ViewCourseActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewCourseActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_ACADEMY_ID, getIntent().getStringExtra(JsonFields.TAG_MUSICAL_ACADEMY_ID));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ViewCourseActivity.this);
        requestQueue.add(stringRequest);
    }
}