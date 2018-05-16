package com.quickpick.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.customizedmenu.MainOuter;
import com.quickpick.model.menu.menunew.Item;
import com.quickpick.presenter.utils.AdjestListviewHeight;
import com.quickpick.presenter.utils.Image_Fetch;
import com.quickpick.views.ui.menu_datails.MenuDetailsActivity;
import com.quickpick.views.ui.restarunt.tabs.Restaurant_menu_fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rajesh Kumar on 12-05-2018.
 */
public class Restaurent_menu_tab_new extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private MyViewHolder holder;
    View root;
    Context context;
    ArrayList<String > header_names;
    int list_size =0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_SUB_ITEM = 3;
    String restaurent_name="",time="";
    Restaurant_menu_fragment fragment;
    int i = 0;
    MainOuter data;



    public Restaurent_menu_tab_new(Context context,MainOuter data, Restaurant_menu_fragment fragment){
        this.context = context;
        this.data = data;
        this.fragment = fragment;
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
        ListView ll_subitems;


        public MyViewHolder(View view) {
            super(view);
            txt_header = (TextView)view.findViewById(R.id.txt_header);
            ll_view_items = (LinearLayout)view.findViewById(R.id.ll_view_items);
            ll_subitems = view.findViewById(R.id.ll_subitems);

        }
    }

    public class MyViewHolder_Sub extends RecyclerView.ViewHolder {


        public MyViewHolder_Sub(View view) {
            super(view);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_new_menu_item, parent, false);
            return new Restaurent_menu_tab_new.MyViewHolder(root);
        }else if(viewType==TYPE_HEADER){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false);
            return new Restaurent_menu_tab_new.HeaderViewHolder(root);
        }/*else if(viewType ==TYPE_SUB_ITEM){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_menu_name, parent, false);
            return new Restaurent_menu_tab_new.MyViewHolder_Sub(root);
        }*/
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {
        if(holder1 instanceof MyViewHolder){
            MyViewHolder holder = (MyViewHolder)holder1;
            holder.txt_header.setText(data.getItems().get(position-1).getHeader());
//            holder.ll_subitems.setAdapter(new MenuSubadapter(context,data.getItems().get(position-1).getSubItems()));
//            AdjestListviewHeight.setListViewHeightBasedOnItems(holder.ll_subitems);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView[] textView = new TextView[data.getItems().get(position-1).getSubItems().size()];
            TextView[] txt_amount = new TextView[data.getItems().get(position-1).getSubItems().size()];
            TextView[] txt_description = new TextView[data.getItems().get(position-1).getSubItems().size()];
            ImageView[] img_res = new ImageView[data.getItems().get(position-1).getSubItems().size()];
            LinearLayout[] ll_item = new LinearLayout[data.getItems().get(position-1).getSubItems().size()];
            for ( i = 0; i <data.getItems().get(position-1).getSubItems().size(); i++) {
                View view = inflater.inflate(R.layout.restaurent_menu_name, null);
                textView[i] = (TextView)view.findViewById(R.id.txt_item);
                txt_amount[i] = (TextView)view.findViewById(R.id.txt_amount);
                txt_description[i] = (TextView)view.findViewById(R.id.txt_description);
                img_res[i] = (ImageView)view.findViewById(R.id.img_src);
                ll_item[i] = view.findViewById(R.id.ll_item);

                textView[i].setId(i+(position-1));
                img_res[i].setId(i+(position-1));
                ll_item[i].setId(i+(position-1));
                txt_description[i].setId(i+(position-1));

                textView[i].setText(data.getItems().get(position-1).getSubItems().get(i).getSubItem_name());
                txt_amount[i].setText("₹"+data.getItems().get(position-1).getSubItems().get(i).getAmount());
                txt_description[i].setText(data.getItems().get(position-1).getSubItems().get(i).getDescription());
                Image_Fetch.getInstance().LoadImage(context, img_res[i], data.getItems().get(position-1).getSubItems().get(i).getImage());
                holder.ll_view_items.addView(view);
                ll_item[i].setOnClickListener(view1 -> {
//                    String  name = (textView[view1.getId()]).getText().toString();
//
//                    String des = ((TextView)txt_description[view1.getId()]).getText().toString();
//                    String price = ((TextView)txt_amount[view1.getId()]).getText().toString();
//                    Log.e("des is ","<><>"+des);
                    Log.e("item id is ","<>>"+data.getItems().get(position-1).getSubItems().get(view1.getId()).getSubItem_name());
//                    Log.e("position is ","<>>>"+data.getItems().get(position-1).getSubItems());
                    Log.e("sub list item position ","<>>><><"+view1.getId());

//                    Intent intent = new Intent(context,MenuDetailsActivity.class);
//                    intent.putExtra("Item_name",data.getItems().get(position-1).getSubItems().get(i).getSubItem_name());
//                    intent.putExtra("description",data.getItems().get(position-1).getSubItems().get(i).getDescription());
//                    intent.putExtra("price",data.getItems().get(position-1).getSubItems().get(i).getAmount());
//                    intent.putExtra("item_id",data.getItems().get(position-1).getSubItems().get(i).getItem_Id());
//                    intent.putExtra("qty",data.getItems().get(position-1).getSubItems().get(i).getNumberofQtys());
//                    intent.putExtra("RestaurantID",data.getItems().get(position-1).getSubItems().get(i).getRestaurantID());
//                    context.startActivity(intent);

                });

//
            }

            Fetching_items_completed fetching_items_completed = (Fetching_items_completed)fragment;
            fetching_items_completed.completed();
        }
        else{
            Restaurent_menu_tab_new.HeaderViewHolder headerViewHolder = (Restaurent_menu_tab_new.HeaderViewHolder)holder1;
            headerViewHolder.txt_res_name.setText(restaurent_name);
            headerViewHolder.txt_time.setText(time);


        }



    }

    @Override
    public int getItemCount() {

        return data.getItems().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }else {

                return TYPE_ITEM;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
}
