package com.quickpick.views.ui.dashboard.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.quickpick.model.restaurant_category.RestaurantData;
import com.quickpick.model.restaurant_category.Restaurant_names;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.views.adapters.ShowRestaurant_Adapter;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.DashBoardActivityNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 02-04-2018.
 */
public class EatsFragment extends Fragment {

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

    ArrayList<RestaurantData> dataList = new ArrayList<>();
    Restaurant_names restaurant_name, temp_pojo;
    int spage = 1, no_records = 0;
    private boolean loading;
    private boolean scrollflag = true;
    ShowRestaurant_Adapter adapter;
    ArrayList<String> city_items = new ArrayList<>();
    ArrayList<String> al_city_id = new ArrayList<>();
    String city_id = "8", category_id = "";
    String lat="16.989065",lng="82.247465";

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
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        fetchData("restaurnts", "show_progress");
        return view;
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
                                adapter = new ShowRestaurant_Adapter(getActivity(), temp_pojo.getRestaurantData());
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
//                            CustomDialog.getInstance().showTooltip(getActivity(), category_items,menu_category);


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
                                adapter = new ShowRestaurant_Adapter(getActivity(), temp_pojo.getRestaurantData());
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
}
