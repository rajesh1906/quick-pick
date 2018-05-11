package com.quickpick.views.ui.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.easydialog.EasyDialog;
import com.quickpick.BuildConfig;
import com.quickpick.R;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.views.ui.dashboard.GetCategory_Id;

import java.util.ArrayList;

/**
 * Created by Rajesh kumar on 07-03-2018.
 */

public class CustomDialog {
    public static CustomDialog customDialog;

    public getpaymentType getpaymenttype;


    public static CustomDialog getInstance() {

        if (customDialog == null) {
            customDialog = new CustomDialog();
        }
        return customDialog;

    }


    public void showCategory_Dialog(final Context activity,final getpaymentType getpaymenttype) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pay_dialog);
        final RadioGroup radio_group = (RadioGroup) dialog.findViewById(R.id.radio_group);
        String[] payement_array = activity.getResources().getStringArray(R.array.payment_type);
        TextView txt_cancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        Button btn_proceed = (Button) dialog.findViewById(R.id.btn_proceed);

        for (int i = 0; i < payement_array.length; i++) {
            RadioButton rbn = new RadioButton(activity);
            rbn.setId(i + 1);
            rbn.setText(payement_array[i]);
            radio_group.addView(rbn);
        }
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_group.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(activity, "Please select atleast one payment option", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                     getpaymenttype.getpayment(radio_group.getCheckedRadioButtonId());
                }
            }
        });

        dialog.show();
    }


    public void showTooltip(final Context context, ArrayList<String> category_items, View target_view, final Fragment fragment) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.categoriesdialog, null);
        final EasyDialog dialog = new EasyDialog(context)
                // .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
                .setLayout(view)
                .setBackgroundColor(context.getResources().getColor(R.color.black_transprient))
                // .setLocation(new location[])//point in screen
                .setLocationByAttachedView(target_view)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
//                .setMatchParent(true)
                .setMarginLeftAndRight(50, 50)
                .setMarginTopAndBottom(400,0)
                .setOutsideColor(context.getResources().getColor(R.color.black_transprient))
                .show();
        ListView listView = (ListView) view.findViewById(R.id.ll_categories);
        LinearLayout ll_inflate = view.findViewById(R.id.ll_inflate);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, category_items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null!=fragment) {
                    GetCategory_Id getCategory_id = (GetCategory_Id) fragment;
                    getCategory_id.getId((position+1));
                }else{
                    GetCategory_Id getCategory_id = (GetCategory_Id) context;
                    getCategory_id.getId((position+1));
                }
                dialog.dismiss();
            }
        });
       /* LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<category_items.size();i++){
            View view_item = inflater.inflate(R.layout.item_parent, null);
            TextView text1 = (TextView)view_item.findViewById(R.id.text1);
            text1.setText(category_items.get(i));
            ll_inflate.addView(view_item);
        }*/




    }


    public void commonDialog(final Context context, String title, String desc_data, final String coming_from) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_common_dialog);
        TextView desc = (TextView) dialog.findViewById(R.id.txt_dec);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        desc.setText(desc_data);
        txt_title.setText(title);

        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coming_from.equalsIgnoreCase("location")) {
                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }else{
                    context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                }
                dialog.dismiss();
            }
        });


    }

    public interface getpaymentType{
        public void getpayment(int position);
    }

    public void Alternative_note(Context context,getAlternativenote getAlternativenote){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.alternative_note_dialog);
        TextView txt_done = (TextView) dialog.findViewById(R.id.txt_done);
        EditText edt_txt = (EditText) dialog.findViewById(R.id.edt_txt);
        edt_txt.requestFocus();
        new Common_methods(context).openKeyboard(edt_txt);
        dialog.show();
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Common_methods(context).hideKeyboard(edt_txt);
                getAlternativenote.getnote(edt_txt.getText().toString());

                dialog.dismiss();
            }
        });
    }

    public interface getAlternativenote{
        void getnote(String note);

    }


}
