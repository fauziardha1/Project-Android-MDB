package com.example.pengenalanandroidmdb.service;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuth implements Interceptor {
    private String credentials;

    public BasicAuth(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticationRequest = request.newBuilder()
                .header("Authorization", credentials)
                .build();
        return chain.proceed(authenticationRequest);
    }
}
