package com.example.draw_imagination;

import android.content.Context;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.draw_imagination.voice.VoiceRecognition;


public class Fragment_generation extends Fragment implements Image_Generation.AsyncResponse {

    EditText gen_editText;
    Button btn_gen_make;
    Button btn_gen_download;
    Button btn_gen_guide;

    ImageButton btn_mic;

    ImageView result_image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_generation, container, false);

        // 만들기 버튼
        gen_editText = (EditText) view.findViewById(R.id.gen_editText);
        btn_gen_make = (Button) view.findViewById(R.id.btn_gen_make);
        result_image = (ImageView) view.findViewById(R.id.gen_image1);
        btn_gen_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만들기 기능
//                String imageStr = "https://cdn.openai.com/dall-e-2/demos/text2im/astronaut/horse/photo/0.jpg";
//
//                Image_Generation2 gen = new Image_Generation2();
//                String s = "";
//                s = String.valueOf(gen.execute("오늘 날씨는 맑음"));
                String text = gen_editText.getText().toString();
//                s = String.valueOf(gen.execute(text));
//                System.out.println(s);

                new Image_Generation(Fragment_generation.this).execute(text);

            }
        });

        // 이미지 저장 버튼
        btn_gen_download = (Button) view.findViewById(R.id.btn_gen_download);
        btn_gen_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Image_Download image_download = new Image_Download();
                    image_download.saveImage(result_image);
                    Toast.makeText(getActivity(), "저장 완료", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "저장 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 가이드 버튼
        btn_gen_guide = (Button) view.findViewById(R.id.btn_gen_guide);
        btn_gen_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 음성 인식
        btn_mic = (ImageButton) view.findViewById(R.id.btn_mic);

        VoiceRecognition voicerecog = new VoiceRecognition();
        voicerecog.edtxt = gen_editText;
        Context context = getActivity().getApplicationContext();
        voicerecog.context = context;
        // 새 SpeechRecognizer 를 만드는 팩토리 메서드
        SpeechRecognizer sRecog;
        sRecog = SpeechRecognizer.createSpeechRecognizer(getActivity());

        btn_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voicerecog.VoiceRecognitionButton(sRecog);
            }
        });

        return view;
    }

    @Override
    public void processFinish(String output) {
//        System.out.println("url: " + output);
        Log.i("return", "이미지 url : " + output);

        GlideApp
                .with(Fragment_generation.this)
                .load(output)
                .thumbnail(GlideApp.with(Fragment_generation.this).load(R.drawable.loading))
                .into(result_image);
    }
}
