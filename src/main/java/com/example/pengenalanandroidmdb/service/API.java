package com.example.pengenalanandroidmdb.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static Retrofit retrofit = null;

    private static OkHttpClient client() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth("apitraining", "password"))
                .build();

        return client;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://jamlima.multiintifinancialteknologi.co.id:8443/api/Training/")
                    .build();
        }
        return retrofit;
    }
}
