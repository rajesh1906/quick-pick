package com.quickpick.views.ui.restarunt;

import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quickpick.R;
import com.quickpick.model.menu.menunew.Menu;
import com.quickpick.model.menu.menunew.Menucategory;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.views.adapters.Restaurant_menu_Adapter;
import com.quickpick.views.ui.BaseActivity;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.DashBoardActivityNew;
import com.quickpick.views.ui.dashboard.GetCategory_Id;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 12-03-2018.
 */

public class RestaruntActivityNew extends BaseActivity implements View.OnClickListener, GetCategory_Id {
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
    String Res_id = "",name="";
    ArrayList<String> menu_items = new ArrayList<>();
    ArrayList<String > menu_id = new ArrayList<>();
    String  menu__item_id="1", category_id = "";
    ArrayList<String > category_items_id = new ArrayList<>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activitydashboard_new;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        txt_header.setText("Search by Item");
        txt_header.setTextColor(getResources().getColor(R.color.black));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("res_name");
        img_search.setVisibility(View.VISIBLE);
        Res_id = bundle.getString("menu_id");
        fetchData("default_res","");
        fetchLisiner();


        list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("menu name is ", "<>>" + menu_items.get(i));
                Log.e("menu id is ", "<>>" + menu_id.get(i));
                menu__item_id = menu_id.get(i);
                txt_header.setText(menu_items.get(i));
                new Common_methods(RestaruntActivityNew.this).hideKeyboard(edt_txt_search);
                container_serach.setVisibility(View.GONE);
                fetchData("search_based_menu", "show_progress");
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

    @Override
    public void getId(int id) {
        menu__item_id = ""+category_items_id .get(id);
        fetchData("search_based_menu", "show_progress");
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

                        fetchData("search_items", "");
                    }
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }



    private void fetchData(final String coming_from, String progress_bar){
        RetrofitClient.getInstance().getEndPoint(this,progress_bar).getResult(getparams(coming_from), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ","<><><>"+res);
                switch (coming_from){
                    case "default_res":
                        Menu menu = new Gson().fromJson(res,Menu.class);
                        if(menu.getItems().size()!=0){
                            Restaurant_menu_Adapter adapter = new Restaurant_menu_Adapter(RestaruntActivityNew.this,menu.getItems());
                            recyclerview.setAdapter(adapter);
                        }
                        break;
                    case "search_items":
                        if (menu_items.size() != 0) {
                            menu_items.clear();
                            menu_id.clear();
                        }
                        Menu menu_search = new Gson().fromJson(res,Menu.class);
                        if(menu_search.getItems().size()!=0){
                            for (int i = 0; i < menu_search.getItems().size(); i++) {
                                menu_items.add(menu_search.getItems().get(i).getItemName());
                               menu_id.add(menu_search.getItems().get(i).getMenuId());

                                Log.e("menu data is ", "<><>" + menu_search.getItems().get(i).getItemName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RestaruntActivityNew.this, R.layout.search_menu_item, R.id.txt_item, menu_items.toArray(new String[menu_items.size()]));
                            list_menu.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }else{
                            Toast.makeText(RestaruntActivityNew.this, "No Search Found", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case "search_based_menu":
                        Menu menu_search_based = new Gson().fromJson(res,Menu.class);
                        if(menu_search_based.getItems().size()!=0){
                            Restaurant_menu_Adapter adapter = new Restaurant_menu_Adapter(RestaruntActivityNew.this,menu_search_based.getItems());
                            recyclerview.setAdapter(adapter);
                            txt_no_res.setVisibility(View.GONE);
                        }else{
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
                            CustomDialog.getInstance().showTooltip(RestaruntActivityNew.this, category_items,menu_category);


                        } else {
                            Toast.makeText(RestaruntActivityNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }

            @Override
            public void onFailure(String res) {

            }
        });


    }



    private Map<String ,String > getparams(String coming_from){
        Map<String,String > params = new HashMap<>();
        switch (coming_from){
            case "default_res":
                params.put("action", APIS.DisplayItems);
                break;
            case "search_items":
                params.put("action",APIS.displayItemsSearchData);
                params.put("Text",edt_txt_search.getText().toString());
                break;
            case "search_based_menu":
                params.put("action",APIS.displayItemsDataMenuBased);
                params.put("MenuId",""+menu__item_id);

                break;
            case "category":
                params.put("action",APIS.MenuLoading);
                break;
        }
        params.put("RestaurantID", Res_id);
        params.put("FlagSlNo","0");




        return params;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_search:
                edt_txt_search.setText("");
                container_serach.setVisibility(View.VISIBLE);
                edt_txt_search.requestFocus();
                new Common_methods(RestaruntActivityNew.this).openKeyboard(edt_txt_search);
                break;
            case R.id.img_back:
                new Common_methods(RestaruntActivityNew.this).hideKeyboard(edt_txt_search);
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (container_serach.isShown()) {
            container_serach.setVisibility(View.GONE);
        } else if (!menu__item_id.equals("1")||!txt_header.getText().toString().equalsIgnoreCase("Search by Item")) {
            menu__item_id = "1";
            txt_header.setText("Search by Item");
            fetchData("default_res", "show_progress");
        } else {
            finish();
        }
    }
}
