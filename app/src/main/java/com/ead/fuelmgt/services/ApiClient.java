package com.ead.fuelmgt.services;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    //get retrofit client for connect with web services
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder() // backend http://100.82.46.4:53188
                .baseUrl("http://192.168.1.4:5001/api/fuelstation/") // 10.0.2.2:45461 192.168.1.4:5000 100.82.46.4:53188 http://localhost:49146/api/fuelstation/
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
