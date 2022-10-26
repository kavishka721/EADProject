package com.ead.fuelmgt.services;

import com.ead.fuelmgt.Entity.FuelStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface FuelStationService {

    @GET("/")
    Call<List<FuelStation>> findAll();

    @GET("/{stationId}")
    Call<FuelStation> find(@Query("StationId") int stationId);

    @PUT("update/status/{stationId}/{status}")
    Call<Void> updateStatus(@Query("StationId") int stationId, boolean status);

}
