package com.quick_pick.View.ui.Appetizer;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
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
    String[] TITLES;
    public static CustomViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    ArrayList<TextView> tvList = new ArrayList<>();
    List<Fragment> fragments = new Vector<Fragment>();

    @Override
    protected int getLayoutResourceId() {
        return R.layout.apptizerdashboard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        txt_header.setText("Restaurant");
        img_filter.setVisibility(View.VISIBLE);
        img_search.setVisibility(View.VISIBLE);
//        tabLayout.setBackgroundColor(Color.parseColor("FFFFFF"));
                TITLES = getResources().getStringArray(R.array.apptizers);
        Appetizer_Fragment appetizer_fragment = new Appetizer_Fragment();
        for (int i = 0; i < TITLES.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("from", i);
            fragments.add(Fragment.instantiate(this, appetizer_fragment.getClass().getName(), bundle));
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
