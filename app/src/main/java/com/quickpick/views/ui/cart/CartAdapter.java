package com.quickpick.views.ui.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickpick.R;
import com.quickpick.views.adapters.Restaurant_menu_Adapter;

/**
 * Created by Rajesh Kumar on 18-04-2018.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    View root;
    private MyViewHolder holder;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.resuarunt_menu_item, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}
