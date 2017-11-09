package com.gw.banner.sample;

import android.app.Application;

/**
 * Created by GongWen on 17/12/4.
 */

public class MainApplication extends Application {
    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
