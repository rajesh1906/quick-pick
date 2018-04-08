package com.quickpick.views.ui.dashboard.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quickpick.R;
import com.quickpick.model.Category;
import com.quickpick.model.Cities;
import com.quickpick.model.adds.AddsData;
import com.quickpick.model.adds.AddsRoot;
import com.quickpick.model.restaurant_category.RestaurantData;
import com.quickpick.model.restaurant_category.Restaurant_names;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.ApiService;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.views.adapters.ShowRestaurant_Adapter;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.DashboardTabs;
import com.quickpick.views.ui.dashboard.GetCategory_Id;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rajesh Kumar on 02-04-2018.
 */
public class EatsFragment extends Fragment implements Calling_Fragment, GetCategory_Id, View.OnClickListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.txt_no_res)
    TextView txt_no_res;
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.img_cart)
    ImageView img_cart;
    @Bind(R.id.container_serach)
    FrameLayout container_serach;
    @Bind(R.id.img_back)
    LinearLayout img_back;
    @Bind(R.id.edt_txt_search)
    EditText edt_txt_search;
    @Bind(R.id.list_cities)
    ListView list_cities;
    @Bind(R.id.ll_category)
    LinearLayout ll_category;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.view3)
    View view3;
    @Bind(R.id.txt_header)
    TextView txt_header;

    @Bind(R.id.txt_book_table)
    TextView txt_book_table;
    @Bind(R.id.txt_eat)
    TextView txt_eat;
    @Bind(R.id.txt_pickup)
    TextView txt_pickup;
    @Bind(R.id.txt_delivery)
    TextView txt_delivery;
    @Bind(R.id.img_cancel)
    ImageView img_cancel;



    ArrayList<RestaurantData> dataList = new ArrayList<>();
    Restaurant_names restaurant_name, temp_pojo;
    int spage = 1, no_records = 0;
    private boolean loading;
    private boolean scrollflag = true;
    ShowRestaurant_Adapter adapter;
    ArrayList<String> city_items = new ArrayList<>();
    ArrayList<String> al_city_id = new ArrayList<>();
    String city_id = "8", category_id = "";
    String lat = "16.989065", lng = "82.247465";
    com.rey.material.widget.FloatingActionButton floatingActionButton;
    ArrayList<AddsData> addsData;

    public static EatsFragment newInstance(int index) {
        EatsFragment fragment = new EatsFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activitydashboard_new, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        ll_category.setVisibility(View.VISIBLE);
        view1.setMinimumHeight(ll_category.getMeasuredHeight());
        view2.setMinimumHeight(ll_category.getMeasuredHeight());
        view3.setMinimumHeight(ll_category.getMeasuredHeight());
        img_search.setVisibility(View.VISIBLE);
        img_cart.setVisibility(View.VISIBLE);
        setVolexBG("grey", "grey", "grey", "grey");
        HashMap<String, String> params = ((DashboardTabs) getActivity()).getLatLongs();

        lat = params.get("lat");
        lng = params.get("lng");

        Log.e("lat is ", "<><>" + params.get("lat") + " lng " + params.get("lng"));
        fetchData("adds", "");
        fetchData("getCity_Id", "");



        fetchListerns();

        list_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("city name is ", "<>>" + city_items.get(i));
                Log.e("city id is ", "<>>" + al_city_id.get(i));
                city_id = al_city_id.get(i);
                txt_header.setText(city_items.get(i));
                new Common_methods(getActivity()).hideKeyboard(edt_txt_search);
                container_serach.setVisibility(View.GONE);
                fetchData("restaurnts", "show_progress");
            }
        });

