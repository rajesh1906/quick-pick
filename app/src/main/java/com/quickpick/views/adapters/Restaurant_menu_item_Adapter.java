package com.quickpick.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.menu.Menu;
import com.quickpick.views.ui.appetizer.Appetizer_Dashboard;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 20-11-2017.
 */

public class Restaurant_menu_item_Adapter  extends RecyclerView.Adapter<Restaurant_menu_item_Adapter.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    ArrayList<Menu> dataList;
    String Res_name="";
    public Restaurant_menu_item_Adapter(Context context, ArrayList<Menu> dataList, String Res_name){
        this.context=context;
        this.dataList=dataList;
        this.Res_name = Res_name;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.txt_item);
            imageView = (ImageView)view.findViewById(R.id.img_item);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_rest_menu_items, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.textView.setText(dataList.get(position).getMenuName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Appetizer_Dashboard.class);
                intent.putExtra("res_name",Res_name);
                intent.putExtra("menu_id",dataList.get(position).getMenuId());
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
