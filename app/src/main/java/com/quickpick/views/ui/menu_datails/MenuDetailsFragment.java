package com.quickpick.views.ui.menu_datails;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickpick.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rajesh kumar on 08-04-2018.
 */

public class MenuDetailsFragment extends Fragment {
    View view;
    String[] choice_name ={"Shami Kabab","Curry Sauce","Yogurt Salad","Mint Salad"};
    String[] choice_price ={"200","300","400","500"};
    @Bind(R.id.ll_choice)
    LinearLayout ll_choice;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.txt_item_name)
    TextView txt_item_name;
    @Bind(R.id.txt_description)
    TextView txt_description;
//    @Bind(R.id.img_back)
//    ImageView img_back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.menudetailroot, container, false);
        ButterKnife.bind(this,view);
        prepareChoicItems();
        dynamicToolbarColor();
        toolbarTextAppernce();

        String item_name =  getArguments().getString("Item_name");
        String item_des =  getArguments().getString("description");
        txt_item_name.setText(item_name);
        txt_description.setText(item_des);
//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().popBackStack();
//            }
//        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(item_name);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        return view;
    }

    private void prepareChoicItems() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CheckBox[] checkBoxes = new CheckBox[choice_name.length];
        TextView[] text_price = new TextView[choice_name.length];
        for (int i = 0; i < choice_name.length; i++) {
            View view = inflater.inflate(R.layout.restaurent_menu_detail_choice, null);
            checkBoxes[i] = (CheckBox) view.findViewById(R.id.checkbox);
            text_price[i] = (TextView) view.findViewById(R.id.txt_price);
            checkBoxes[i].setText(choice_name[i]);
            text_price[i].setText(choice_price[i]);

            ll_choice.addView(view);
        }
    }
    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.img_four);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }
}
