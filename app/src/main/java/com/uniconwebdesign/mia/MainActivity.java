package com.uniconwebdesign.mia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Thread t1 = new Thread(){
            public void run() {
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i1 = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i1);
                }
            }
        };
        t1.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}