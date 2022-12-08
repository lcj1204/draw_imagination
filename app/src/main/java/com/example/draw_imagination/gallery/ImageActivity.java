package com.example.draw_imagination.gallery;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.draw_imagination.R;

public class ImageActivity extends Activity {

    Button back;
    ImageView imageView;
    TextView textView;
    TextView styleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery_dialog);

        imageView = (ImageView) findViewById(R.id.gal_imageView);
        textView = (TextView) findViewById(R.id.gal_textView);
        styleView = (TextView) findViewById(R.id.gal_styleView);
        setImage(imageView);
        setText(textView, "text ID");
        setText(styleView, "style ID");

        back = (Button) findViewById(R.id.gal_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 텍스트 복사
        // 텍스트뷰 길게 누르면 발생하는 이벤트
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String text = textView.getText().toString();
                createClipData(text);
                return false;
            }
        });

        styleView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String text = styleView.getText().toString();
                createClipData(text);
                return false;
            }
        });
    }

    private void setImage(ImageView imageView) {
        Intent receivedIntent = getIntent();

        // intent로 읽은 이미지 ID -> ImageView의 리로스로 설정
        int imgID = (Integer) receivedIntent.getExtras().get("image ID");
        imageView.setImageResource(imgID);
    }

    private void setText(TextView textView, String ID) {
        Intent receivedIntent = getIntent();

        // intent로 읽은 이미지 ID -> ImageView의 리로스로 설정
        String text = (String) receivedIntent.getExtras().get(ID);
        textView.setText(text);
    }

    public void createClipData(String message){
        //클립보드 복사기능
        ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext()
                .getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("message", message);

        //클립보드에 배치
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "복사되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
