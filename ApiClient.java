package com.darshankateshiya.interviewdemo.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static String baseURL = "http://test.videostatus.net//api/v6/";
    private static Retrofit r = null;

    public static Retrofit getR() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        if (r == null)
            r = new Retrofit.Builder().baseUrl(baseURL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();
        return r;
    }
}
