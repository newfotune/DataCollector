package com.data.pooja.datacollector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Fortune on 1/12/2017.
 * @version 1.0
 * An image adapter for the display images on MainActivity.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.gas_station, R.drawable.restaurant_sign,
            R.drawable.stop_sign, R.drawable.traffic,
            R.drawable.traffic_camera, R.drawable.road_work_sign_icon
    };

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Integer[] getPictures() {
        return mThumbIds.clone();
    }

    public Object getItem(int position) {
        if (position >= mThumbIds.length)
            throw new IndexOutOfBoundsException();
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
