package com.quickpick.views.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.quickpick.R;
import com.quickpick.presenter.helper.HandlingViews;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.smoothprogress.SmoothProgressDrawable;
import com.quickpick.presenter.utils.WebViewClientImpl;
import com.quickpick.presenter.utils.Webview_implementation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 21-04-2018.
 */

public class PaymentView extends AppCompatActivity implements HandlingViews {

    @Bind(R.id.web_payment)
    WebView web_payment;
    @Bind(R.id.progress_download_google)
    ProgressBar progress_download_google;
    @Bind(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentview);
        ButterKnife.bind(this);
        loadWebview();
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void loadWebview(){
        progress_download_google.setVisibility(View.VISIBLE);
        progress_download_google.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(this).interpolator(new AccelerateInterpolator()).build());
        progress_download_google.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        new Webview_implementation().startWebView(APIS.PAYMENT_URL, web_payment, PaymentView.this);

    }

    @Override
    public void implementation(String message, int position) {

    }

    @Override
    public void conncetion(String value, String title, String description) {

    }

    @Override
    public int getContantField() {
        return 0;
    }

    @Override
    public void dialog_control() {
        progress_download_google.setVisibility(View.GONE);
    }
}
