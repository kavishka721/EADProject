package com.ead.fuelmgt.Entity;

import com.google.gson.annotations.SerializedName;

public class FuelStation {

    @SerializedName("StationId")
    public int StationId;
    @SerializedName("StationName")
    public String StationName;
    @SerializedName("Status")
    public boolean Status;
    @SerializedName("Quantity")
    public int Quantity;
    @SerializedName("Queue")
    public int Queue;
    @SerializedName("City")
    public String City;

    public int getStationId() {
        return StationId;
    }

    public void setStationId(int stationId) {
        StationId = stationId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getQueue() {
        return Queue;
    }

    public void setQueue(int queue) {
        Queue = queue;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
