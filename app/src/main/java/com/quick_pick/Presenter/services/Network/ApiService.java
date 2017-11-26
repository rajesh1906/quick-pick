package com.quick_pick.Presenter.services.Network;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("/mobile_api_json/")
    Call<String> getApiResult(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("/mobile_api_json/")
    Call<String> uploadImage(@Part MultipartBody.Part file, @PartMap() Map<String, String> fields);
}
