package com.quickpick.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.restaurant_category.RestaurantData;
import com.quickpick.views.ui.restarunt.RestaruntActivity;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 05-09-2017.
 */

public class ShowRestaurant_Adapter extends RecyclerView.Adapter<ShowRestaurant_Adapter.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    ArrayList<RestaurantData> dataList;
    public ShowRestaurant_Adapter(Context context,ArrayList<RestaurantData> dataList){
        this.context=context;
        this.dataList = dataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       TextView textView;
       RelativeLayout rel_parent;
        public MyViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.txt_item);
            rel_parent = (RelativeLayout)view.findViewById(R.id.rel_parent);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_restarunt_item, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        holder.textView.setText( dataList.get(position).getRes_Name());
        holder.rel_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaruntActivity.class);
                intent.putExtra("menu_id",dataList.get(position).getRes_id());
                intent.putExtra("res_name",dataList.get(position).getRes_Name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}