package com.quickpick.views.ui.cart;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.quickpick.R;
import com.quickpick.model.cartdeails.CartdetailsData;
import com.quickpick.presenter.utils.Common_methods;
import com.quickpick.presenter.utils.Constants;
import com.quickpick.views.adapters.Restaurant_menu_Adapter;
import com.quickpick.views.ui.authentication.SignIn;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 18-04-2018.
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    View root;
    private MyViewHolder holder;
    ArrayList<CartdetailsData> dataArrayList;
    DecimalFormat df = new DecimalFormat();
    Context context;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    PriceSettings priceSettings;
   public static BottomHolder bottom_holder;

    public CartAdapter(Context context, ArrayList<CartdetailsData> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
         priceSettings = CartView.cartView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
            MyViewHolder   holder = new MyViewHolder(root);
            return holder;
        }else{
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtotal_layout, parent, false);
            BottomHolder  holder = new BottomHolder(root);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {


        if(holder1 instanceof MyViewHolder){
            MyViewHolder holder = (MyViewHolder)holder1;
            holder.rd_item.setText(dataArrayList.get(position).getItemName());
            if (!dataArrayList.get(position).getAmount().matches("\\d*\\.?\\d+")) {
                holder.txt_price.setText(Constants.RUPEE + dataArrayList.get(position).getAmount());
                holder.txt_final_price.setText(Constants.RUPEE + dataArrayList.get(position).getAmount());
            } else {
                holder.txt_price.setText(Constants.RUPEE + dataArrayList.get(position).getAmount() + ".00");
                holder.txt_final_price.setText(Constants.RUPEE + dataArrayList.get(position).getAmount()+".00");
            }


            holder.txt_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("position is ", "<><>" + (position));
                    String resultent = "";

                    int price = Integer.parseInt(holder.txt_value.getText().toString().replace(Constants.RUPEE, ""));
                    if (price != 0) {
                        price--;

                        holder.txt_value.setText("" + price);

                        try {
                            float resultent_price = (price * Float.parseFloat(holder.txt_price.getText().toString().replace(Constants.RUPEE, "").replace(",", "")));
                            resultent = "" + df.format(resultent_price);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!resultent.matches("\\d*\\.?\\d+")) {
                            holder.txt_final_price.setText(Constants.RUPEE + resultent);
                        }
                        else {
                            holder.txt_final_price.setText(Constants.RUPEE + resultent + ".00");
                        }
                    }
                    priceSettings.setPrice(position,holder.txt_final_price.getText().toString());
                    priceSettings.getPrice();

                }
            });


            holder.txt_pluse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("position is ", "<><>" + (position));
                    int price = Integer.parseInt(holder.txt_value.getText().toString().replace(Constants.RUPEE, ""));
                    String resultent = "";
                    if (price >= 0) {
                        price++;
                        holder.txt_value.setText("" + price);
                        try {
                            float resultent_price = (price * Float.parseFloat(holder.txt_price.getText().toString().replace(Constants.RUPEE, "").replace(",", "")));
                            resultent = "" + df.format(resultent_price);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!resultent.matches("\\d*\\.?\\d+")) {
                            holder.txt_final_price.setText(Constants.RUPEE + resultent);
                        }
                        else {
                            holder.txt_final_price.setText(Constants.RUPEE + resultent + ".00");
                        }

                        priceSettings.setPrice(position,holder.txt_final_price.getText().toString());
                        priceSettings.getPrice();
                    }
                }
            });
        }else{
            bottom_holder = (BottomHolder)holder1;
            priceSettings.getPrice();
            bottom_holder.btn_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new Common_methods(context). makePayment();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == dataArrayList.size()) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rd_item)
        RadioButton rd_item;
        @Bind(R.id.txt_price)
        TextView txt_price;
        @Bind(R.id.txt_final_price)
        TextView txt_final_price;

        @Bind(R.id.txt_minus)
        TextView txt_minus;
        @Bind(R.id.txt_pluse)
        TextView txt_pluse;
        @Bind(R.id.txt_value)
        TextView txt_value;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public class BottomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_total_price)
        TextView txt_total_price;
        @Bind(R.id.txt_tax_price)
        TextView txt_tax_price;
        @Bind(R.id.txt_res_total)
        TextView txt_res_total;
        @Bind(R.id.btn_payment)
        Button btn_payment;

        public BottomHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
    public void updateResultent( int value){
        String resultent = ""+value;
        int res_total = value+80;
        if (!resultent.matches("\\d*\\.?\\d+")) {
            bottom_holder.txt_total_price.setText(Constants.RUPEE + value);
            bottom_holder.txt_res_total.setText(Constants.RUPEE + res_total);
        }else{
            bottom_holder.txt_total_price.setText(Constants.RUPEE + value+".00");
            bottom_holder.txt_res_total.setText(Constants.RUPEE + res_total+".00");
        }
    }
}
