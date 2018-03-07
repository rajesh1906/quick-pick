package com.quickpick.views.ui.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.quickpick.R;
import com.quickpick.views.ui.dashboard.GetCategory_Id;

import java.util.ArrayList;

/**
 * Created by Rajesh kumar on 07-03-2018.
 */

public class CustomDialog {
    public static CustomDialog customDialog;


    public static CustomDialog getInstance(){

        if(customDialog==null){
            customDialog = new CustomDialog();
        }
        return customDialog;

    }


    public void showCategory_Dialog(final Context activity, ArrayList<String> items){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.categoriesdialog);
        ListView ll_categories = (ListView)dialog.findViewById(R.id.ll_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,android.R.id.text1,items);
        ll_categories.setAdapter(adapter);
        ll_categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetCategory_Id getCategory_id =(GetCategory_Id)activity;
                getCategory_id.getId(position);
            }
        });
        dialog.show();
    }





}
