package com.quickpick.presenter.services.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.quickpick.views.ui.BaseActivity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajesh Kumar on 15-02-2018.
 */

public class EndPoint  {

   Context context;
    ApiService apiService;
    EndPoint(Context context){

        this.context = context;
         apiService =((ConnectApiService) RetrofitClient.getInstance()).getApiService();

    }




    public void getResult(Map<String ,String > params,final APIResponse api_res ){
        params.put("InputType","M");
        for ( Map.Entry<String, String > entry : params.entrySet()) {
            String key = entry.getKey();
            String tab = entry.getValue();
            Log.e("key is ","<><"+key+" value is "+tab);
            // do something with key and/or tab
        }

        if(BaseActivity.haveNetworkConnection(context)){
            apiService.getApiResultCity(params.get("action")+"?",params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        try {

                            api_res.onSuccess(response.body());
                            Log.e("response url","url is=="+response.raw().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                api_res.onFailure(call.toString());
                            } catch (Exception ee) {
                                ee.printStackTrace();
                            }
                        } finally {
                            RetrofitClient.getInstance().hideProgressDialog();
                        }

                    } else {
                        RetrofitClient.getInstance().hideProgressDialog();
                        api_res.onFailure("Data validation Error");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }else{
           RetrofitClient.getInstance().hideProgressDialog();
            Toast.makeText(context, "please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }


}
