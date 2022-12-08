package com.example.draw_imagination;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.draw_imagination.gallery.ImageGridAdapter;

public class Fragment_gallery extends Fragment {

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
            "an illustration of a baby daikon radish",
            "an armchair in the shape of an avocado.",
            "a small red block sitting on",
            "a capybara sitting in a field at sunrise",
            "a capybara sitting in a field at sunrise",
            "a capybara sitting in a field at sunrise",
            "a photo of alamo square, san francisco, from a street at night",
            "a photo of the food of china",
            "a snail made of harp",
    };

    private String[] styles = new String[] {
            " X ",
            " X ",
            " X ",
            "painting",
            "pop art",
            "pencil sketch",
            " X ",
            " X ",
            " X ",
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_grid, container, false);

        // 그리드 이미지
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getActivity(), imgIDs, texts, styles);
        gridView.setAdapter(imageGridAdapter);

        return view;
    }

}
