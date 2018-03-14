package com.quickpick.views.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.views.adapters.SlidingImage_Adapter;
import com.quickpick.views.ui.dashboard.DashBoardActivityNew;
import com.quickpick.views.ui.restarunt.RestaruntActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 14-03-2018.
 */

public class Reference extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.reviewpager)
    ViewPager myPager;
    SlidingImage_Adapter adapter;
    @Bind(R.id.txt_skip)
    TextView txt_skip;
    @Bind(R.id.txt_count_pages)
    TextView txt_count_pages;
    @Bind(R.id.txt_proceed)
    TextView txt_proceed;


    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference);
        ButterKnife.bind(this);
        adapter = new SlidingImage_Adapter(Reference.this, IMAGES);
        init();
        String  page_number = 1+"/"+IMAGES.length;
        txt_count_pages.setText(page_number);
        fetchListeners();
    }

    private void fetchListeners(){
        txt_skip.setOnClickListener(this);
        txt_proceed.setOnClickListener(this);
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
                /*tab_position = position;
                addBottomDots(position);*/
                String  page_number = (position+1)+"/"+IMAGES.length;
                txt_count_pages.setText(page_number);
                if((position+1)==IMAGES.length){
                    txt_proceed.setVisibility(View.VISIBLE);
                }else{
                    txt_proceed.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(Reference.this, DashBoardActivityNew.class));
        finish();
    }
}
