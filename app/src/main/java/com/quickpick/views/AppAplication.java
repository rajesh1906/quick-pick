package com.quickpick.views;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

/**
 * Created by ashok on 18/02/18.
 */

public class AppAplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d("","inside onCreate()...................... ");
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