//        fetchParllelData();
        return view;
    }


    private void fetchListerns() {
        txt_book_table.setOnClickListener(this);
        txt_eat.setOnClickListener(this);
        txt_pickup.setOnClickListener(this);
        txt_delivery.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_cancel.setOnClickListener(this);
        edt_txt_search.addTextChangedListener(new CustomWatcher(edt_txt_search));
    }

    private void fetchData(final String coming_from, final String prograss_bar_status) {

        RetrofitClient.getInstance().getEndPoint(getActivity(), prograss_bar_status).getResult(getParams(coming_from), new APIResponse() {
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
                                adapter = new ShowRestaurant_Adapter(getActivity(), temp_pojo.getRestaurantData(),EatsFragment.this);
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
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.search_item, R.id.txt_item, city_items.toArray(new String[city_items.size()]));
                            list_cities.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (cities.getRestaurantData().size() == 0) {
                                Toast.makeText(getActivity(), "No Search Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            CustomDialog.getInstance().showTooltip(getActivity(), category_items, floatingActionButton, EatsFragment.this);


                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
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
                                adapter = new ShowRestaurant_Adapter(getActivity(), temp_pojo.getRestaurantData(),EatsFragment.this);
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
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }


                        break;

                    case "getCity_Id":
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            if (jsonObject.getString("Status").equalsIgnoreCase("successfully")) {
                                txt_header.setText(jsonObject.getString("CityId"));

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case "adds":
                        try{
                            AddsRoot data = new Gson().fromJson(res,AddsRoot.class);
                            if(data.getStatus().equalsIgnoreCase("successfully")){

                                addsData = data.getAddsData();
                                for(int i=0;i<data.getAddsData().size();i++){
                                    Log.e("first url url is ","<><"+data.getAddsData().get(i).getFirsturls());
                                    Log.e("second url is ","<><"+data.getAddsData().get(i).getSecondurls());
                                }


                            }
                            fetchData("restaurnts", "show_progress");

                        }catch (Exception e) {
                            e.printStackTrace();
                    }


                        break;
                }

            }

            @Override
            public void onFailure(String res) {

            }
        });

    }

    private Map<String, String> getParams(String coming_from) {
        Map<String, String> params = new HashMap<>();
        switch (coming_from) {
            case "restaurnts":
                params.put("action", APIS.DefultRestaurantLoading);
                params.put("CityId", city_id);
                params.put("FlagSlNo", "0");
                params.put("latitude", lat);
                params.put("longitude", lng);
                params.put("typeofways", "EatIn");
                params.put("foodandbeverage", "Food");
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
                params.put("typeofways", "EatIn");
                params.put("foodandbeverage", "Food");
                break;
            case "getCity_Id":
                params.put("action", APIS.GettingResDataBasedOnLat);
                params.put("LatLng", lat + "," + lng);
                break;
            case "adds":
                params.put("action", APIS.AddsLoading);
                params.put("CityId", city_id);
                break;
        }


        return params;
    }

    @Override
    public void calling(com.rey.material.widget.FloatingActionButton floatingActionButton) {
        Log.e("coming to fragment", "<><>");
        this.floatingActionButton = floatingActionButton;
        fetchData("category", "");
    }

    @Override
    public ArrayList<AddsData> getAddsData() {
        return addsData;
    }

    @Override
    public void getId(int id) {
        category_id = "" + id;
        fetchData("category_id", "show_progress");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_book_table:
                if (txt_book_table.getTag().equals("grey")) {
                    setVolexBG("green", "grey", "grey", "grey");
                    txt_book_table.setBackgroundResource(R.drawable.txt_background);
                    txt_book_table.setTextColor(Color.WHITE);

                    txt_eat.setBackgroundResource(R.color.transparent_color);
                    txt_eat.setTextColor(getResources().getColor(R.color.black));

                    txt_pickup.setBackgroundResource(R.color.transparent_color);
                    txt_pickup.setTextColor(getResources().getColor(R.color.black));

                    txt_delivery.setBackgroundResource(R.color.transparent_color);
                    txt_delivery.setTextColor(getResources().getColor(R.color.black));
                } else {
                    setVolexBG("grey", "grey", "grey", "grey");
                    txt_book_table.setBackgroundResource(R.color.transparent_color);
                    txt_book_table.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.txt_eat:

                if (txt_eat.getTag().equals("grey")) {
                    setVolexBG("grey", "green", "grey", "grey");

                    txt_eat.setBackgroundResource(R.drawable.txt_background);
                    txt_eat.setTextColor(Color.WHITE);

                    txt_book_table.setBackgroundResource(R.color.transparent_color);
                    txt_book_table.setTextColor(getResources().getColor(R.color.black));

                    txt_pickup.setBackgroundResource(R.color.transparent_color);
                    txt_pickup.setTextColor(getResources().getColor(R.color.black));

                    txt_delivery.setBackgroundResource(R.color.transparent_color);
                    txt_delivery.setTextColor(getResources().getColor(R.color.black));
                } else {
                    setVolexBG("grey", "grey", "grey", "grey");
                    txt_eat.setBackgroundResource(R.color.transparent_color);
                    txt_eat.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.txt_pickup:

                if (txt_pickup.getTag().equals("grey")) {
                    setVolexBG("grey", "grey", "green", "grey");

                    txt_pickup.setBackgroundResource(R.drawable.txt_background);
                    txt_pickup.setTextColor(Color.WHITE);

                    txt_book_table.setBackgroundResource(R.color.transparent_color);
                    txt_book_table.setTextColor(getResources().getColor(R.color.black));

                    txt_eat.setBackgroundResource(R.color.transparent_color);
                    txt_eat.setTextColor(getResources().getColor(R.color.black));

                    txt_delivery.setBackgroundResource(R.color.transparent_color);
                    txt_delivery.setTextColor(getResources().getColor(R.color.black));
                } else {
                    setVolexBG("grey", "grey", "grey", "grey");
                    txt_pickup.setBackgroundResource(R.color.transparent_color);
                    txt_pickup.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.txt_delivery:

                if (txt_delivery.getTag().equals("grey")) {
                    setVolexBG("grey", "grey", "grey", "green");

                    txt_delivery.setBackgroundResource(R.drawable.txt_background);
                    txt_delivery.setTextColor(Color.WHITE);

                    txt_pickup.setBackgroundResource(R.color.transparent_color);
                    txt_pickup.setTextColor(getResources().getColor(R.color.black));

                    txt_book_table.setBackgroundResource(R.color.transparent_color);
                    txt_book_table.setTextColor(getResources().getColor(R.color.black));

                    txt_eat.setBackgroundResource(R.color.transparent_color);
                    txt_eat.setTextColor(getResources().getColor(R.color.black));

                } else {
                    setVolexBG("grey", "grey", "grey", "grey");
                    txt_delivery.setBackgroundResource(R.color.transparent_color);
                    txt_delivery.setTextColor(getResources().getColor(R.color.black));
                }
                break;

            case R.id.img_search:
                edt_txt_search.setText("");
                container_serach.setVisibility(View.VISIBLE);
                edt_txt_search.requestFocus();
                new Common_methods(getActivity()).openKeyboard(edt_txt_search);
                break;
            case R.id.img_back:
                new Common_methods(getActivity()).hideKeyboard(edt_txt_search);
                container_serach.setVisibility(View.GONE);
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


    private void setVolexBG(String t1, String t2, String t3, String t4) {
        txt_book_table.setTag(t1);
        txt_eat.setTag(t2);
        txt_pickup.setTag(t3);
        txt_delivery.setTag(t4);
    }

    void fetchParllelData(){
        Observable.just(RetrofitClient.getInstance().getClient(APIS.BASEURL).create(ApiService.class)).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<String> observable[] = new Observable[2];

                    Observable<String> city_observable = s.getData(getParams("getCity_Id").get("action")+"?",getParams("getCity_Id")).subscribeOn(Schedulers.io());
                    Observable<String> res_observable = s.getData(getParams("restaurnts").get("action")+"?",getParams("restaurnts")).subscribeOn(Schedulers.io());
                   /* for (int i = 0; i < observable.length; i++) {
                        if(i==0)
                        observable[0]
                                = s.getData(getParams("getCity_Id").get("action")+"?",getParams("getCity_Id")).subscribeOn(Schedulers.io());
                        if(i==1)
                        observable[1]
                                = s.getData(getParams("restaurnts").get("action")+"?",getParams("restaurnts")).subscribeOn(Schedulers.io());
                    }*/

                    return Observable.concatArray(city_observable,res_observable);
                })
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("on onSubscribe calling","##");
            }

            @Override
            public void onNext(String s) {

                Log.e("on onNext calling","##"+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("on onError calling","##"+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("on onComplete calling","##");
            }
        });

    }

}
