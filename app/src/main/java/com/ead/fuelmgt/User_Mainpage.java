package com.ead.fuelmgt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ead.fuelmgt.Entity.FuelStation;
import com.ead.fuelmgt.services.ApiClient;
import com.ead.fuelmgt.services.FuelStationService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;

public class User_Mainpage extends AppCompatActivity {

    EditText area, station;
    Button showBtn;
    String getStationUrl = "http://192.168.1.4:5001/api/fuelstation/";
    String stationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mainpage);

        area = (EditText) findViewById(R.id.area_edt);
        station = (EditText) findViewById(R.id.station_edt);
        showBtn = (Button) findViewById(R.id.show_btn);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String city = area.getEditableText().toString();
                    String fuelStation = station.getEditableText().toString();
                    System.out.println("data : ...."+city+fuelStation);
                    FuelStationService fuelStationService = ApiClient.getClient().create(FuelStationService.class);

                    /*
                    //get station details according to the entered city and fuelStation
                    Call call = fuelStationService.findStation(city,fuelStation);
                    //Call call = fuelStationService.findAll();
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.body()==null) {
                                System.out.println("data : ...."+response.message());
                            }
                            //System.out.println("data : ...."+response.body().toString());
                            if (response.isSuccessful()) {
                                FuelStation shed = (FuelStation) response.body();
                                //List<Object> fuelStation1 = (List<Object>) response.body(); // 100.82.46.4 (port 53188)
                                Object fuelStation1 = response.body();
                                System.out.println("data status: ...."+shed.getStatus()+" obj "+fuelStation1);
                                //Log.i("OBJ.....", fuelStation1[0].getStationName());
                                Intent intent = new Intent(User_Mainpage.this, Home.class);
                                //intent.getExtras().putSerializable("stationObj", (Serializable) fuelStation1);
                                intent.getExtras().putString("name",shed.getStationName());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    */

                    RequestQueue queue = Volley.newRequestQueue(User_Mainpage.this);

                    JsonObjectRequest fuelStationRequest = new JsonObjectRequest(Request.Method.GET, getStationUrl.concat(city+"/"+fuelStation), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null) {
                                        System.out.println("response is not null...");
                                    }
                                    try {
                                        //JSONObject obj = response.getJSONObject("StationName");
                                        stationName = response.getString("StationName");
                                        System.out.println("name "+stationName);
                                        area.setText(stationName);
                                        Intent intent = new Intent(User_Mainpage.this, Home.class);
                                        intent.putExtra("station", stationName);
                                        startActivity(intent);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    queue.add(fuelStationRequest);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}