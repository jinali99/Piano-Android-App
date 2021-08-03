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

public class AddTune extends AppCompatActivity {

    @BindView(R.id.etTuneName)
    TextInputEditText etTuneName;

    @BindView(R.id.etTuneUrl)
    TextInputEditText etTuneUrl;

    @BindView(R.id.etInstrumentId)
    TextInputEditText etInstrumentId;

    @BindView(R.id.btnAddTune)
    Button btnAddTune;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tune);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAddTune)
    public void insert_date() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.Add_Tune_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        Toast.makeText(AddTune.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddTune.this, jsonObject.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddTune.this, "Catch:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTune.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonFields.TAG_TUNE_NAME,etTuneName.getText().toString());
                map.put(JsonFields.TAG_TUNE_URL,etTuneUrl.getText().toString());
                map.put(JsonFields.TAG_INSTRUMENT_ID,etInstrumentId.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddTune.this);
        requestQueue.add(stringRequest);
    }

}
