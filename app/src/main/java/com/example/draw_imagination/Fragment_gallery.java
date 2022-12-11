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
            R.drawable.img4_1,
            R.drawable.img4_2,
            R.drawable.img4_3,
            R.drawable.img5_1,
            R.drawable.img5_2,
            R.drawable.img5_3,
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
            "파란 모자, 빨간 장갑, 녹색 셔츠, 그리고 노란 바지를 입은 아기 펭귄의 이모지",
            "테이블 위에 놓여있는 유리잔",
            "오각형의 녹색 시계",
            "거대한 산호의 사진",
            "호두의 단면도",
            "호메로스의 흉상 사진",
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
            " X ",
            " X ",
            " X ",
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
