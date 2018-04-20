package com.quickpick.views.ui.menu_datails;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quickpick.R;
import com.quickpick.model.StoredDB;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.views.ui.customviews.CustomDialog;
import com.quickpick.views.ui.dashboard.DashboardTabs;
import com.quickpick.views.ui.dashboard.ShowViews;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 08-04-2018.
 */

public class MenuDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    View view;
    String[] choice_name ={"Shami Kabab","Curry Sauce","Yogurt Salad","Mint Salad"};
    String[] choice_price ={"200","300","400","500"};
    @Bind(R.id.ll_choice)
    LinearLayout ll_choice;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.txt_item_name)
    TextView txt_item_name;
    @Bind(R.id.txt_description)
    TextView txt_description;
    @Bind(R.id.img_back)
    LinearLayout img_back;

    @Bind(R.id.txt_minus)
    TextView txt_minus;
    @Bind(R.id.txt_pluse)
    TextView txt_pluse;
    @Bind(R.id.txt_value)
    TextView txt_value;
    String item_name = "", item_price = "";
    @Bind(R.id.txt_pay)
    TextView txt_pay;
    @Bind(R.id.txt_pay_not)
    TextView txt_pay_not;
    @Bind(R.id.txt_alternative_note)
    TextView txt_alternative_note;
    DecimalFormat df = new DecimalFormat();
    @Bind(R.id.txt_addto_cart)
    TextView txt_addto_cart;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menudetailroot);

        ButterKnife.bind(this);
        prepareChoicItems();
        dynamicToolbarColor();
        toolbarTextAppernce();
        df.setMaximumFractionDigits(2);

        String item_name =  getIntent().getExtras().getString("Item_name");
        String item_des =  getIntent().getExtras().getString("description");
        item_price = getIntent().getExtras().getString("price").replace("₹","");
        txt_item_name.setText(item_name);
        txt_description.setText(item_des);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(item_name);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
        txt_minus.setOnClickListener(this);
        txt_pluse.setOnClickListener(this);
        img_back.setOnClickListener(this);
        txt_pay.setOnClickListener(this);
        txt_alternative_note.setOnClickListener(this);
        txt_addto_cart.setOnClickListener(this);
    }

    private void prepareChoicItems() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CheckBox[] checkBoxes = new CheckBox[choice_name.length];
        TextView[] text_price = new TextView[choice_name.length];
        for (int i = 0; i < choice_name.length; i++) {
            View view = inflater.inflate(R.layout.restaurent_menu_detail_choice, null);
            checkBoxes[i] = (CheckBox) view.findViewById(R.id.checkbox);
            text_price[i] = (TextView) view.findViewById(R.id.txt_price);
            checkBoxes[i].setText(choice_name[i]);
            text_price[i].setText("₹"+choice_price[i]);

            ll_choice.addView(view);
        }
    }
    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_four);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public void onPause() {
        super.onPause();
        ShowViews showViews = (ShowViews) DashboardTabs.instance;
        showViews.fabShowing(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_minus:
                String item_value = txt_value.getText().toString();
                String resultent = "";
                try {
                    if (item_value.length() != 0) {
                        int item = Integer.parseInt(item_value);
                        Log.e("item value is ","<><>"+item);
                        if (item > 0) {
                            item--;
                            txt_value.setText("" + item);
                            try{
                                float resultent_price = (item * Float.parseFloat(item_price));
                                resultent=""+df.format(resultent_price);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            //txt_price.setText("₹" + resultent_price);
                            if(item==0) {
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent);
                                txt_pay.setVisibility(View.GONE);
                                txt_pay_not.setVisibility(View.VISIBLE);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent);
                            }
                            else {
                                txt_pay.setVisibility(View.VISIBLE);
                                txt_pay_not.setVisibility(View.GONE);
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent);
                            }


                        } else {
                            Toast.makeText(MenuDetailsActivity.this, "minus values not accepted", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_pluse:
                String item_value_pluse = txt_value.getText().toString();
                String resultent_price = "";
                try {
                    if (item_value_pluse.length() != 0) {
                        int item = Integer.parseInt(item_value_pluse);
                        if (item >= 0) {
                            item++;
                            txt_value.setText("" + item);
                            try{
                                float   value = (item * Float.parseFloat(item_price));
                                resultent_price = ""+df.format(value);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(item==0) {
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent_price);
                                txt_pay.setVisibility(View.GONE);
                                txt_pay_not.setVisibility(View.VISIBLE);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent_price);
                            }
                            else {
                                txt_pay.setVisibility(View.VISIBLE);
                                txt_pay_not.setVisibility(View.GONE);
                                txt_pay.setText("Checkout " + item + " item for ₹" + resultent_price);
                                txt_pay_not.setText("Checkout " + item + " item for ₹" + resultent_price);
                            }
                            txt_pay.setText("Checkout "+item +" item for ₹"+resultent_price);
                        } else {
                            Toast.makeText(MenuDetailsActivity.this, "minus values not accepted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        txt_value.setText("" + 1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.txt_alternative_note:
                CustomDialog.getInstance().Alternative_note(this, new CustomDialog.getAlternativenote() {
                    @Override
                    public void getnote(String note) {
                        txt_alternative_note.setText(note);
                    }
                });
                break;

            case R.id.txt_pay:
               new Common_methods(this). makePayment();
                break;
            case R.id.txt_addto_cart:
                fetchData();
                break;
        }
    }

    private void fetchData(){
        RetrofitClient.getInstance().getEndPoint(this,"show progressbar").getResult(getparams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response is ","<><>"+res);
                try{
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.getString("Status").equalsIgnoreCase("successfully")){
                        Toast.makeText(MenuDetailsActivity.this, "Item added to Cart Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(MenuDetailsActivity.this, "Somthing went wrong, please try again after some time", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String res) {

            }
        });
    }

    private Map<String ,String > getparams(){
        Map<String ,String > params = new HashMap<>();
        params.put("action",APIS.ADDCART);
        params.put("loginid",(String ) StoredDB.getInstance(this).getStorageValue("id"));
        params.put("itemid","1");
        params.put("qty","5");

        return params;
    }
}
