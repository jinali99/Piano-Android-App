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
import com.uniconwebdesign.mia.adapter.PaymentAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPaymentActivity extends AppCompatActivity {

    @BindView(R.id.rvPayment)
    RecyclerView rvPayment;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);
        ButterKnife.bind(this);

    }

    void fetchdata()
    {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, WebUrl.VIEW_Payment_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object=new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1"))
                    {
                        JSONArray jsonArray=object.getJSONArray(JsonFields.TAG_PAYMENT);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_PAYMENT_ID, object1.getString(JsonFields.TAG_PAYMENT_ID));
                            hashMap.put(JsonFields.TAG_PAYMENT_AMOUNT, object1.getString(JsonFields.TAG_PAYMENT_AMOUNT));
                            hashMap.put(JsonFields.TAG_PAYMENT_DATE, object1.getString(JsonFields.TAG_PAYMENT_DATE));
                            hashMap.put(JsonFields.TAG_PAYMENT_MODE, object1.getString(JsonFields.TAG_PAYMENT_MODE));
                            hashMap.put(JsonFields.TAG_PAYMENT_STATUS, object1.getString(JsonFields.TAG_PAYMENT_STATUS));


                            arrayList.add(hashMap);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewPaymentActivity.this);
                        rvPayment.setLayoutManager(linearLayoutManager);

                        PaymentAdapter paymentAdapter = new PaymentAdapter(ViewPaymentActivity.this, arrayList);
                        rvPayment.setAdapter(paymentAdapter);


                    }
                    else
                    {
                        Toast.makeText(ViewPaymentActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(ViewPaymentActivity.this, "Catch"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewPaymentActivity.this, "Error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewPaymentActivity.this);
        requestQueue.add(stringRequest);


    }



}

