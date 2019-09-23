package prowebtech.com.discounter.api;

import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDexApplication;


public class ApplicationClient extends Application {

    private static ApplicationClient instance;
    private static ApiInterface api;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setClient(ApplicationClient.this);
    }

    public static void setClient(Context applicationClient){
        api = ApiClient.getR(applicationClient).create(ApiInterface.class);
    }

    public static void reSetClient(Context applicationClient){
        api = ApiClient.resetR(applicationClient).create(ApiInterface.class);
    }



    public static ApplicationClient getInstance() {
        return instance;
    }

    public static ApiInterface getApi() {
        return api;
    }

}
