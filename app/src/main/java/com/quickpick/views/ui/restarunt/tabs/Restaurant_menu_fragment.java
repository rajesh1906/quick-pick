package com.quickpick.views.ui.restarunt.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
import com.quickpick.model.AllItems_Pojo;
import com.quickpick.model.ItemsData;
import com.quickpick.model.Temp;
import com.quickpick.model.menu.menunew.Menu;
import com.quickpick.model.menu.menunew.Menucategory;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.views.adapters.Restaurant_menu_Adapter;
import com.quickpick.views.adapters.Restaurent_menu_tab;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.restarunt.RestaruntActivityNew;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 20-11-2017.
 */

public class Restaurant_menu_fragment extends Fragment implements View.OnClickListener {
    View view;
    @Bind(R.id.img_filter)
    ImageView img_filter;
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
    ListView list_menu;
    @Bind(R.id.menu_category)
    FloatingActionButton menu_category;
    @Bind(R.id.img_cancel)
    ImageView img_cancel;
    @Bind(R.id.txt_header)
    TextView txt_header;
    @Bind(R.id.ll_icon)
    LinearLayout ll_icon;
    @Bind(R.id.fab_filter)
    FloatingActionButton fab_filter;
    @Bind(R.id.fab_menu)
    FloatingActionButton fab_menu;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    String Res_id = "", name = "";
    ArrayList<String> menu_items = new ArrayList<>();
    ArrayList<String> menu_id = new ArrayList<>();
    String menu__item_id = "1", category_id = "";
    ArrayList<String> category_items_id = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activitydashboard_new, container, false);
        ButterKnife.bind(this, view);

        Res_id = getArguments().getString("menu_id");
        name = getArguments().getString("res_name");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        fetchData("AllItemsLoading", "show");

        img_filter.setVisibility(View.VISIBLE);
        img_filter.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_filter:
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }

    private void fetchData(final String coming_from, String progress_bar) {
        RetrofitClient.getInstance().getEndPoint(getActivity(), progress_bar).getResult(getparams(coming_from), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ", "<><><>" + res);
                switch (coming_from) {
                    case "default_res":
                        Menu menu = new Gson().fromJson(res, Menu.class);
                        if (menu.getItems().size() != 0) {
                            Restaurant_menu_Adapter adapter = new Restaurant_menu_Adapter(getActivity(), menu.getItems());
                            recyclerview.setAdapter(adapter);
                            txt_no_res.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);
                        } else {
                            txt_no_res.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        }
                        break;
                    case "search_items":
                        if (menu_items.size() != 0) {
                            menu_items.clear();
                            menu_id.clear();
                        }
                        Menu menu_search = new Gson().fromJson(res, Menu.class);
                        if (menu_search.getItems().size() != 0) {
                            for (int i = 0; i < menu_search.getItems().size(); i++) {
                                menu_items.add(menu_search.getItems().get(i).getItemName());
                                menu_id.add(menu_search.getItems().get(i).getMenuId());

                                Log.e("menu data is ", "<><>" + menu_search.getItems().get(i).getItemName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.search_menu_item, R.id.txt_item, menu_items.toArray(new String[menu_items.size()]));
                            list_menu.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), "No Search Found", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case "search_based_menu":
                        Menu menu_search_based = new Gson().fromJson(res, Menu.class);
                        if (menu_search_based.getItems().size() != 0) {
                            Restaurant_menu_Adapter adapter = new Restaurant_menu_Adapter(getActivity(), menu_search_based.getItems());
                            recyclerview.setAdapter(adapter);
                            txt_no_res.setVisibility(View.GONE);
                        } else {
                            txt_no_res.setVisibility(View.VISIBLE);
                            recyclerview.setVisibility(View.GONE);
                        }
                        break;

                    case "category":
                        Log.e("getting category is ", "<><>" + res);
                        Menucategory category = new Gson().fromJson(res, Menucategory.class);
                        ArrayList<String> category_items = new ArrayList<>();
                        if (category.getStatus().equalsIgnoreCase("successfully")) {
                            for (int i = 0; i < category.getMenuData().size(); i++) {
                                Log.e("category names is ", "<><>" + category.getMenuData().get(i).getItemName());
                                category_items.add(category.getMenuData().get(i).getItemName());
                                category_items_id.add(category.getMenuData().get(i).getMenuId());

                            }
//                            CustomDialog.getInstance().showTooltip(getActivity(), category_items, fab_menu,Restaurant_menu_fragment.this);


                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "AllItemsLoading":

                        try {
                            AllItems_Pojo items_pojo = new Gson().fromJson(Temp.data, AllItems_Pojo.class);
                            HashMap<String, List<String>> display_data = new HashMap<>();
                            HashMap<String, ArrayList<HashMap<String ,String >>> additional_data = new HashMap<>();

                            if (items_pojo.getStatus().equalsIgnoreCase("successfully")) {
                                List<String> itemList = null;
                                ArrayList<HashMap<String,String >> addidional=null;
                                for (int i = 0; i < items_pojo.getItemsData().size(); i++) {
                                    ItemsData itemsData = items_pojo.getItemsData().get(i);
                                    if (!display_data.containsKey(itemsData.getSubMenuName())) {
                                        itemList = new ArrayList<String>();
                                        addidional = new ArrayList<>();
                                        HashMap<String ,String> internal = new HashMap<>();
                                        itemList.add(itemsData.getItemName());
                                        display_data.put(itemsData.getSubMenuName(), itemList);

                                        internal.put("Item_Id",itemsData.getItem_Id());
                                        internal.put("NumberofQtys",itemsData.getNumberofQtys());
                                        internal.put("Amount",itemsData.getAmount());
                                        internal.put("ItemUrl",itemsData.getItemUrl());
                                        addidional.add(internal);
                                        additional_data.put(itemsData.getSubMenuName(),addidional);
                                    } else {
                                        itemList = display_data.get(itemsData.getSubMenuName());
                                        itemList.add(itemsData.getItemName());
                                        HashMap<String ,String> internal = new HashMap<>();
                                        addidional = additional_data.get(itemsData.getSubMenuName());
                                        internal.put("Item_Id",itemsData.getItem_Id());
                                        internal.put("NumberofQtys",itemsData.getNumberofQtys());
                                        internal.put("Amount",itemsData.getAmount());
                                        internal.put("ItemUrl",itemsData.getItemUrl());
                                        addidional.add(internal);
                                        additional_data.put(itemsData.getSubMenuName(),addidional);
                                    }
                                }
                            }
//                            Log.e("final data is ", "<><><<>" + display_data);
//                            Log.e("additional data is ", "<><><<>" + additional_data);
                            recyclerview.setAdapter(new Restaurent_menu_tab(getActivity(), display_data,additional_data));
                            recyclerview.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
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


    private Map<String, String> getparams(String coming_from) {
        Map<String, String> params = new HashMap<>();
        switch (coming_from) {
            case "default_res":
                params.put("action", APIS.SubMenusLoading);
                params.put("RestaurantID", Res_id);
                params.put("FlagSlNo", "0");
                break;
            case "search_items":
                params.put("action", APIS.displayItemsSearchData);
                params.put("Text", edt_txt_search.getText().toString());
                params.put("RestaurantID", Res_id);
                params.put("FlagSlNo", "0");
                break;
            case "search_based_menu":
                params.put("action", APIS.displayItemsDataMenuBased);
                params.put("MenuId", "" + menu__item_id);
                params.put("RestaurantID", Res_id);
                params.put("FlagSlNo", "0");

                break;
            case "category":
                params.put("action", APIS.MenuLoading);
                params.put("RestaurantID", Res_id);
                params.put("FlagSlNo", "0");
                break;
            case "AllItemsLoading":
                params.put("action", APIS.AllItemsLoading);
                params.put("RestaurantID", Res_id);
                break;
        }


        return params;
    }
}
