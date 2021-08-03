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
import com.uniconwebdesign.mia.adapter.VideoAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewVideoActivity extends AppCompatActivity {


    @BindView(R.id.rvVideo)
    RecyclerView rvVideo;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);
        ButterKnife.bind(this);
        fetchdata();
    }


    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Video_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_VIDEO);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_VIDEO_ID, object1.getString(JsonFields.TAG_VIDEO_ID));
                            hashMap.put(JsonFields.TAG_VIDEO_TITLE, object1.getString(JsonFields.TAG_VIDEO_TITLE));
                            hashMap.put(JsonFields.TAG_VIDEO_URL, object1.getString(JsonFields.TAG_VIDEO_URL));
                            hashMap.put(JsonFields.TAG_VIDEO_DESCRIPTION, object1.getString(JsonFields.TAG_VIDEO_DESCRIPTION));

                            arrayList.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewVideoActivity.this);
                        rvVideo.setLayoutManager(linearLayoutManager);

                        VideoAdapter videoAdapter = new VideoAdapter(ViewVideoActivity.this, arrayList);
                        rvVideo.setAdapter(videoAdapter);


                    } else {
                        Toast.makeText(ViewVideoActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewVideoActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewVideoActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewVideoActivity.this);
        requestQueue.add(stringRequest);


    }


}
