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

public class RetrofitClient extends AppCompatActivity {
    private static Retrofit retrofit = null;
    private static RetrofitClient uniqInstance;
    Context ctx;
    private String TAG = "<><>Retrofit<><>";
    private RecyclerView.Adapter adapter;
    private APIResponse api_res;
    private ApiService apiService;
    public static SharedPreferences preferences;

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
    public void doBackProcess(final Context context, final Map<String, String> postParams,
                              final String from, final APIResponse api_res) {
        this.ctx = context;
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        apiService = RetrofitClient.getClient(context.getResources().getString(R.string.base_host_name)).create(ApiService.class);
        if (from.length() == 0) {
                try {
                    if(null==progressDialog)
                        progressDialog = new ProgressDialog((Activity) context);
                        progressDialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

        apiService.doGetRestaurants("M","16.9890648","82.2476477").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.e("response is ","<><>"+response.body());
//                        api_res.onSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            api_res.onFailure(call.toString());
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    } finally {
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog=null;
                        }
                    }

                } else {
                    if (from.length() == 0) {
                        if (null != progressDialog && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog=null;
                        }
                    }
                    api_res.onFailure("Data validation Error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }



    public void getRestaurants(String input_type){

    }
}
