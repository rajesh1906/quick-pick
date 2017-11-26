package com.quick_pick.View.ui.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quick_pick.R;
import com.quick_pick.View.adapters.PagerAdapter;
import com.quick_pick.View.adapters.SlidingImage_Adapter;
import com.quick_pick.View.customviews.CustomViewPager;
import com.quick_pick.View.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;
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
    String[] TITLES ;
    public static CustomViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<TextView> tvList = new ArrayList<>();
    List<Fragment> fragments = new Vector<Fragment>();
    TextView[] dots;
    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four};

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_dash_board;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        TITLES= getResources().getStringArray(R.array.food_types);
        adapter = new SlidingImage_Adapter(DashBoardActivity.this, IMAGES);
        init();
        Restaurant_fragment restaurant_fragment = new Restaurant_fragment();
        addBottomDots(0);
        for(int i=0;i<TITLES.length;i++){
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
}
