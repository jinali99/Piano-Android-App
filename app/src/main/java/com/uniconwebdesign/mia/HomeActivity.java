package com.uniconwebdesign.mia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.uniconwebdesign.mia.adapter.InstrumentAdapter;
import com.uniconwebdesign.mia.adapter.MusicalAcademyAdapter;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.MyUtil;
import com.uniconwebdesign.mia.util.WebUrl;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.rvInstrument)
    RecyclerView rvInstrument;

    @BindView(R.id.rvMusicalAcademy)
    RecyclerView rvMusicalAcademy;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvUserName = header.findViewById(R.id.tvUserName);
        tvUserName.setText("Welcome , " + getSharedPreferences("MyFile", 0).getString(JsonFields.TAG_USER_NAME, "Guest"));
        navigationView.setNavigationItemSelectedListener(this);
        if (MyUtil.checkLoggedIn(HomeActivity.this)) {
            navigationView.inflateMenu(R.menu.activity_home_drawer_after_login);
        } else {
            navigationView.inflateMenu(R.menu.activity_home_drawer);
        }

        ButterKnife.bind(this);
        setTitle("Home");
        fetchdata1();
        fetchdata();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_register) {
            Intent i = new Intent(HomeActivity.this, AddUserActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_search) {
            Intent i = new Intent(HomeActivity.this, ViewEnrollmentActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_login) {
            Intent i = new Intent(HomeActivity.this, LogInActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(HomeActivity.this, AboutusActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_contact) {
            Intent i = new Intent(HomeActivity.this, ContactusActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_search) {
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_change_password) {
            Intent i = new Intent(HomeActivity.this, ChangePasswordActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor e = getSharedPreferences("MyFile", 0).edit();
            e.clear();
            e.commit();
            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void fetchdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_Instrument_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_INSTRUMENT);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_INSTRUMENT_ID, object1.getString(JsonFields.TAG_INSTRUMENT_ID));
                            hashMap.put(JsonFields.TAG_INSTRUMENT_NAME, object1.getString(JsonFields.TAG_INSTRUMENT_NAME));
                            hashMap.put(JsonFields.TAG_INSTRUMENT_PHOTO, object1.getString(JsonFields.TAG_INSTRUMENT_PHOTO));

                            arrayList.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false);
                        rvInstrument.setLayoutManager(linearLayoutManager);

                        InstrumentAdapter instrumentAdapter = new InstrumentAdapter(HomeActivity.this, arrayList);
                        rvInstrument.setAdapter(instrumentAdapter);

                    } else {
                        Toast.makeText(HomeActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(HomeActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);
    }

    void fetchdata1() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebUrl.VIEW_MusicalAcademy_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(JsonFields.TAG_SUCCESS).equals("1")) {
                        JSONArray jsonArray = object.getJSONArray(JsonFields.TAG_MUSICAL_ACADEMY);
                        for (int i = jsonArray.length() - 1; i >= 0; i--) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_ID, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_ID));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_NAME, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_NAME));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_EMAIL, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_EMAIL));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_LOGO, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_LOGO));
                            hashMap.put(JsonFields.TAG_MUSICAL_ACADEMY_MOBILENO, object1.getString(JsonFields.TAG_MUSICAL_ACADEMY_MOBILENO));
                            arrayList1.add(hashMap);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false);
                        rvMusicalAcademy.setLayoutManager(linearLayoutManager);

                        MusicalAcademyAdapter musicalAcademyAdapter = new MusicalAcademyAdapter(HomeActivity.this, arrayList1);
                        rvMusicalAcademy.setAdapter(musicalAcademyAdapter);
                    } else {
                        Toast.makeText(HomeActivity.this, object.getString(JsonFields.TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(HomeActivity.this, "Catch" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);
    }
}