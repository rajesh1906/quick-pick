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

import com.quickpick.R;
import com.quickpick.model.StoredDB;
import com.quickpick.presenter.services.Network.APIResponse;
import com.quickpick.presenter.services.Network.APIS;
import com.quickpick.presenter.services.Network.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 18-04-2018.
 */
public class CartView extends Fragment implements View.OnClickListener {

    View view;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.img_back)
    LinearLayout img_back;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.cart_view,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(new CartAdapter());
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
                getFragmentManager().popBackStack();
                break;
        }
    }


    private void fetchCartData(){
        RetrofitClient.getInstance().getEndPoint(getActivity(),"show progressbar").getResult(getparams(), new APIResponse() {
            @Override
            public void onSuccess(String res) {
                Log.e("response ","<><>"+res);
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
}
