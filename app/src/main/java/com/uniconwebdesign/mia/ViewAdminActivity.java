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
import com.uniconwebdesign.mia.adapter.AdminAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAdminActivity extends AppCompatActivity {

    @BindView(R.id.rvAdmin)
    RecyclerView rvAdmin;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin);
        ButterKnife.bind(this);
        setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchdata();
    }

    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Admin_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_ADMIN);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_ADMIN_ID, object1.getString(JsonFields.TAG_ADMIN_ID));
                            hashMap.put(JsonFields.TAG_ADMIN_NAME, object1.getString(JsonFields.TAG_ADMIN_NAME));
                            hashMap.put(JsonFields.TAG_ADMIN_MAIL, object1.getString(JsonFields.TAG_ADMIN_MAIL));

                            arrayList.add(hashMap);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAdminActivity.this);
                        rvAdmin.setLayoutManager(linearLayoutManager);

                        AdminAdapter adminAdapter = new AdminAdapter(ViewAdminActivity.this, arrayList);
                        rvAdmin.setAdapter(adminAdapter);


                    } else {
                        Toast.makeText(ViewAdminActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewAdminActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewAdminActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAdminActivity.this);
        requestQueue.add(stringRequest);


    }


}
