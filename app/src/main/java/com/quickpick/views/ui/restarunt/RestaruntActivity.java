package com.quickpick.views.ui.restarunt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quickpick.R;
import com.quickpick.model.menu.Menu;
import com.quickpick.model.menu.Menu_Items;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.views.adapters.Restaurant_menu_item_Adapter;
import com.quickpick.views.adapters.SlidingImage_Adapter;
import com.quickpick.views.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 18-11-2017.
 */

public class RestaruntActivity extends BaseActivity {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.reviewpager)
    ViewPager myPager;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;
    @Bind(R.id.txt_header)
    TextView txt_header;
    @Bind(R.id.txt_no_items)
    TextView txt_no_items;


    SlidingImage_Adapter adapter;
    int tab_position = 0;
    String Res_id = "";
    ArrayList<Menu> dataList = new ArrayList<>();
    Menu_Items menu_items, temp_pojo;
    int spage = 1, no_records = 0;
    String name="";
    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four};

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_restarunt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.setDrawerIndicatorEnabled(false);

        Bundle bundle = getIntent().getExtras();
         name = bundle.getString("res_name");
        txt_header.setText(name);
        Res_id = bundle.getString("menu_id");

        addBottomDots(0);
        adapter = new SlidingImage_Adapter(RestaruntActivity.this, IMAGES);
        init();
        GridLayoutManager grid_layout = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(grid_layout);

        getMenuItmes();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {

        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
        myPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab_position = position;
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[IMAGES.length];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FF0000"));
    }

    private void getMenuItmes() {
        RetrofitClient.getInstance().getEndPoint(this, "show_progress").getResult(getParams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response ", "<><" + res);
                menu_items = new Gson().fromJson(res, Menu_Items.class);
                if (menu_items.getStatus().equalsIgnoreCase("successfully")) {
                    if (spage == 1) {
                        temp_pojo = menu_items;
                    } else {
                        dataList = (ArrayList<Menu>) temp_pojo.getMenu();
                        dataList.addAll(menu_items.getMenu());
                        temp_pojo.setMenu(dataList);
                    }
                    recyclerview.setAdapter(new Restaurant_menu_item_Adapter(RestaruntActivity.this, menu_items.getMenu(),name));
                    if(menu_items.getMenu().size()==0)
                    {
                        txt_no_items.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(String res) {

            }
        });

    }


    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("action", APIS.DisplayMenusData);

//        params.put("action", getResources().getString(R.string.displayMenusData));
        params.put("gettingRestaurantId", Res_id);
        params.put("Text", "");
        params.put("FlagSlNo", "0");


        return params;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home://this will handle back navigation click
               onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
