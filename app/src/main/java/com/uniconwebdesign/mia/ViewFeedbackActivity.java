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
import com.uniconwebdesign.mia.adapter.FeedbackAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewFeedbackActivity extends AppCompatActivity {


    @BindView(R.id.rvFeedback)
    RecyclerView rvFeedback;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        ButterKnife.bind(this);
        setTitle("Feedbacks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    void fetchdata()
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, WebUrl.VIEW_Feedback_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1"))
                    {
                        JSONArray jsonArray=object.getJSONArray(JsonFields.TAG_FEEDBACK);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_FEEDBACK_ID, object1.getString(JsonFields.TAG_FEEDBACK_ID));
                            hashMap.put(JsonFields.TAG_FEEDBACK_DATE, object1.getString(JsonFields.TAG_FEEDBACK_DATE));
                            hashMap.put(JsonFields.TAG_FEEDBACK_DESCRIPTION, object1.getString(JsonFields.TAG_FEEDBACK_DESCRIPTION));
                            hashMap.put(JsonFields.TAG_FEEDBACK_RATING, object1.getString(JsonFields.TAG_FEEDBACK_RATING));


                            arrayList.add(hashMap);

                        }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewFeedbackActivity.this);
                            rvFeedback.setLayoutManager(linearLayoutManager);

                            FeedbackAdapter feedbackAdapter = new FeedbackAdapter(ViewFeedbackActivity.this, arrayList);
                            rvFeedback.setAdapter(feedbackAdapter);





                    }
                    else
                    {
                        Toast.makeText(ViewFeedbackActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewFeedbackActivity.this, "Catch"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewFeedbackActivity.this, "Error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewFeedbackActivity.this);
        requestQueue.add(stringRequest);

    }

}
