package com.quickpick.views.ui.brain_tree;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.quickpick.R;
import com.quickpick.views.ui.BaseActivity;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rajesh Kumar on 23-05-2018.
 */
public class BrainTreeDashBoard extends BaseActivity {
    private static final String TAG = BrainTreeDashBoard.class.getSimpleName();
    private static final String PATH_TO_SERVER = "PATH_TO_SERVER";
    private String clientToken="eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIwYmUwZTk2MWQ1MzE1NTI0MDQ0NjFlZjUwYTI5NTRjZTQ4YjBmNDhjYTMyZDczY2NlZjczNzlmYmZhYzhmMzdifGNyZWF0ZWRfYXQ9MjAxOC0wNS0yM1QwNToyNDoyMi4zMzQ1NTc0MzIrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.brain_tree;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getClientTokenFromServer();
        Button buyNowButton = (Button)findViewById(R.id.buy_now);
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBraintreeSubmit(view);
            }
        });
    }

    public void onBraintreeSubmit(View view){
        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }

    private void getClientTokenFromServer(){
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.get(PATH_TO_SERVER, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, getString(R.string.token_failed) + responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseToken) {
                Log.d(TAG, "Client token: " + responseToken);
                clientToken = responseToken;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BRAINTREE_REQUEST_CODE){
            if (RESULT_OK == resultCode){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                //send to your server
                Log.d(TAG, "Testing the app here");
                sendPaymentNonceToServer(paymentNonce);
            }else if(resultCode == Activity.RESULT_CANCELED){
                Log.d(TAG, "User cancelled payment");
            }else {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d(TAG, " error exception");
            }
        }
    }

    private void sendPaymentNonceToServer(String paymentNonce){
        RequestParams params = new RequestParams("NONCE", paymentNonce);
        AsyncHttpClient androidClient = new AsyncHttpClient();
        androidClient.post(PATH_TO_SERVER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "Error: Failed to create a transaction");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "Output " + responseString);
            }
        });
    }
}
