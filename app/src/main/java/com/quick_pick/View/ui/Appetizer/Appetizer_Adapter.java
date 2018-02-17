package com.quick_pick.View.ui.Appetizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quick_pick.Model.Appetizer.Appetizer;
import com.quick_pick.Model.Appetizer.Items;
import com.quick_pick.Model.menu.Menu;
import com.quick_pick.R;
import com.quick_pick.View.adapters.Restaurant_menu_item_Adapter;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Appetizer_Adapter  extends RecyclerView.Adapter<Appetizer_Adapter.MyViewHolder> {
private MyViewHolder holder;
        View root;
        Context context;
        ArrayList<Items> dataList;
        String Res_name="";
public Appetizer_Adapter(Context context, ArrayList<Items> dataList){
        this.context=context;
        this.dataList=dataList;

        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView item_name,item_amount;

    public MyViewHolder(View view) {
        super(view);
        item_name = (TextView)view.findViewById(R.id.item_name);
        item_amount = (TextView)view.findViewById(R.id.item_amount);


    }
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menu_fragment, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.item_name.setText(dataList.get(position).getItemName());
            holder.item_amount.setText(" ₹"+dataList.get(position).getAmount()+".00");

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
