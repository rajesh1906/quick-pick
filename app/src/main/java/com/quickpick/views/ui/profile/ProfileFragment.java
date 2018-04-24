package com.quickpick.views.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quickpick.R;
import com.quickpick.views.ui.dashboard.DashboardTabs;
import com.quickpick.views.ui.restarunt.tabs.Restaurant_menu_fragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Kumar on 24-04-2018.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.ll_trackorder)
    LinearLayout ll_trackorder;
    @Bind(R.id.img_menu)
    ImageView img_menu;

    public static ProfileFragment newInstance(int index) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
//        initDemoList(view);
        ButterKnife.bind(this,view);

        ll_trackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.container_fag,new TrackFragment());
                ft.addToBackStack("profile");
                ft.commit();
            }
        });

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashboardTabs)getActivity()).handleDrawer();
            }
        });

        return view;

    }
}
