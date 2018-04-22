package com.quickpick.presenter.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.StoredDB;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.views.ui.SplashScreen;
import com.quickpick.views.ui.authentication.SignIn;

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


   public void popup(Context context,String coming_from){
       final Dialog dialog = new Dialog(context);
       // Include dialog.xml file
       dialog.setContentView(R.layout.signoutdialog);
       // Set dialog title
       TextView alert_text = (TextView)dialog.findViewById(R.id.alert_text);

       Button btn_yes = (Button)dialog.findViewById(R.id.btn_ok);
       Button btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);
       if(coming_from.equalsIgnoreCase("logout")){
           alert_text.setText("Are you sure want to signout ?");
       }else{
           alert_text.setText("Please login for use this feature!");
       }

       btn_yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//
               if(coming_from.equalsIgnoreCase("logout")) {

                   SharedPreferences clearNotificationSP = context.getSharedPreferences("MyPref", 0);
                   SharedPreferences.Editor editor = clearNotificationSP.edit();
                   editor.putString("id", "");
                  // editor.commit();

                   editor.remove("id");
                   editor.commit();
//                   StoredDB.pref.edit().putString("id", "");
//                   StoredDB.pref.edit().remove("MyPref").commit();
                   ((Activity) context).finish();
//                   SplashScreen.activity.finish();

               }
               ((Activity) context).startActivity(new Intent(context, SignIn.class));

           }
       });
       btn_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       dialog.show();
   }
}
