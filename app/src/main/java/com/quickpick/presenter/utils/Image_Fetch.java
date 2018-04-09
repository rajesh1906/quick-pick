package com.quickpick.presenter.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quickpick.R;

/**
 * Created by Rajesh Kumar on 05-04-2018.
 */
public class Image_Fetch {
    public static Image_Fetch instance;
    public static Image_Fetch getInstance() {
        if (null == instance) {
            instance = new Image_Fetch();
        }
        return instance;
    }

    public void LoadImage(Context context, ImageView imgview, String source) {
        Glide.with(context)
                .load(source)
                .centerCrop()
//                .placeholder(R.drawable.img_four)
//                .error(R.drawable.img_one)
                .into(imgview);
    }
}
