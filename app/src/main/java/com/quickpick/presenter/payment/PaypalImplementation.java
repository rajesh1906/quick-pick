package com.quickpick.presenter.payment;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/**
 * Created by Rajesh kumar on 27-03-2018.
 */

public class PaypalImplementation {

    public static final int PAYPAL_REQUEST_CODE = 123;

    public static PaypalImplementation paypalImplementation=null;


    public static PaypalImplementation getInstance(){
        if(paypalImplementation==null){
            paypalImplementation = new PaypalImplementation();
        }
        return paypalImplementation;
    }


    public PayPalConfiguration getPPConfigrationInstance(){
       return   new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    }







}
