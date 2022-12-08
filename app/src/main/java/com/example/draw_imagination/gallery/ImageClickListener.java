package com.example.draw_imagination.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.draw_imagination.Fragment_gallery;

public class ImageClickListener implements View.OnClickListener {

    Context context;
    int imgID;
    String text;
//    int imgID2;

    public ImageClickListener(Context context, int imgID, String text) {
        this.context = context;
        this.imgID = imgID;
        this.text = text;
//        this.imgID2 = imgID2;
    }

    public void onClick(View v) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image ID", imgID);
        intent.putExtra("text ID", text);
//        intent.putExtra("image ID2", imgID2);
        context.startActivity(intent);
    }
}
