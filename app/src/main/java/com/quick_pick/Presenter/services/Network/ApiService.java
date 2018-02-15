package com.quick_pick.Presenter.services.Network;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public interface ApiService {


    @GET("/QuikPickApi/displayRestaurantNames?")
    Call<String > doGetRestaurants(@Query("InputType") String inputtype, @Query("latitude") String latitude, @Query("longitude") String longitude);
}
