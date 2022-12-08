package com.example.draw_imagination;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.draw_imagination.gallery.ImageActivity;
import com.example.draw_imagination.gallery.ImageGridAdapter;

public class Fragment_gallery extends Fragment {

//    ImageView gal_imageView;

    // res ID
    private int[] imgIDs = new int[] {
            R.drawable.img1_1,
            R.drawable.img1_2,
            R.drawable.img1_3,
            R.drawable.img2_1,
            R.drawable.img2_2,
            R.drawable.img2_3,
            R.drawable.img3_1,
            R.drawable.img3_2,
            R.drawable.img3_3,
    };

    private String[] texts = new String[] {
            "음...",
            "음...",
            "음...",
            "음...",
            "음...",
            "음...",
            "음...",
            "음...",
            "음...",
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_grid, container, false);

        // 그리드 이미지
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getActivity(), imgIDs, texts);
        gridView.setAdapter(imageGridAdapter);

        return view;
    }

    private void setImage(ImageView imageView) {
        Intent receivedIntent = getActivity().getIntent();

        // intent로 읽은 이미지 ID -> ImageView의 리로스로 설정
        int imgID = (Integer) receivedIntent.getExtras().get("image ID");
        imageView.setImageResource(imgID);
    }

}
