package com.quick_pick.View.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quick_pick.R;
import com.quick_pick.View.ui.Restarunt.RestaruntActivity;

/**
 * Created by Rajesh Kumar on 05-09-2017.
 */

public class ShowRestaurant_Adapter extends RecyclerView.Adapter<ShowRestaurant_Adapter.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    public ShowRestaurant_Adapter(Context context){
        this.context=context;
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

        holder.textView.setText("Restaurant "+(position+1));
        holder.rel_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RestaruntActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}