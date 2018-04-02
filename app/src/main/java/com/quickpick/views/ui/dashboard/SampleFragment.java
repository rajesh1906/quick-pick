package com.quickpick.views.ui.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quickpick.R;

/**
 * Created by Rajesh Kumar on 02-04-2018.
 */
public class SampleFragment extends Fragment {
    Bundle bundle;
    int from;
    View view;
    public static SampleFragment newInstance(int from) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        args.putInt("from", from);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.simple_fragment, container, false);
        bundle = this.getArguments();
        from = bundle.getInt("from", 0);
        ((TextView)view.findViewById(R.id.txt_view)).setText("Fragment "+from);
        return view;
    }

}
