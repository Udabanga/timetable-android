package com.example.timetableio.api;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_Connection {
    private API api;
    private String URL;

    public API_Connection()
    {
        API_BASE_URL base = new API_BASE_URL();
        URL = base.API_URL();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create());

        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();

        this.api = retrofit.create(API.class);
    }

    public API getConnection()
    {
        return api;
    }

}
