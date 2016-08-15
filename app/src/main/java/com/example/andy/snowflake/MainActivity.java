package com.example.andy.snowflake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SnowSurfaceView snowSurfaceView = new SnowSurfaceView(this);
        setContentView(snowSurfaceView);
    }
}
