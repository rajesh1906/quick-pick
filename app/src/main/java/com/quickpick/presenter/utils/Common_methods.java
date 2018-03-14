package com.quickpick.presenter.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewTreeObserver;
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


    public void ScreenLift(final View scroll, final View et, final int focus) {

                scroll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        scroll.getWindowVisibleDisplayFrame(r);
                        int heightDiff = scroll.getRootView().getHeight() - (r.bottom - r.top);
                        if (heightDiff > 100) {
                            if (et.hasFocus()) {
                                scroll.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        scroll.scrollTo(0, focus);
                                    }
                                }, 300);
                            }
                        }
                    }
                });


    }


}
