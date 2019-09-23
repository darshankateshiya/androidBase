package com.darshankateshiya.interviewdemo.api;

import android.app.Application;

public class ApplicationClient extends Application {

    private static ApplicationClient instance;
    private static ApiInterface api;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        api=ApiClient.getR().create(ApiInterface.class);

    }

    public static ApplicationClient getInstance(){
        return instance;
    }

    public static ApiInterface getApi() {
        return api;
    }
}
