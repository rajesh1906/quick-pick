package com.quick_pick.Presenter.services.Network;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public interface APIResponse {
    void onSuccess(String res);

    void onFailure(String res);
}
