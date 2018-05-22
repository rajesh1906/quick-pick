package com.quickpick.presenter.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quickpick.presenter.helper.HandlingViews;

/**
 * Created by ChRajeshKumar on 10/18/2016.
 */

public class Webview_implementation {
    public void startWebView(String url, WebView webview, final Context context) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setSavePassword(true);
        webview.getSettings().setSaveFormData(true);
        webview.getSettings().setEnableSmoothTransition(true);
        webview.getSettings().setSupportZoom(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {

                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    Activity activity = (Activity)context;
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Loading...");
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    HandlingViews handlingViews = (HandlingViews)context;
                    handlingViews.dialog_control();
//                    progress_download_google.setVisibility(View.GONE);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
//        webview.getSettings().setJavaScriptEnabled(true);
//
//        // Other webview options
//        webview.getSettings().setLoadWithOverviewMode(true);
//        webview.getSettings().setUseWideViewPort(true);
//        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webview.setScrollbarFadingEnabled(false);
//        webview.getSettings().setBuiltInZoomControls(true);
//        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webview.loadUrl(url);


    }
}
