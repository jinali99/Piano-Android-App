package com.uniconwebdesign.mia.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.uniconwebdesign.mia.AddEnrollment;
import com.uniconwebdesign.mia.LogInActivity;
import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.ViewEnrollmentActivity;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.MyUtil;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public CourseAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_course, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvcoursename.setText(hashMap.get(JsonFields.TAG_COURSE_NAME));
        Glide.with(a).load(WebUrl.IMAGE_URL + hashMap.get(JsonFields.TAG_INSTRUMENT_PHOTO)).placeholder(R.drawable.music_logo).into(holder.tvinstrumentphoto);
        holder.btnAddEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtil.checkLoggedIn(a)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_Enrollment_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "onResponse: " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                                    Toast.makeText(a, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(a, ViewEnrollmentActivity.class);
                                    a.startActivity(i);
                                } else {
                                    Toast.makeText(a, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(a, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(a, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put(JsonFields.TAG_ENROLLMENT_AMOUNT, hashMap.get(JsonFields.TAG_COURSE_COST));
                            map.put(JsonFields.TAG_USER_ID, a.getSharedPreferences("MyFile", 0).getString(JsonFields.TAG_USER_ID, ""));
                            map.put(JsonFields.TAG_COURSE_ID, hashMap.get(JsonFields.TAG_COURSE_ID));
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(a);
                    requestQueue.add(stringRequest);
                } else {
                    Intent i = new Intent(a, LogInActivity.class);
                    a.startActivity(i);
                }
            }
        });
        holder.tvcoursecost.setText("Rs." + hashMap.get(JsonFields.TAG_COURSE_COST));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvcoursename)
        TextView tvcoursename;
        @BindView(R.id.tvinstrumentphoto)
        ImageView tvinstrumentphoto;
        @BindView(R.id.tvcoursecost)
        TextView tvcoursecost;

        @BindView(R.id.btnAddEnrollment)
        Button btnAddEnrollment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}