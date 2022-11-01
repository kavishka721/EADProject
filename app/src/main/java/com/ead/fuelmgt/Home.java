package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sname = (TextView) findViewById(R.id.sname_tv);

        Intent intent = getIntent();
        sname.setText(intent.getStringExtra("station"));
    }
}