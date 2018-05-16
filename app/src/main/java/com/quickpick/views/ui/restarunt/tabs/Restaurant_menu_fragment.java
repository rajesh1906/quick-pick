package com.quickpick.views.ui.restarunt.tabs;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.quickpick.R;
import com.quickpick.model.AllItems_Pojo;
import com.quickpick.model.ItemsData;
import com.quickpick.model.adds.AddsData;
import com.quickpick.model.customizedmenu.MainOuter;
import com.quickpick.model.menu.menunew.Menu;
import com.quickpick.model.menu.menunew.Menucategory;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Image_Fetch;
import com.quickpick.views.adapters.Fetching_items_completed;
import com.quickpick.views.adapters.Restaurant_menu_Adapter;
import com.quickpick.views.adapters.Restaurent_menu_tab;
import com.quickpick.views.adapters.Restaurent_menu_tab_new;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.DashboardTabs;
import com.quickpick.views.ui.dashboard.GetCategory_Id;
import com.quickpick.views.ui.dashboard.ShowViews;
import com.quickpick.views.ui.dashboard.tabs.Calling_Fragment;
import com.quickpick.views.ui.dashboard.tabs.EatsFragment;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class Restaurant_menu_fragment extends Fragment implements Calling_Fragment, Fetching_items_completed, GetCategory_Id,SwipeRefreshLayout.OnRefreshListener {
    View view;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.txt_no_res_pagger)
    TextView txt_no_res;
    @Bind(R.id.img_back)
    LinearLayout img_back;
    @Bind(R.id.img_header)
    ImageView img_header;


    String Res_id = "", name = "", time = "", img_url = "";
    ArrayList<String> menu_items = new ArrayList<>();
    ArrayList<String> menu_id = new ArrayList<>();
    String menu__item_id = "1", category_id = "";
    ArrayList<String> category_items_id = new ArrayList<>();
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    com.rey.material.widget.FloatingActionButton floatingActionButton;
    ArrayList<String> header_names;
    HashMap<String, List<String>> display_data;
    HashMap<String, ArrayList<HashMap<String, String>>> additional_data;
    LinearLayoutManager mLayoutManager;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.restaurent_menu_fragment, container, false);
        ButterKnife.bind(this, view);


        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);

        Res_id = getArguments().getString("menu_id");
        name = getArguments().getString("res_name");
        time = getArguments().getString("time");
        img_url = getArguments().getString("img_url");
        Image_Fetch.getInstance().LoadImage(getActivity(), img_header, img_url);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchData("AllItemsLoading", "show");
        dynamicToolbarColor();
        toolbarTextAppernce();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(name);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        ShowViews showViews = (ShowViews) DashboardTabs.instance;
        showViews.fabShowing(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowViews showViews = (ShowViews) DashboardTabs.instance;
        showViews.fabShowing(true);
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_four);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
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

                                Log.e("menu_bar data is ", "<><>" + menu_search.getItems().get(i).getItemName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.search_menu_item, R.id.txt_item, menu_items.toArray(new String[menu_items.size()]));
