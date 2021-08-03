package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
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

public class AddFeedback extends AppCompatActivity {

    @BindView(R.id.etFeedbackDescripion)
    TextInputEditText etFeedbackDescripion;

    @BindView(R.id.etFeedbackRating)
    RatingBar etFeedbackRating;

    @BindView(R.id.btnAddFeedback)
    Button btnAddFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        ButterKnife.bind(this);
        setTitle("Post Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(R.id.btnAddFeedback)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_Feedback_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(AddFeedback.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddFeedback.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddFeedback.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddFeedback.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_FEEDBACK_DESCRIPTION, etFeedbackDescripion.getText().toString());
                map.put(JsonFields.TAG_FEEDBACK_RATING, "" + etFeedbackRating.getRating());
                map.put(JsonFields.TAG_USER_ID, getSharedPreferences("MyFile", 0).getString(JsonFields.TAG_USER_ID, ""));
                map.put(JsonFields.TAG_ACADEMY_ID, getIntent().getStringExtra(JsonFields.TAG_ACADEMY_ID));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddFeedback.this);
        requestQueue.add(stringRequest);
    }
}