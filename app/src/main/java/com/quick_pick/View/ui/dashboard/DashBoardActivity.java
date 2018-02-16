package com.quick_pick.View.ui.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quick_pick.Model.Cities;
import com.quick_pick.Presenter.services.Network.APIResponse;
import com.quick_pick.Presenter.services.Network.RetrofitClient;
import com.quick_pick.R;
import com.quick_pick.View.adapters.PagerAdapter;
import com.quick_pick.View.adapters.SlidingImage_Adapter;
import com.quick_pick.View.customviews.CustomViewPager;
import com.quick_pick.View.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Rajesh kumar on 18-11-2017.
 */

public class DashBoardActivity extends BaseActivity {
    @Bind(R.id.reviewpager)
    ViewPager myPager;
    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;
    SlidingImage_Adapter adapter;
    int tab_position = 0;
    String[] TITLES;
    public static CustomViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<TextView> tvList = new ArrayList<>();
    List<Fragment> fragments = new Vector<Fragment>();
    TextView[] dots;
    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four};
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.edtSearch)
    AutoCompleteTextView edtSearch;
    @Bind(R.id.img_delete)
    ImageView img_delete;
    ArrayList<String > city_items = new ArrayList<>();


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_dash_board;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        TITLES = getResources().getStringArray(R.array.food_types);
        adapter = new SlidingImage_Adapter(DashBoardActivity.this, IMAGES);
        init();
//        setSupportActionBar(mToolbar);

        Restaurant_fragment restaurant_fragment = new Restaurant_fragment();
        addBottomDots(0);
        for (int i = 0; i < TITLES.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("from", i);
            fragments.add(Fragment.instantiate(this, restaurant_fragment.getClass().getName(), bundle));

        }
        this.mPagerAdapter = new PagerAdapter(
                super.getSupportFragmentManager(), fragments);
        this.mViewPager = (CustomViewPager) super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        tabLayout.setupWithViewPager(this.mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        this.mViewPager.setPagingEnabled(true);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        for (int i = 0; i < TITLES.length; i++) {
            setCountForTab(i, tabLayout);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setPagingEnabled(true);
                mViewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
                for (int i = 0; i < TITLES.length; i++) {
                    if (i == tabLayout.getSelectedTabPosition()) {
                        tvList.get(i).setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        tvList.get(i).setTypeface(Typeface.DEFAULT);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setVisibility(View.VISIBLE);
                edtSearch.requestFocus();
                img_search.setVisibility(View.GONE);
                img_delete.setVisibility(View.VISIBLE);
            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setVisibility(View.GONE);
                img_search.setVisibility(View.VISIBLE);
                img_delete.setVisibility(View.GONE);
            }
        });
    }

    private void init() {
        img_search.setVisibility(View.VISIBLE);
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



        fetchLisiner();

    }


    private void fetchLisiner(){
        edtSearch.addTextChangedListener(new CustomWatcher(edtSearch));

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

                case R.id.edtSearch:
                    if (edtSearch.getText().length() >= 1) {

                        getCities();
                    }
                    break;

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
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


    private void getCities(){
        RetrofitClient.getInstance().getEndPoint(this).getResult(getParams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("resultent cities is ","<><>"+res);
                if(city_items.size()!=0)
                    city_items.clear();
                Cities cities = new Gson().fromJson(res,Cities.class);
                if(cities.getStatus().equalsIgnoreCase("successfully")){
                    for(int i =0;i<cities.getRestaurantData().size();i++){
                        city_items.add(cities.getRestaurantData().get(i).getCityName());
                        Log.e("city data is ","<><>"+cities.getRestaurantData().get(i).getCityName());
                    }
                    ArrayAdapter<String > adapter = new ArrayAdapter<String>(DashBoardActivity.this,R.layout.adapter_item,R.id.txt_item,city_items.toArray(new String[city_items.size()]));
                    edtSearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String res) {

            }
        });
    }
    private Map<String ,String > getParams(){
        Map<String ,String > params = new HashMap<>();
        params.put("Text",edtSearch.getText().toString());
        params.put("FlagSlNo","0");
        params.put("action",getResources().getString(R.string.getCities));
        return params;



    }




}
