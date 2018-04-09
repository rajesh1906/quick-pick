package com.quickpick.views.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quickpick.R;
import com.quickpick.model.adds.AddsData;
import com.quickpick.presenter.utils.Image_Fetch;

import java.util.ArrayList;


/**
 * Created by ct on 01-09-2017.
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private Integer[] IMAGES;
    private LayoutInflater inflater;
    private Context context;

    ArrayList<AddsData> addsData;

    public SlidingImage_Adapter(Context context, Integer[]  IMAGES,ArrayList<AddsData> addsData) {
        this.context = context;
        this.IMAGES=IMAGES;
        this.addsData = addsData;
        Log.e("data lenght is ","<><>"+addsData.size());
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return addsData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.home_page_item, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        try {
//            URL url = new URL(addsData.get(position).getFirsturls());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.e("url is ","<><><"+addsData.get(position).getFirsturls());
//            imageView.setImageBitmap(bmp);
            Image_Fetch.getInstance().LoadImage(context, imageView, "http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png");
//            Picasso.with(context).load(R.mipmap.ic_launcher).into(imageView);
//            Picasso.with(context)
//                    .load("http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png").into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }


//        imageView.setImageResource(IMAGES[position]);
//        Glide.with(context)
//                .load(addsData.get(position).getFirsturls())
//                .override(300, 200)
//                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("position is ","<><>"+position);

            }
        });
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}