//                            list_menu.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();

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
                            AllItems_Pojo items_pojo = new Gson().fromJson(res, AllItems_Pojo.class);
                            display_data = new HashMap<>();
                            additional_data = new HashMap<>();

                            if (items_pojo.getStatus().equalsIgnoreCase("successfully")) {
                                if (items_pojo.getItemsData().size() != 0) {
                                    List<String> itemList = null;
                                    ArrayList<HashMap<String, String>> addidional = null;
                                    for (int i = 0; i < items_pojo.getItemsData().size(); i++) {
                                        ItemsData itemsData = items_pojo.getItemsData().get(i);
                                        if (!display_data.containsKey(itemsData.getSubMenuName())) {
                                            itemList = new ArrayList<String>();
                                            addidional = new ArrayList<>();
                                            HashMap<String, String> internal = new HashMap<>();
                                            itemList.add(itemsData.getItemName());
                                            display_data.put(itemsData.getSubMenuName(), itemList);

                                            internal.put("Item_Id", itemsData.getItem_Id());
                                            internal.put("NumberofQtys", itemsData.getNumberofQtys());
                                            internal.put("Amount", itemsData.getAmount());
                                            internal.put("Description", itemsData.getDescription());
                                            internal.put("ItemUrl", itemsData.getItemUrl());
                                            internal.put("RestaurantID", itemsData.getRestaurantID());
                                            addidional.add(internal);
                                            additional_data.put(itemsData.getSubMenuName(), addidional);
                                        } else {
                                            itemList = display_data.get(itemsData.getSubMenuName());
                                            itemList.add(itemsData.getItemName());
                                            HashMap<String, String> internal = new HashMap<>();
                                            addidional = additional_data.get(itemsData.getSubMenuName());
                                            internal.put("Item_Id", itemsData.getItem_Id());
                                            internal.put("NumberofQtys", itemsData.getNumberofQtys());
                                            internal.put("Amount", itemsData.getAmount());
                                            internal.put("Description", itemsData.getDescription());
                                            internal.put("ItemUrl", itemsData.getItemUrl());
                                            internal.put("RestaurantID", itemsData.getRestaurantID());
                                            addidional.add(internal);
                                            additional_data.put(itemsData.getSubMenuName(), addidional);
                                        }
                                    }


                                    JSONArray jsonArray = new JSONArray();
                                    Map<String,List<String>> final_list = new TreeMap<>(display_data);
                                    ArrayList<String > header_names = hashmapKeys(final_list);
                                    for(int i=0;i< hashmapKeys(display_data).size();i++){
                                        JSONObject mainObj = new JSONObject();
                                        mainObj.put("Header",header_names.get(i));
                                        JSONArray sub_json = new JSONArray();
                                        for(int j=0;j<final_list.get(header_names.get(i)).size();j++){
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("subItem_name",final_list.get(header_names.get(i)).get(j));
                                            jsonObject.put("amount",additional_data.get(header_names.get(i)).get(j).get("Amount"));
                                            jsonObject.put("description",additional_data.get(header_names.get(i)).get(j).get("Description"));
                                            jsonObject.put("Image",additional_data.get(header_names.get(i)).get(j).get("ItemUrl"));

                                            jsonObject.put("Item_Id",additional_data.get(header_names.get(i)).get(j).get("Item_Id"));
                                            jsonObject.put("NumberofQtys",additional_data.get(header_names.get(i)).get(j).get("NumberofQtys"));
                                            jsonObject.put("RestaurantID",additional_data.get(header_names.get(i)).get(j).get("RestaurantID"));

                                            sub_json.put(jsonObject);
                                        }
                                        mainObj.put("subItems",sub_json);
                                        jsonArray.put(mainObj);

                                    }
                                    Log.e("Json array is ","<><><>"+jsonArray.toString());
                                    MainOuter data = new Gson().fromJson("{\"items\":"+jsonArray.toString()+"}",MainOuter.class);
                                    Log.e("pojo valus is ","<><<>"+data.getItems().get(0).getSubItems().get(0).getSubItem_name());


                                    try {
                                        RetrofitClient.getInstance().showProgressDialog(getActivity());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    recyclerview.setAdapter(new Restaurent_menu_tab(getActivity(), display_data, additional_data, name, time, Restaurant_menu_fragment.this));
                                    recyclerview.setAdapter(new Restaurent_menu_tab_new(getActivity(),data, Restaurant_menu_fragment.this));
                                    recyclerview.setVisibility(View.VISIBLE);
                                } else {
                                    txt_no_res.setVisibility(View.VISIBLE);
                                    txt_no_res.setText("No Menu Items found");

                                    recyclerview.setVisibility(View.GONE);
                                }
                            }
                            swipeRefreshLayout.setRefreshing(false);

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
//                params.put("Text", edt_txt_search.getText().toString());
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

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void calling(FloatingActionButton floatingActionButton, String coming_from) {

    }

    @Override
    public ArrayList<AddsData> getAddsData() {
        return null;
    }

    @Override
    public void completed() {
        RetrofitClient.getInstance().hideProgressDialog();
    }

    public void showMenu(com.rey.material.widget.FloatingActionButton floatingActionButton) {
        this.floatingActionButton = floatingActionButton;
        Map<String, List<String>> final_list = new TreeMap<>(display_data);
        header_names = hashmapKeys(final_list);
        CustomDialog.getInstance().showTooltip(getActivity(), header_names, floatingActionButton, Restaurant_menu_fragment.this);

    }

    @Override
    public void getId(int id) {


       /* if (id == 1) {
//            recyclerview.scrollToPosition(0);
            mLayoutManager.scrollToPosition(0);
        } else{
//            recyclerview.scrollToPosition(additional_data.get(header_names.get((id - 1))).size() + 1);
//            mLayoutManager.scrollToPosition(additional_data.get(header_names.get((id - 1))).size() + 1);

            for(int i=0;i<id;i++){
                if(i!=0)
                    lenght = lenght+additional_data.get(header_names.get((i))).size();
            }

        }*/
        mLayoutManager.scrollToPositionWithOffset(id, 0);
        ;
//        Toast.makeText(getActivity(), "Show Restaurent Fragment"+lenght, Toast.LENGTH_SHORT).show();


    }

    private ArrayList<String> hashmapKeys(Map<String, List<String>> display_data) {
        ArrayList<String> keynames = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : display_data.entrySet()) {
            String key = entry.getKey();
//             = entry.getValue();
            Log.e("keys is ", "<><" + key);
            keynames.add(key);
            // do something with key and/or tab
        }

        return keynames;
    }

    @Override
    public void onRefresh() {
        fetchData("AllItemsLoading", "show");
    }



}
