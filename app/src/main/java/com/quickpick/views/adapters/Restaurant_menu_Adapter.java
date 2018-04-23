package com.quickpick.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by Rajesh Kumar on 20-11-2017.
 */

public class Restaurant_menu_Adapter extends RecyclerView.Adapter<Restaurant_menu_Adapter.MyViewHolder> {
    private MyViewHolder holder;
    View root;
    Context context;
    List<Item> item;
    public Restaurant_menu_Adapter(Context context,List<Item> item) {
        this.context = context;
        this.item = item;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,txt_amount,txt_pay;
        ImageView imageView;
        RelativeLayout rel_item;
        LinearLayout ll_cart;


        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.txt_item);
            txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            imageView = (ImageView)view.findViewById(R.id.img_res);
            txt_pay = view.findViewById(R.id.txt_pay);
            rel_item = (RelativeLayout)view.findViewById(R.id.rel_item);
            ll_cart = view.findViewById(R.id.ll_cart);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        root = LayoutInflater.from(parent.getContext()).inflate(R.layout.resuarunt_menu_item, parent, false);
        holder = new MyViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.textView.setText(item.get(position).getItemName());
        holder.txt_amount.setText("₹"+item.get(position).getAmount()+".00");
        holder.txt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity(new Intent(context, Appetizer_Dashboard.class));
                CustomDialog.getInstance().showCategory_Dialog(context, new CustomDialog.getpaymentType() {
                    @Override
                    public void getpayment() {

                    }
                });
            }
        });
        holder.rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("item_name",item.get(position).getItemName());
                intent.putExtra("item_price",item.get(position).getAmount());
                context.startActivity(intent);*/
            }
        });
        holder.ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Cart under construction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

