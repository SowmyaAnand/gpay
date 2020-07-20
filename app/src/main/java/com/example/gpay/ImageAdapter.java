package com.example.gpay;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImageAdapter extends PagerAdapter {
    Context mcontext;
    ImageAdapter(Context context)
    {
        this.mcontext=context;
    }



    @Override
    public int getCount() {
        return slideImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }

    private int[] slideImageId = new int[]{
            R.drawable.img,R.drawable.circle,R.drawable.ic_textsms_black_24dp
    };

    @Override
    public Object instantiateItem(ViewGroup container , int position)
    {
        ImageView imageview = new ImageView(mcontext);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setImageResource(slideImageId[position]);
        ((ViewPager) container).addView(imageview,0);
        return imageview;
    }
@Override
    public void destroyItem(ViewGroup container,int position,Object object)
{
    ((ViewPager)container).removeView((ImageView)object);
}


}
