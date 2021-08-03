package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uniconwebdesign.mia.adapter.MusicalAcademyAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMusicalAcademyActivity extends AppCompatActivity {

    @BindView(R.id.rvMusicalAcademy)
    RecyclerView rvMusicalAcademy;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_musical_academy);
        ButterKnife.bind(this);
        fetchdata();
        setTitle("Academy List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_MusicalAcademy_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_MUSICAL_ACADEMY);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_ID, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_ID));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_NAME, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_NAME));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_EMAIL, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_EMAIL));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_LOGO, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_LOGO));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_MOBILENO, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_MOBILENO));

                            arrayList.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewMusicalAcademyActivity.this);
                        rvMusicalAcademy.setLayoutManager(linearLayoutManager);

                        MusicalAcademyAdapter musicalAcademyAdapter = new MusicalAcademyAdapter(ViewMusicalAcademyActivity.this, arrayList);
                        rvMusicalAcademy.setAdapter(musicalAcademyAdapter);
                    } else {
                        Toast.makeText(ViewMusicalAcademyActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ViewMusicalAcademyActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewMusicalAcademyActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewMusicalAcademyActivity.this);
        requestQueue.add(stringRequest);
    }
}
