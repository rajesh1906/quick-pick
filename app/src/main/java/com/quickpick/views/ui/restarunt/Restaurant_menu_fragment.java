package com.quickpick.views.ui.restarunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.quickpick.R;

import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 20-11-2017.
 */

public class Restaurant_menu_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.resuarunt_menu_item,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
