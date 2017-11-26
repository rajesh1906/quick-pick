package com.quick_pick.Presenter.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quick_pick.R;


public final class Image_Fetch {
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
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imgview);
    }
}
