package com.quickpick.views.ui.dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michael.easydialog.EasyDialog;
import com.quickpick.R;
import com.quickpick.model.Category;
import com.quickpick.model.Cities;
import com.quickpick.model.restaurant_category.RestaurantData;
import com.quickpick.model.restaurant_category.Restaurant_names;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.presenter.utils.GPSTracker;
import com.quickpick.views.adapters.ShowRestaurant_Adapter;
import com.quickpick.views.ui.BaseActivity;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 07-03-2018.
 */

public class DashBoardActivityNew extends BaseActivity implements GetCategory_Id, View.OnClickListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.txt_no_res)
    TextView txt_no_res;
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.container_serach)
    FrameLayout container_serach;
    @Bind(R.id.img_back)
    LinearLayout img_back;
    @Bind(R.id.edt_txt_search)
    EditText edt_txt_search;
    @Bind(R.id.list_cities)
    ListView list_cities;
    @Bind(R.id.menu_category)
    FloatingActionButton menu_category;
    @Bind(R.id.img_cancel)
    ImageView img_cancel;
    @Bind(R.id.txt_header)
    TextView txt_header;


    int from;
    ArrayList<RestaurantData> dataList = new ArrayList<>();
    Restaurant_names restaurant_name, temp_pojo;
    int spage = 1, no_records = 0;
    private boolean loading;
    private boolean scrollflag = true;
    ShowRestaurant_Adapter adapter;
    ArrayList<String> city_items = new ArrayList<>();
    ArrayList<String> al_city_id = new ArrayList<>();
    String city_id = "1", category_id = "";
    public final int REQUEST_CODE_ASK_LOCATION_PERMISSIONS = 124;
    GPSTracker gpsTracker;
    String lat="",lng="";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activitydashboard_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        txt_header.setText("Search by City");
        txt_header.setTextColor(getResources().getColor(R.color.black));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        img_search.setVisibility(View.VISIBLE);
        fetchData("restaurnts", "show_progress");
        fetchLisiner();
        list_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("city name is ", "<>>" + city_items.get(i));
                Log.e("city id is ", "<>>" + al_city_id.get(i));
                city_id = al_city_id.get(i);
                txt_header.setText(city_items.get(i));
                new Common_methods(DashBoardActivityNew.this).hideKeyboard(edt_txt_search);
                container_serach.setVisibility(View.GONE);
                fetchData("restaurnts", "show_progress");
            }
        });

    }

    private void fetchLisiner() {

        img_search.setOnClickListener(this);
        img_back.setOnClickListener(this);
        menu_category.setOnClickListener(this);
        img_cancel.setOnClickListener(this);
        edt_txt_search.addTextChangedListener(new CustomWatcher(edt_txt_search));

    }



    private void fetchData(final String coming_from, final String prograss_bar_status) {

        RetrofitClient.getInstance().getEndPoint(this, prograss_bar_status).getResult(getParams(coming_from), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response ", "<><>" + res);

                switch (coming_from) {
                    case "restaurnts":
                        restaurant_name = new Gson().fromJson(res, Restaurant_names.class);
                        if (restaurant_name.getStatus().equalsIgnoreCase("successfully")) {
                            if (spage == 1) {
                                temp_pojo = restaurant_name;
                            } else {
                                dataList = (ArrayList<RestaurantData>) temp_pojo.getRestaurantData();
                                dataList.addAll(restaurant_name.getRestaurantData());
                                temp_pojo.setRestaurantData(dataList);
                                loading = false;
                            }
                            if (spage == 1) {
                                adapter = new ShowRestaurant_Adapter(DashBoardActivityNew.this, temp_pojo.getRestaurantData(),null);
                                recyclerview.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                            if (restaurant_name.getRestaurantData().size() == 0 && spage == 1) {
                                txt_no_res.setVisibility(View.VISIBLE);
                            } else {
                                txt_no_res.setVisibility(View.GONE);
                            }


                  /*  if (restaurant_name.getRestaurantData().size() < 2) {
                        scrollflag = false;
                    }*/

                        } else {
                            Toast.makeText(DashBoardActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "cities":
                        if (city_items.size() != 0) {
                            city_items.clear();
                            al_city_id.clear();
                        }
                        Cities cities = new Gson().fromJson(res, Cities.class);
                        if (cities.getStatus().equalsIgnoreCase("successfully")) {
                            for (int i = 0; i < cities.getRestaurantData().size(); i++) {
                                city_items.add(cities.getRestaurantData().get(i).getCityName());
                                al_city_id.add(cities.getRestaurantData().get(i).getCityId());

                                Log.e("city data is ", "<><>" + cities.getRestaurantData().get(i).getCityName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DashBoardActivityNew.this, R.layout.search_item, R.id.txt_item, city_items.toArray(new String[city_items.size()]));
                            list_cities.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (cities.getRestaurantData().size() == 0) {
                                Toast.makeText(DashBoardActivityNew.this, "No Search Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DashBoardActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "category":
                        Log.e("getting category is ", "<><>" + res);
                        Category category = new Gson().fromJson(res, Category.class);
                        ArrayList<String> category_items = new ArrayList<>();
                        if (category.getStatus().equalsIgnoreCase("successfully")) {
                            for (int i = 0; i < category.getCateogryData().size(); i++) {
                                Log.e("category names is ", "<><>" + category.getCateogryData().get(i).getCategory_Name());
                                category_items.add(category.getCateogryData().get(i).getCategory_Name());

                            }
                            CustomDialog.getInstance().showTooltip(DashBoardActivityNew.this, category_items,menu_category,null);


                        } else {
                            Toast.makeText(DashBoardActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "category_id":


                        restaurant_name = new Gson().fromJson(res, Restaurant_names.class);
                        if (restaurant_name.getStatus().equalsIgnoreCase("successfully")) {
                            if (spage == 1) {
                                temp_pojo = restaurant_name;
                            } else {
                                dataList = (ArrayList<RestaurantData>) temp_pojo.getRestaurantData();
                                dataList.addAll(restaurant_name.getRestaurantData());
                                temp_pojo.setRestaurantData(dataList);
                                loading = false;
                            }
                            if (spage == 1) {
                                adapter = new ShowRestaurant_Adapter(DashBoardActivityNew.this, temp_pojo.getRestaurantData(),null);
                                recyclerview.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                            if (restaurant_name.getRestaurantData().size() == 0 && spage == 1) {
                                txt_no_res.setVisibility(View.VISIBLE);
                            } else {
                                txt_no_res.setVisibility(View.GONE);
                            }


                  /*  if (restaurant_name.getRestaurantData().size() < 2) {
                        scrollflag = false;
                    }*/

                        } else {
                            Toast.makeText(DashBoardActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }


                        break;
                }

            }

            @Override
            public void onFailure(String res) {

            }
        });

    }

    @Override
    public void getId(int id) {

        category_id = "" + id;
        fetchData("category_id", "show_progress");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_search:
                edt_txt_search.setText("");
                container_serach.setVisibility(View.VISIBLE);
                edt_txt_search.requestFocus();
                new Common_methods(DashBoardActivityNew.this).openKeyboard(edt_txt_search);
                break;
            case R.id.img_back:
                new Common_methods(DashBoardActivityNew.this).hideKeyboard(edt_txt_search);
                container_serach.setVisibility(View.GONE);
                break;
            case R.id.menu_category:
                fetchData("category", "");
//                showTooltip();
                break;
            case R.id.img_cancel:
                edt_txt_search.setText("");
                break;
        }

    }

    private class CustomWatcher implements TextWatcher {

        private View view;

        public CustomWatcher(View view) {
            this.view = view;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            switch (view.getId()) {

                case R.id.edt_txt_search:
                    if (edt_txt_search.getText().length() >= 1) {

                        fetchData("cities", "");
                    }
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    private Map<String, String> getParams(String coming_from) {
        Map<String, String> params = new HashMap<>();
        switch (coming_from) {
            case "restaurnts":
                params.put("action", APIS.Defaultrestarents);
                params.put("CityId", city_id);
                params.put("FlagSlNo", "0");
                params.put("latitude",lat);
                params.put("longitude",lng);
                break;
            case "cities":
                params.put("Text", edt_txt_search.getText().toString());
                params.put("FlagSlNo", "0");
//                params.put("action", getResources().getString(R.string.getCities));
                params.put("action", APIS.CITIES);
                break;
            case "category":
                params.put("action", APIS.Category);
                break;
            case "category_id":
                params.put("action", APIS.RestarentsBasedCategory);
                params.put("CityId", city_id);
                params.put("FlagSlNo", "0");
                params.put("CatrgoryId", category_id);
                break;
        }


        return params;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (container_serach.isShown()) {
            container_serach.setVisibility(View.GONE);
        } else if (!city_id.equals("1")||!txt_header.getText().toString().equalsIgnoreCase("Search by City")) {
            city_id = "1";
            txt_header.setText("Search by City");
            fetchData("restaurnts", "show_progress");
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(permission_Location()) {
            gpsTracker = new GPSTracker(this);
            if (!gpsTracker.isLocationAvailable()) {
                CustomDialog.getInstance().commonDialog(this,
                        "Please Enable Location Services",
                        "Go to Settings -> Location Service ->" +
                                "Check on Use GPS, Wi-Fi or mobile networks to determine location ","location");
            }else{
                lat = String.valueOf(gpsTracker.getLatitude());
                lng = String.valueOf(gpsTracker.getLongitude());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_LOCATION_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // if (gpsTracker == null)
                    gpsTracker = new GPSTracker(this);
                    if (gpsTracker.isLocationAvailable()) {
                        Log.e("location", gpsTracker.getLatitude() + ":::" + gpsTracker.getLongitude());
                        lat = String.valueOf(gpsTracker.getLatitude());
                        lng = String.valueOf(gpsTracker.getLongitude());
                        if(String.valueOf(gpsTracker.getLatitude()).equals("0.0")){
                            gpsTracker.getLocation();
                        }

                    } else {
                        CustomDialog.getInstance().commonDialog(this,
                                "Please Enable Location Services",
                                "Go to Settings -> Location Service ->" +
                                        "Check on Use GPS, Wi-Fi or mobile networks to determine location ","location");
                    }

                }else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    CustomDialog.getInstance().commonDialog(this,
                            "Your Location permission Denied",
                            "Go to Settings and Grant the permission to use better features. ","location permssion");
//                Toast.makeText(DashBoardActivityNew.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
                // User selected the Never Ask Again Option
            } else {
                Toast.makeText(DashBoardActivityNew.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }

    public boolean permission_Location() {
        int hasStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_LOCATION_PERMISSIONS);
                return false;
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_LOCATION_PERMISSIONS);

            return false;
        }
        return true;
    }
}
