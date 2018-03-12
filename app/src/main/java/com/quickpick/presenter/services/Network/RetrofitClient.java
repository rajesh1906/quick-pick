package com.quickpick.presenter.services.Network;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.quickpick.views.ui.customviews.CustomProgressbar;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public class RetrofitClient extends AppCompatActivity implements ConnectApiService {
    private static Retrofit retrofit = null;
    private static RetrofitClient uniqInstance;
    Context ctx;
    EndPoint endPoint = null;
    public static RetrofitClient getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new RetrofitClient();
        }
        return uniqInstance;
    }

    private static Retrofit getClient(String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new RetrofitConverter())
                .client(okHttpClient)
                .build();

        return retrofit;
    }





    public EndPoint getEndPoint(Context context, String progress_bar_status){
        this.ctx = context;

            if (endPoint == null) {
                endPoint = new EndPoint(context);
            }
            if (progress_bar_status.length()!=0) {
                showProgressDialog(context);
            }
        return endPoint;
    }



    private void showProgressDialog(Context context){

        CustomProgressbar.showProgressBar(context, false);
    }

    public void hideProgressDialog(){
        CustomProgressbar.hideProgressBar();
    }
    @Override
    public ApiService getApiService() {
        return  RetrofitClient.getClient(APIS.BASEURL).create(ApiService.class);
    }
}
