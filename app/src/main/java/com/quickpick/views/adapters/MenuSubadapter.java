package com.quickpick.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.customizedmenu.SubItems;
import com.quickpick.presenter.utils.Image_Fetch;
import com.quickpick.views.ui.menu_datails.MenuDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 12-05-2018.
 */
public class MenuSubadapter extends BaseAdapter {

    ArrayList<SubItems> subItems;
    Context context;

    public MenuSubadapter(Context context,ArrayList<SubItems> subItems){
        this.subItems = subItems;
        this.context = context;
    }



    @Override
    public int getCount() {
        return subItems.size();
    }

    @Override
    public Object getItem(int i) {
        return subItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return subItems.indexOf(getItem(i));
    }
    private class ViewHolder {
        TextView textView ;
        TextView txt_amount ;
        TextView txt_description;
        ImageView img_res;
        LinearLayout ll_item;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.restaurent_menu_name, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.txt_item);
            holder.txt_amount = (TextView) convertView.findViewById(R.id.txt_description);
            holder.txt_description = (TextView) convertView.findViewById(R.id.txt_amount);
            holder.img_res = (ImageView) convertView.findViewById(R.id.img_src);
            holder.ll_item =  convertView.findViewById(R.id.ll_item);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        SubItems rowItem = (SubItems) getItem(position);

        holder.textView.setText(rowItem.getSubItem_name());
        holder.txt_amount.setText(rowItem.getAmount());
        holder.txt_description.setText(rowItem.getDescription());
        Image_Fetch.getInstance().LoadImage(context, holder.img_res, rowItem.getImage());
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.e("Item name is ","<><><><"+subItems.get(position).getSubItem_name());
                Intent intent = new Intent(context,MenuDetailsActivity.class);
                intent.putExtra("Item_name",subItems.get(position).getSubItem_name());
                intent.putExtra("description",subItems.get(position).getDescription());
                intent.putExtra("price",subItems.get(position).getAmount());
                intent.putExtra("item_id",subItems.get(position).getItem_Id());
                intent.putExtra("qty",subItems.get(position).getNumberofQtys());
                intent.putExtra("RestaurantID",subItems.get(position).getRestaurantID());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
