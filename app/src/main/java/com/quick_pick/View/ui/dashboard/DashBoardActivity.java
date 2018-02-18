package com.quick_pick.View.ui.dashboard;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quick_pick.Model.Category;
import com.quick_pick.Model.Cities;
import com.quick_pick.Presenter.services.Network.APIResponse;
import com.quick_pick.Presenter.services.Network.APIS;
import com.quick_pick.Presenter.services.Network.RetrofitClient;
import com.quick_pick.Presenter.utils.Common_methods;
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

public class DashBoardActivity extends BaseActivity implements GetCity_id {
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
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.edtSearch)
    AutoCompleteTextView edtSearch;
    @Bind(R.id.img_delete)
    ImageView img_delete;
    ArrayList<String> city_items = new ArrayList<>();
    ArrayList<String> al_city_id = new ArrayList<>();
    String city_id="1";
    public static Activity instance;
    ArrayList<String> category_items;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_dash_board;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        adapter = new SlidingImage_Adapter(DashBoardActivity.this, IMAGES);
        init();
        instance = this;

        addBottomDots(0);


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

        fetchData("category", "show_progress");

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city_id = al_city_id.get(position);
               hideKeyboard();
                setCategories(category_items.toArray(new String[category_items.size()]));
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


    private void fetchLisiner() {
        edtSearch.addTextChangedListener(new CustomWatcher(edtSearch));

    }

    @Override
    public String getCity_id() {
        return city_id;
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

                        fetchData("cities", "");
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

    private void setCountForTab(int tabPos, TabLayout tabLayout, String[] TITLES) {

        View tabview = getLayoutInflater().inflate(R.layout.item_parent, null);
        TextView txt_tilte = (TextView) tabview.findViewById(R.id.text1);
        txt_tilte.setText(TITLES[tabPos]);
        if (tabPos == 0) {
            txt_tilte.setTypeface(Typeface.DEFAULT_BOLD);
        }
        tvList.add(txt_tilte);
        tabLayout.getTabAt(tabPos).setCustomView(tabview);
    }


    private void fetchData(final String coming_from, String progress_bar_status) {
        RetrofitClient.getInstance().getEndPoint(this, progress_bar_status).getResult(getParams(coming_from), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("resultent cities is ", "<><>" + res);

                switch (coming_from) {
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DashBoardActivity.this, R.layout.adapter_item, R.id.txt_item, city_items.toArray(new String[city_items.size()]));
                            edtSearch.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if( cities.getRestaurantData().size()==0){
                                Toast.makeText(DashBoardActivity.this, "No Search Found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DashBoardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "category":
                        Category category = new Gson().fromJson(res, Category.class);
                         category_items = new ArrayList<>();
                        if (category.getStatus().equalsIgnoreCase("successfully")) {
                            for (int i = 0; i < category.getCateogryData().size(); i++) {
                                Log.e("category names is ", "<><>" + category.getCateogryData().get(i).getCategory_Name());
                                category_items.add(category.getCateogryData().get(i).getCategory_Name());

                            }
                            setCategories(category_items.toArray(new String[category_items.size()]));

                        } else {
                            Toast.makeText(DashBoardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            case "cities":
                params.put("Text", edtSearch.getText().toString());
                params.put("FlagSlNo", "0");
//                params.put("action", getResources().getString(R.string.getCities));
                params.put("action", APIS.CITIES);

                break;
            case "category":
//                params.put("action", getResources().getString(R.string.getCategory));
                params.put("action", APIS.Category);

                break;
        }

        return params;
    }


    private void setCategories(final String[] TITLES) {

        Restaurant_fragment restaurant_fragment = new Restaurant_fragment();
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
            setCountForTab(i, tabLayout, TITLES);
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
    }


}
