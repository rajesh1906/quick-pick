package com.quickpick.presenter.braintree.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PaymentMethodNonce;

import com.paypal.android.sdk.onetouch.core.PayPalOneTouchCore;
import com.quickpick.BuildConfig;
import com.quickpick.R;
import com.quickpick.presenter.braintree.api.internal.SignatureVerificationOverrides;
import com.quickpick.presenter.braintree.demo.internal.LogReporting;
import com.quickpick.presenter.braintree.demo.models.ClientToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements OnRequestPermissionsResultCallback,
        PaymentMethodNonceCreatedListener, BraintreeCancelListener, BraintreeErrorListener,
        ActionBar.OnNavigationListener {

    private static final String KEY_AUTHORIZATION = "com.braintreepayments.demo.KEY_AUTHORIZATION";

    protected String mAuthorization;
    protected String mCustomerId;
    protected BraintreeFragment mBraintreeFragment;
    protected Logger mLogger;

    private boolean mActionBarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogger = LoggerFactory.getLogger(getClass().getSimpleName());

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_AUTHORIZATION)) {
            mAuthorization = savedInstanceState.getString(KEY_AUTHORIZATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mActionBarSetup) {
            setupActionBar();
            mActionBarSetup = true;
        }

        SignatureVerificationOverrides.disableAppSwitchSignatureVerification(
                Settings.isPayPalSignatureVerificationDisabled(this));
        PayPalOneTouchCore.useHardcodedConfig(this, Settings.useHardcodedPayPalConfiguration(this));

        if (BuildConfig.DEBUG && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            handleAuthorizationState();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handleAuthorizationState();
    }

    private void handleAuthorizationState() {
        if (mAuthorization == null ||
                (Settings.useTokenizationKey(this) && !mAuthorization.equals(Settings.getEnvironmentTokenizationKey(this))) ||
                !TextUtils.equals(mCustomerId, Settings.getCustomerId(this))) {
            performReset();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAuthorization != null) {
            outState.putString(KEY_AUTHORIZATION, mAuthorization);
        }
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        mLogger.debug("Payment Method Nonce received: " + paymentMethodNonce.getTypeLabel());
    }

    @Override
    public void onCancel(int requestCode) {
        mLogger.debug("Cancel received: " + requestCode);
    }

    @Override
    public void onError(Exception error) {
        mLogger.debug("Error received (" + error.getClass() + "): "  + error.getMessage());
        mLogger.debug(error.toString());

        showDialog("An error occurred (" + error.getClass() + "): " + error.getMessage());
    }

    private void performReset() {
        mAuthorization = null;
        mCustomerId = Settings.getCustomerId(this);

        if (mBraintreeFragment == null) {
            mBraintreeFragment = (BraintreeFragment) getFragmentManager()
                    .findFragmentByTag(BraintreeFragment.TAG);
        }

        if (mBraintreeFragment != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getFragmentManager().beginTransaction().remove(mBraintreeFragment).commitNow();
            } else {
                getFragmentManager().beginTransaction().remove(mBraintreeFragment).commit();
                getFragmentManager().executePendingTransactions();
            }

            mBraintreeFragment = null;
        }

        reset();
        fetchAuthorization();
    }

    protected abstract void reset();

    protected abstract void onAuthorizationFetched();

    protected void fetchAuthorization() {
        if (mAuthorization != null) {
            onAuthorizationFetched();
        } else if (Settings.useTokenizationKey(this)) {
            mAuthorization = Settings.getEnvironmentTokenizationKey(this);
            onAuthorizationFetched();
        } else {
            DemoApplication.getApiClient(this).getClientToken(Settings.getCustomerId(this),
                    Settings.getMerchantAccountId(this), new Callback<ClientToken>() {
                        @Override
                        public void success(ClientToken clientToken, Response response) {
                            if (TextUtils.isEmpty(clientToken.getClientToken())) {
                                showDialog("Client token was empty");
                            } else {
                                mAuthorization = clientToken.getClientToken();
                                onAuthorizationFetched();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            showDialog("Unable to get a client token. Response Code: " +
                                    error.getResponse().getStatus() + " Response body: " +
                                    error.getResponse().getBody());
                        }
                    });
        }
    }

    protected void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    protected void setUpAsBack() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.environments, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(Settings.getEnvironment(this));
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        if (Settings.getEnvironment(this) != itemPosition) {
            Settings.setEnvironment(this, itemPosition);
            performReset();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.reset:
                performReset();
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.feedback:
                new LogReporting(this).collectAndSendLogs();
            default:
                return false;
        }
    }
}
