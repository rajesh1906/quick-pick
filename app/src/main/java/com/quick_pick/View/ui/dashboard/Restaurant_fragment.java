package com.quick_pick.View.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick_pick.Presenter.services.Network.APIResponse;
import com.quick_pick.Presenter.services.Network.RetrofitClient;
import com.quick_pick.R;
import com.quick_pick.View.adapters.ShowRestaurant_Adapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 19-11-2017.
 */

public class Restaurant_fragment extends Fragment {
    View view;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.resatarunt_list_fragment,container,false);
        ButterKnife.bind(this,view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(new ShowRestaurant_Adapter(getActivity()));
        fetchRestaurent();
        return view;
    }

    private void fetchRestaurent(){
        RetrofitClient.getInstance().doBackProcess(getActivity(), null, "", new APIResponse() {
            @Override
            public void onSuccess(String res) {

            }

            @Override
            public void onFailure(String res) {

            }
        });
    }
}
