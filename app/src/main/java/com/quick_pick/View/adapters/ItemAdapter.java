package com.quick_pick.View.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.quick_pick.R;
import com.quick_pick.View.ui.BaseActivity;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajesh Kumar on 18-07-2017.
 */

 public class ItemAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> dataList;
    int pos;
    Context context;

    public ItemAdapter(Context context,
                       ArrayList<HashMap<String, String>> dataList) {
        this.dataList = dataList;
        this.context = context;
        Log.e("data list size is ","<><><"+dataList.size());
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(context);
        }
        gridView = inflater.inflate(R.layout.home_list_items, null);
        TextView title = (TextView) gridView.findViewById(R.id.title);
        title.setText(dataList.get(position).get("title"));
        return gridView;
    }

    @Override
    public int getCount() {
        return dataList.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
