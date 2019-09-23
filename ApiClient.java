package prowebtech.com.discounter.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static android.content.Context.MODE_PRIVATE;


public class ApiClient {

    private static String baseUrl = "http://thediscounterapp.com/public/api/v1/";
    private static Retrofit r = null;

    static Retrofit getR(Context context) {

        if (r == null) {

            SharedPreferences sp = context.getSharedPreferences("token", MODE_PRIVATE);
            final String token = sp.getString("auth_token", "");
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
                    return chain.proceed(request);
                }
            });
            r = new Retrofit.Builder().client(client.build()).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return r;
    }

    static Retrofit resetR(Context context) {


        r = null;
        SharedPreferences sp = context.getSharedPreferences("token", MODE_PRIVATE);
        final String token = sp.getString("auth_token", "");
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
                return chain.proceed(request);
            }
        });
        r = new Retrofit.Builder().client(client.build()).baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        return r;
    }
}
