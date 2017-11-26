package com.quick_pick.View.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quick_pick.R;
import com.quick_pick.View.ui.Appetizer.Appetizer_Dashboard;

/**
 * Created by Rajesh Kumar on 20-11-2017.
 */

public class Restaurant_menu_Adapter extends RecyclerView.Adapter<Restaurant_menu_Adapter.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;

    public Restaurant_menu_Adapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        RelativeLayout rel_item;


        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.txt_item);
            imageView = (ImageView)view.findViewById(R.id.img_item);
            rel_item = (RelativeLayout)view.findViewById(R.id.rel_item);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.resuarunt_menu_item, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.textView.setText("Item " + (position + 1));
        holder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Appetizer_Dashboard.class));
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

