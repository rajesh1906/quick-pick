package com.quickpick.presenter.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Common_methods {

    Context context;



    public Common_methods(Context context){
        this.context = context;
    }


    public void hideKeyboard(View view) {

        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) ((Activity)context)
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openKeyboard(View view){
        InputMethodManager inputManager = (InputMethodManager) ((Activity)context)
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }


}
