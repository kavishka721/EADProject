package com.ead.fuelmgt.services;

import com.ead.fuelmgt.Entity.FuelStation;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FuelStationService {

    @GET("/api/fuelstation")
    Call<List<FuelStation>> findAll();

    @GET("/api/fuelstation/{city}/{stationName}")
    Call<FuelStation> findStation(@Part String city, String stationName);
//    Call<FuelStation> findStation(@Query("city")String city, @Query("stationName") String stationName);

    @GET("{stationId}")
    Call<FuelStation> find(@Query("StationId") int stationId);

    @PUT("update/status/{stationId}/{status}")
    Call<Void> updateStatus(@Query("StationId") int stationId, boolean status);

    @PUT("update/queue/{stationId}")
    Call<Void> updateQueue(@Query("StationId") int stationId);

    @PUT("update/fuel/{stationId}")
    Call<Void> updateFuelQuantity(@Query("StationId") int stationId, int amount);

}
