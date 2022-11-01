package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ead.fuelmgt.Entity.FuelStation;

public class StationDetails extends AppCompatActivity {

    TextView city, name, status, quantity, queue;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        Intent intent = getIntent();
        FuelStation fuelStation = (FuelStation) intent.getSerializableExtra("stationObj");

        city = (TextView) findViewById(R.id.city_view);
        name = (TextView) findViewById(R.id.sname_view);
        status = (TextView) findViewById(R.id.status_view);
        quantity = (TextView) findViewById(R.id.quantity_view);
        queue = (TextView) findViewById(R.id.queue_view);

        Log.i("VALUES",fuelStation.getStationName()+fuelStation.getCity());
        city.setText(fuelStation.getCity());
        name.setText(fuelStation.getStationName());
        if(fuelStation.getStatus()) {
            status.setText("Available");
        } else {
            status.setText("Unavailable");
        }
        quantity.setText(fuelStation.getQuantity());
    }
}