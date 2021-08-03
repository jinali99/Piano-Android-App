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
import com.uniconwebdesign.mia.adapter.TuneAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewTuneActivity extends AppCompatActivity {

    @BindView(R.id.rvtune)
    RecyclerView rvtune;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tune);
        ButterKnife.bind(this);
        setTitle("Tunes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchdata();
    }

    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Tune_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_TUNE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_TUNE_ID, object1.getString(JsonFields.TAG_TUNE_ID));
                            hashMap.put(JsonFields.TAG_TUNE_NAME, object1.getString(JsonFields.TAG_TUNE_NAME));
                            hashMap.put(JsonFields.TAG_TUNE_URL, object1.getString(JsonFields.TAG_TUNE_URL));
                            arrayList.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewTuneActivity.this);
                        rvtune.setLayoutManager(linearLayoutManager);

                        TuneAdapter tuneAdapter= new TuneAdapter(ViewTuneActivity.this, arrayList);
                        rvtune.setAdapter(tuneAdapter);
                    } else {
                        Toast.makeText(ViewTuneActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewTuneActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewTuneActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewTuneActivity.this);
        requestQueue.add(stringRequest);

    }
}