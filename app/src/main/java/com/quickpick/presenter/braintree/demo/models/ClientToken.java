package com.quickpick.presenter.braintree.demo.models;

import com.google.gson.annotations.SerializedName;

public class ClientToken {

    @SerializedName("client_token")
    private String mClientToken;

    public String getClientToken() {
        return mClientToken;
    }
}
