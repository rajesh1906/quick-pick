package com.quick_pick.Presenter.services.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.quick_pick.R;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public class RetrofitClient extends AppCompatActivity implements ConnectApiService {
    private static Retrofit retrofit = null;
    private static RetrofitClient uniqInstance;
    Context ctx;
    public final static String action = "<><>Retrofit<><>";
    private RecyclerView.Adapter adapter;
    private APIResponse api_res;
    private ApiService apiService;
    public static SharedPreferences preferences;
    EndPoint endPoint = null;
   public static ProgressDialog progressDialog;
    public static RetrofitClient getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new RetrofitClient();
        }
        return uniqInstance;
    }

    private static Retrofit getClient(String baseUrl) {
        //   if (retrofit == null) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new RetrofitConverter())
                .client(okHttpClient)
                .build();
        //  }
        return retrofit;
    }


    //RetrofitCallBack



    public EndPoint getEndPoint(Context context){
        this.ctx = context;
        if(endPoint==null){
            endPoint = new EndPoint(context);
        }
//        showProgressDialog(context);
        return endPoint;
    }



    private void showProgressDialog(Context context){
        try {
            if(null==progressDialog)
                progressDialog = new ProgressDialog((Activity) context);
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideProgressDialog(){
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }





    @Override
    public ApiService getApiService() {
        return  RetrofitClient.getClient(ctx.getResources().getString(R.string.base_host_name)).create(ApiService.class);
    }
}
