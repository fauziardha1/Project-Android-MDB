package com.example.pengenalanandroidmdb.service;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("office")
    Call<JsonObject> getOffice();
}
