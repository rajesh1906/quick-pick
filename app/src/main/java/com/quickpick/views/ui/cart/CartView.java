package com.quickpick.views.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.quickpick.R;

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
}
