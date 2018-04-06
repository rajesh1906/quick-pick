package com.quickpick.views.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickpick.R;
import com.quickpick.model.menu.menunew.Item;
import com.quickpick.presenter.utils.Image_Fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rajesh Kumar on 05-04-2018.
 */
public class Restaurent_menu_tab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    public Restaurent_menu_tab(Context context,HashMap<String,List<String>> display_data,HashMap<String, ArrayList<HashMap<String ,String >>> additional_data,String restaurent_name,String time) {
        this.context = context;
        this.display_data = display_data;
        this.additional_data = additional_data;
        list_size = hashmapKeys(display_data).size();
        final_list = new TreeMap<>(display_data);
        this.restaurent_name = restaurent_name;
        this.time = time;
        header_names = hashmapKeys(final_list);

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
            ImageView[] img_res = new ImageView[final_list.get(header_names.get(position-1)).size()];
            for (int i = 0; i < final_list.get(header_names.get(position-1)).size(); i++) {
                View view = inflater.inflate(R.layout.restaurent_menu_name, null);
                textView[i] = (TextView)view.findViewById(R.id.txt_item);
                textView[i].setText(final_list.get(header_names.get(position-1)).get(i));
//            Image_Fetch.getInstance().LoadImage(context,img_res[i],additional_data.get(header_names.get(position)).get(i).get("ItemUrl"));
                //Image_Fetch.getInstance().LoadImage(context,img_res[i],"http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png");
                textView[i].setId(i+(position-1));
                holder.ll_view_items.addView(view);
//            Log.e("item id is ","##"+additional_data.get(header_names.get(position)).get(i).get("Item_Id"));

                textView[i].setOnClickListener(view1 -> {
                    String  name = ((TextView) view1).getText().toString();
//                Log.e("name is ","<><>"+name);
                    Toast.makeText(context,name,Toast.LENGTH_SHORT).show();

                });
            }
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
