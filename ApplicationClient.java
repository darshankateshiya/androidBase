package com.dc_app.dcapp_driver.Api;

import android.support.multidex.MultiDexApplication;

public class ApplicationClient extends MultiDexApplication {
    private static ApplicationClient instance;
    private ApiInterface api;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        api=ApiClient.GetR().create(ApiInterface.class);
    }

    public ApiInterface getApi() {
        return api;
    }

    public static ApplicationClient getInstance() {
        return instance;
    }
}
