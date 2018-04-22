package com.quickpick.views.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.quickpick.model.StoredDB;
import com.quickpick.views.ui.authentication.SignIn;
import com.quickpick.views.ui.dashboard.DashboardTabs;

/**
 * Created by Rajesh kumar on 21-04-2018.
 */

public class SplashScreen extends AppCompatActivity {
    public static Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        Log.e("login id is ","<>><>"+StoredDB.getInstance(this).getStorageValue("id"));
        if(null!=((String ) StoredDB.getInstance(this).getStorageValue("id"))) {
            if (((String) StoredDB.getInstance(this).getStorageValue("id")).length() != 0) {
                startActivity(new Intent(SplashScreen.this, DashboardTabs.class));

            } else {
                startActivity(new Intent(SplashScreen.this, SignIn.class));
            }
        }else{
            startActivity(new Intent(SplashScreen.this, SignIn.class));
        }

    }
}
