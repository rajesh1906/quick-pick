package com.quickpick.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.menu.menunew.Item;
import com.quickpick.presenter.utils.Image_Fetch;
import com.quickpick.views.ui.menu_datails.MenuDetailsActivity;
import com.quickpick.views.ui.restarunt.tabs.Restaurant_menu_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rajesh Kumar on 05-04-2018.
 */
public class Restaurent_menu_tab extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private MyViewHolder holder;
    View root;
    Context context;
    List<Item> item;
    HashMap<String,List<String>> display_data;
    ArrayList<String > header_names;
    int list_size =0;
    Map<String,List<String>> final_list ;
    HashMap<String, ArrayList<HashMap<String ,String >>> additional_data;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    String restaurent_name="",time="";
    Restaurant_menu_fragment fragment;
    int i = 0;
    public Restaurent_menu_tab(Context context,HashMap<String,List<String>> display_data,HashMap<String, ArrayList<HashMap<String ,String >>> additional_data,String restaurent_name,String time,Restaurant_menu_fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        this.display_data = display_data;
        this.additional_data = additional_data;
        list_size = hashmapKeys(display_data).size();
        final_list = new TreeMap<>(display_data);
        this.restaurent_name = restaurent_name;
        this.time = time;
        header_names = hashmapKeys(final_list);
       /* ShowViews showViews =(ShowViews) DashboardTabs.instance;
        showViews.fabShowing(false);*/

    }
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txt_res_name,txt_time;
        public HeaderViewHolder(View view) {
            super(view);
            txt_res_name = (TextView)view.findViewById(R.id.txt_res_name);
            txt_time = (TextView)view.findViewById(R.id.txt_time);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_header;
        LinearLayout ll_view_items;


        public MyViewHolder(View view) {
            super(view);
            txt_header = (TextView)view.findViewById(R.id.txt_header);
            ll_view_items = (LinearLayout)view.findViewById(R.id.ll_view_items);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_new_menu_item, parent, false);
            return new MyViewHolder(root);
        }else if(viewType==TYPE_HEADER){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false);
            return new HeaderViewHolder(root);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {
        if(holder1 instanceof MyViewHolder){
            MyViewHolder holder = (MyViewHolder)holder1;
            holder.txt_header.setText(header_names.get(position-1));
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView[] textView = new TextView[final_list.get(header_names.get(position-1)).size()];
            TextView[] txt_amount = new TextView[final_list.get(header_names.get(position-1)).size()];
            TextView[] txt_description = new TextView[final_list.get(header_names.get(position-1)).size()];
            ImageView[] img_res = new ImageView[final_list.get(header_names.get(position-1)).size()];
            LinearLayout[] ll_item = new LinearLayout[final_list.get(header_names.get(position-1)).size()];
            for ( i = 0; i < final_list.get(header_names.get(position-1)).size(); i++) {
                View view = inflater.inflate(R.layout.restaurent_menu_name, null);
                textView[i] = (TextView)view.findViewById(R.id.txt_item);
                txt_amount[i] = (TextView)view.findViewById(R.id.txt_amount);
                txt_description[i] = (TextView)view.findViewById(R.id.txt_description);
                img_res[i] = (ImageView)view.findViewById(R.id.img_src);
                ll_item[i] = view.findViewById(R.id.ll_item);
                textView[i].setText(final_list.get(header_names.get(position-1)).get(i));
                txt_amount[i].setText("₹"+additional_data.get(header_names.get(position-1)).get(i).get("Amount"));
                txt_description[i].setText(additional_data.get(header_names.get(position-1)).get(i).get("Description"));

                textView[i].setId(i+(position-1));
                img_res[i].setId(i+(position-1));
                ll_item[i].setId(i+(position-1));
                txt_description[i].setId(i+(position-1));
                holder.ll_view_items.addView(view);
                ll_item[i].setOnClickListener(view1 -> {
                    String  name = (textView[view1.getId()]).getText().toString();

                    String des = ((TextView)txt_description[view1.getId()]).getText().toString();
                    String price = ((TextView)txt_amount[view1.getId()]).getText().toString();
                Log.e("des is ","<><>"+des);

                    Intent intent = new Intent(context,MenuDetailsActivity.class);
                    intent.putExtra("Item_name",name);
                    intent.putExtra("description",des);
                    intent.putExtra("price",price);
                    context.startActivity(intent);

                });
            }
            Fetching_items_completed fetching_items_completed = (Fetching_items_completed)fragment;
            fetching_items_completed.completed();
        }else{
            HeaderViewHolder headerViewHolder = (HeaderViewHolder)holder1;
            headerViewHolder.txt_res_name.setText(restaurent_name);
            headerViewHolder.txt_time.setText(time);


        }



    }

    @Override
    public int getItemCount() {

        return list_size+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    private ArrayList<String > hashmapKeys(Map<String,List<String>> display_data){
        ArrayList<String > keynames = new ArrayList<>();
        for ( Map.Entry<String,List<String >> entry : display_data.entrySet()) {
            String key = entry.getKey();
//             = entry.getValue();
            Log.e("keys is ","<><"+key);
            keynames.add(key);
            // do something with key and/or tab
        }

        return keynames;
    }
}
