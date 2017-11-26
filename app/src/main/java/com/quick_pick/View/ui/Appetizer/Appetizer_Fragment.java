package com.quick_pick.View.ui.Appetizer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick_pick.R;

/**
 * Created by Rajesh kumar on 26-11-2017.
 */

public class Appetizer_Fragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.restaurant_menu_fragment,container,false);


        return view;
    }
}
