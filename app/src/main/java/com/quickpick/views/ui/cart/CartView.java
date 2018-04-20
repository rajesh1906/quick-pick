package com.quickpick.views.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quickpick.R;
import com.quickpick.model.StoredDB;
import com.quickpick.model.cartdeails.CartDataRoot;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;
import com.quickpick.presenter.utils.Constants;
import com.quickpick.views.ui.dashboard.DashboardTabs;
import com.quickpick.views.ui.dashboard.ShowViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 18-04-2018.
 */
public class CartView extends Fragment implements View.OnClickListener,PriceSettings {

    View view;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.img_back)
    LinearLayout img_back;
    String total_price;
    HashMap<Integer,String > total_values = new HashMap<>();
    public static CartView cartView;
    int total=0;
    CartAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.cart_view,container,false);
        ButterKnife.bind(this,view);
        cartView = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        ShowViews showViews = (ShowViews) DashboardTabs.instance;
        showViews.fabShowing(false);
        fetchListeners();
        fetchCartData();
        return view;

    }

    private void fetchListeners(){
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                ShowViews showViews = (ShowViews) DashboardTabs.instance;
                showViews.fabShowing(true);
                getFragmentManager().popBackStack();
                break;
        }
    }
    private void fetchCartData(){
        RetrofitClient.getInstance().getEndPoint(getActivity(),"show progressbar").getResult(getparams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response ","<><>"+res);
                try{
                    CartDataRoot dataRoot = new Gson().fromJson(res,CartDataRoot.class);
                    if(dataRoot.getStatus().equalsIgnoreCase("successfully")){
                        adapter  = new CartAdapter(getActivity(),dataRoot.getCartdetailsData());
                        recyclerview.setAdapter(adapter);
                        for(int i=0;i<dataRoot.getCartdetailsData().size();i++){
                            total_values.put(i,dataRoot.getCartdetailsData().get(i).getAmount());
                        }
                        getPrice();

                    }else {
                        Toast.makeText(getActivity(), "Soming went wrong please check after some time", Toast.LENGTH_SHORT).show();
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


    private Map<String,String> getparams(){
        Map<String,String> params = new HashMap<>();
        params.put("action", APIS.CARTDETAILS);
//        params.put("loginId",(String )StoredDB.getInstance(getActivity()).getStorageValue("id"));
        params.put("loginId","1");

        return params;
    }

    @Override
    public void setPrice(int pos,String price) {
        total_values.put(pos,price);
    }

    @Override
    public String getPrice() {
        ArrayList<Integer> values = new ArrayList<>();
        for(Map.Entry entry:total_values.entrySet()){
            values.add(Integer.parseInt(entry.getValue().toString().replace(Constants.RUPEE,"").replace(".00","")));
        }
        int total=0;
        for(int i=0;i<values.size();i++){
            total = total+values.get(i);
        }
        Log.e("array list total","<><>"+total);
        adapter.updateResultent(total);
        return ""+total;
    }
}
