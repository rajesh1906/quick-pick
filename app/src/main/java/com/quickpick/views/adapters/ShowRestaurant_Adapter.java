package com.quickpick.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickpick.R;
import com.quickpick.model.restaurant_category.RestaurantData;
import com.quickpick.views.ui.dashboard.DashBoardActivity;
import com.quickpick.views.ui.dashboard.tabs.Calling_Fragment;
import com.quickpick.views.ui.dashboard.tabs.EatsFragment;
import com.quickpick.views.ui.restarunt.RestaruntActivity;
import com.quickpick.views.ui.restarunt.RestaruntActivityNew;
import com.quickpick.views.ui.restarunt.tabs.Restaurant_menu_fragment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rajesh Kumar on 05-09-2017.
 */

public class ShowRestaurant_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    ArrayList<RestaurantData> dataList;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final Integer[] IMAGES = {R.drawable.img_one, R.drawable.img_two, R.drawable.img_three, R.drawable.img_four};
    SlidingImage_Adapter adapter;
    EatsFragment fragment;
    public ShowRestaurant_Adapter(Context context,ArrayList<RestaurantData> dataList,EatsFragment fragment){
        this.context=context;
        this.dataList = dataList;
        this.fragment = fragment;
        Calling_Fragment calling_fragment = (Calling_Fragment)fragment;
        adapter = new SlidingImage_Adapter(context, IMAGES,calling_fragment.getAddsData());
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_dots;
        ViewPager myPager;
        public HeaderViewHolder(View view) {
            super(view);
            ll_dots = (LinearLayout)view.findViewById(R.id.ll_dots);
            myPager = (ViewPager)view.findViewById(R.id.reviewpager);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       TextView textView;
       RelativeLayout rel_parent;
       ImageView img_res;
        public MyViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.txt_item);
            rel_parent = (RelativeLayout)view.findViewById(R.id.rel_parent);
            img_res = view.findViewById(R.id.img_res);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_restarunt_item, parent, false);
            return new MyViewHolder(root);
        }else if(viewType==TYPE_HEADER){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_layout, parent, false);
            return new HeaderViewHolder(root);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

        Log.e("random number is","<<><>"+getRandomNumber());
        if (holder instanceof MyViewHolder) {
            MyViewHolder holder1 = (MyViewHolder)holder;
            holder1.textView.setText(dataList.get(position-1).getRes_Name());
            holder1.img_res.setBackgroundResource(getIMage(getRandomNumber()));
            holder1.rel_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                Intent intent = new Intent(context, RestaruntActivityNew.class);
                intent.putExtra("menu_id",dataList.get(position).getRes_id());
                intent.putExtra("res_name",dataList.get(position).getRes_Name());
                context.startActivity(intent);*/


                Restaurant_menu_fragment restaurant_menu_fragment = new Restaurant_menu_fragment();
                Bundle bundle = new Bundle();
                bundle.putString("menu_id",dataList.get(position-1).getRes_id());
                bundle.putString("res_name",dataList.get(position-1).getRes_Name());
                android.app.FragmentManager fm = ((Activity)context).getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                restaurant_menu_fragment.setArguments(bundle);
                fragmentTransaction.add(R.id.container, restaurant_menu_fragment);
                fragmentTransaction.addToBackStack("bacstack");
                fragmentTransaction.commit();


//                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();


                }
            });
        }else{
            HeaderViewHolder holder2 = (HeaderViewHolder)holder;
            init( holder2.myPager,context, holder2.ll_dots);

        }
    }
    @Override
    public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        return TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private int getRandomNumber(){
        Random r = new Random();
        int Low = 0;
        int High = 4;
        int Result = r.nextInt(High-Low) + Low;

        return Result;
    }


    private int getIMage(int number){
        int item =0;
        switch (number){
            case 0:
                item = R.drawable.img_one;
                break;
            case 1:
                item = R.drawable.img_two;
                break;
            case 2:
                item = R.drawable.img_three;
                break;
            case 3:
                item = R.drawable.img_four;
                break;
        }

        return item;
    }

    private void addBottomDots(int currentPage,Context context,LinearLayout ll_dots) {
        TextView[] dots = new TextView[dataList.size()];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FF0000"));
    }

    private void init(ViewPager myPager,final Context context,final LinearLayout linearLayout) {

        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
        myPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position,context,linearLayout);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}