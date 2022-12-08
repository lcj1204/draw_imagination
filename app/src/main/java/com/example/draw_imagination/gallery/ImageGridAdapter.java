package com.example.draw_imagination.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {

    Context context = null;
    int [] imgIDs = null;
    String [] texts = null;

    public ImageGridAdapter(Context context, int[] imgID, String [] texts) {
        this.context = context;
        this.imgIDs = imgID;
        this.texts = texts;
    }

    public int getCount() {
        return (null != imgIDs) ? imgIDs.length : 0;
    }

    public Object getItem(int position) {
        return (null != imgIDs) ? imgIDs[position] : 0;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;

        if (null != convertView) {
           imageView = (ImageView) convertView;
        }
        else {
            Bitmap bp = BitmapFactory.decodeResource(context.getResources(), imgIDs[position]);
            bp = Bitmap.createScaledBitmap(bp, 300, 300, false);

            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bp);

            ImageClickListener imageViewClickListener = new ImageClickListener(context, imgIDs[position], texts[position]);
            imageView.setOnClickListener(imageViewClickListener);
        }
        return imageView;
    }
}
