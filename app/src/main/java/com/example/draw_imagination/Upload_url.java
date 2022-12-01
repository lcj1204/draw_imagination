package com.example.draw_imagination;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Upload_url extends AppCompatActivity {

    //url 이미지 test
    // https://wonpaper.tistory.com/207
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url_test);

        imageView1 = findViewById(R.id.imageView1);

        String imageStr = "https://cdn.openai.com/dall-e-2/demos/text2im/astronaut/horse/photo/0.jpg";
        Glide.with(this).load(imageStr).into(imageView1);

    }
}