package com.hpe.apppulse.thinkasync;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
    }


    public void onNetworkClicked(View view) {
        Intent intent = new Intent(this, NetworkActivity.class);
        startActivity(intent);
    }

    public void onSerializationClicked(View view) {
        Intent intent = new Intent(this, SerializationActivity.class);
        startActivity(intent);
    }

    public void onUIClicked(View view) {
        Intent intent = new Intent(this, UiActivity.class);
        startActivity(intent);
    }


}
