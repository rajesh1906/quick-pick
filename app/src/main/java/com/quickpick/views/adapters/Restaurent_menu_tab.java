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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickpick.R;
import com.quickpick.model.menu.menunew.Item;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rajesh Kumar on 05-04-2018.
 */
public class Restaurent_menu_tab extends RecyclerView.Adapter<Restaurent_menu_tab.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    List<Item> item;
    HashMap<String,List<String>> display_data;
    ArrayList<String > header_names;
    int list_size =0;
    Map<String,List<String>> final_list ;
    public Restaurent_menu_tab(Context context,HashMap<String,List<String>> display_data) {
        this.context = context;
        this.display_data = display_data;
        list_size = hashmapKeys(display_data).size();
        final_list = new TreeMap<>(display_data);
        header_names = hashmapKeys(final_list);

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_new_menu_item, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.txt_header.setText(header_names.get(position));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Log.e(" array list is ","<><"+final_list.get(header_names.get(position)));
        TextView[] textView = new TextView[final_list.get(header_names.get(position)).size()];
        for (int i = 0; i < final_list.get(header_names.get(position)).size(); i++) {
            View view = inflater.inflate(R.layout.layout_item_demo, null);
            textView[i] = (TextView)view.findViewById(R.id.layout_item_demo_title);
            textView[i].setText(final_list.get(header_names.get(position)).get(i));
            textView[i].setId(i+position);
            holder.ll_view_items.addView(view);

            textView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String  name = ((TextView)view).getText().toString();
                    Log.e("name is ","<><>"+name);
                    Toast.makeText(context,name,Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list_size;
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
