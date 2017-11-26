package com.quick_pick.Presenter.utils;

import android.content.Context;


import com.quick_pick.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajesh Kumar on 13-07-2017.
 */

public class Dashboard_items {


    protected  String[] titles;
    ArrayList<HashMap<String ,String >> data = new ArrayList<>();
    public Dashboard_items(Context context, String coming_from){
        titles = context.getResources().getStringArray(R.array.dashboard_items_menu);

    }
    public ArrayList<HashMap<String ,String >> getDashBoardItems(){
       for (int i = 0;i<titles.length;i++){
           HashMap<String,String > items = new HashMap<>();
           items.put("title",titles[i]);
           data.add(items);
       }
        return data;
    }
}
