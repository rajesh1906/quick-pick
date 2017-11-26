package com.quick_pick.View.ui.Restarunt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.quick_pick.R;
import com.quick_pick.View.adapters.Restaurant_menu_item_Adapter;
import com.quick_pick.View.adapters.ShowRestaurant_Adapter;
import com.quick_pick.View.adapters.SlidingImage_Adapter;
import com.quick_pick.View.ui.BaseActivity;
import com.quick_pick.View.ui.dashboard.DashBoardActivity;

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
    SlidingImage_Adapter adapter;
    int tab_position = 0;
    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four};

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_restarunt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        txt_header.setText("Restaurant");
        addBottomDots(0);
        adapter = new SlidingImage_Adapter(RestaruntActivity.this, IMAGES);
        init();
        GridLayoutManager grid_layout = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(grid_layout);
        recyclerview.setAdapter(new Restaurant_menu_item_Adapter(this));
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
}
