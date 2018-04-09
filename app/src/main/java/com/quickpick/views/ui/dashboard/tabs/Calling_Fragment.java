package com.quickpick.views.ui.dashboard.tabs;

import com.quickpick.model.adds.AddsData;
import com.quickpick.views.ui.dashboard.ShowViews;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 02-04-2018.
 */
public interface Calling_Fragment {
    void calling(FloatingActionButton floatingActionButton, String coming_from);
    ArrayList<AddsData> getAddsData();
}
