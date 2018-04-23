package com.quickpick.presenter.utils;

import android.content.Context;


import com.quickpick.R;
import com.quickpick.model.StoredDB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajesh Kumar on 13-07-2017.
 */

public class Dashboard_items {


    protected  String[] titles;
    ArrayList<HashMap<String ,String >> data = new ArrayList<>();
    public Dashboard_items(Context context, String coming_from){
        if(null!=((String ) StoredDB.getInstance(context).getStorageValue("id"))) {
            if (((String) StoredDB.getInstance(context).getStorageValue("id")).length() != 0) {
                titles = context.getResources().getStringArray(R.array.dashboard_items_menu);

            } else {
                titles = context.getResources().getStringArray(R.array.dashboard_items_menu_without_signout);

            }
        }else{
            titles = context.getResources().getStringArray(R.array.dashboard_items_menu_without_signout);
        }


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
