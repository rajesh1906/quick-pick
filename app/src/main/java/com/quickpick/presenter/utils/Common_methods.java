package com.quickpick.presenter.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.quickpick.presenter.services.Network.APIS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Common_methods {

    Context context;
    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;
    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_-]{3,15}$";

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

    public static boolean validateEmail(EditText editText) {
        String text = editText.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (text.length() != 0 && text.charAt(0) != '_' && text.matches(emailPattern) && text.length() > 5) {
            return false;
        }
        return true;
    }
    public static boolean hasMobileNumber(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text.length() == 10) {
            String pattern = "^[6-9][0-9]{9}$";
            if (text.matches(pattern)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validateUsername(EditText editText){
        String text = editText.getText().toString();

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }


    public void makePayment(){
        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };

        CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();

        chromeCustomTabExample();
    }
    public void chromeCustomTabExample() {
        mCustomTabsIntent.launchUrl((Activity) context, Uri.parse(APIS.PAYMENT_URL));
    }
}
