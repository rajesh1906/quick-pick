package com.quickpick.views.ui.appetizer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quickpick.R;
import com.quickpick.model.appetizer.Appetizer;
import com.quickpick.model.appetizer.Items;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.views.adapters.PagerAdapter;
import com.quickpick.views.customviews.CustomViewPager;
import com.quickpick.views.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 26-11-2017.
 */

public class Appetizer_Dashboard extends BaseActivity {
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.txt_header)
    TextView txt_header;
    @Bind(R.id.img_filter)
    ImageView img_filter;
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.txt_apptizer_error)
    TextView txt_apptizer_error;


    String[] TITLES;
    public static CustomViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<TextView> tvList = new ArrayList<>();
    List<Fragment> fragments = new Vector<Fragment>();
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    String menu_id = "";
    ArrayList<Items> dataList = new ArrayList<>();
    Appetizer appetizer, temp_pojo;
    int spage = 1, no_records = 0;
    private boolean loading;
    private boolean scrollflag = true;

    Appetizer_Adapter adapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.apptizerdashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        drawerToggle.setDrawerIndicatorEnabled(false);
        Bundle bundle1 = getIntent().getExtras();
        menu_id = bundle1.getString("menu_id");
        txt_header.setText(bundle1.getString("res_name"));
        img_filter.setVisibility(View.VISIBLE);
        img_search.setVisibility(View.VISIBLE);
//        tabLayout.setBackgroundColor(Color.parseColor("FFFFFF"));
        TITLES = getResources().getStringArray(R.array.apptizers);
        Appetizer_Fragment appetizer_fragment = new Appetizer_Fragment();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAppetizerData();

    }

    private void setCountForTab(int tabPos, TabLayout tabLayout) {

        View tabview = getLayoutInflater().inflate(R.layout.item_parent, null);
        TextView txt_tilte = (TextView) tabview.findViewById(R.id.text1);
        txt_tilte.setText(TITLES[tabPos]);
        if (tabPos == 0) {
            txt_tilte.setTypeface(Typeface.DEFAULT_BOLD);
        }
        tvList.add(txt_tilte);
        tabLayout.getTabAt(tabPos).setCustomView(tabview);
    }


    private void getAppetizerData() {
        RetrofitClient.getInstance().getEndPoint(this, "show_progress").getResult(getParams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ", "<><>" + res);
                appetizer = new Gson().fromJson(res, Appetizer.class);
                if (appetizer.getStatus().equalsIgnoreCase("successfully")) {
                    if (spage == 1) {
                        temp_pojo = appetizer;
                    } else {
                        dataList = (ArrayList<Items>) temp_pojo.getItems();
                        dataList.addAll(appetizer.getItems());
                        temp_pojo.setItems(dataList);
                        loading = false;
                    }
                    if (spage == 1) {
                        adapter = new Appetizer_Adapter(Appetizer_Dashboard.this, temp_pojo.getItems());
                        recyclerview.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                    if (spage == 1 && temp_pojo.getItems().size() == 0) {
                        txt_apptizer_error.setVisibility(View.VISIBLE);
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

//        params.put("action",getResources().getString(R.string.getDisplayItems));
        params.put("action", APIS.DisplayItems);

        params.put("MunuId", menu_id);
        params.put("Text", "");
        params.put("FlagSlNo", "0");

        return params;
    }
}
