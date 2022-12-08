package com.example.draw_imagination.voice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceRecognition extends Activity {

    public EditText edtxt; // 음성인식 결과 전달할 edittext
    Intent intent;
    public Context context; // toast message에 사용

    public void VoiceRecognitionButton (SpeechRecognizer sRecog) {
        // RecognizerIntent 생성
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        // RecognitionListener
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // 음성인식 준비 완료했을 때 호출
            }

            @Override
            public void onBeginningOfSpeech() {
                // 말하기 시작했을 때 호출
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // 입력받는 소리의 크기
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // 인식한 단어를 buffer에 담는다.
            }

            @Override
            public void onEndOfSpeech() {
                // 말하기 중지했을 때 호출
            }

            @Override
            public void onError(int error) {
                // 네트워크 오류, 인식 오류가 발생했을 때 호출
                String message;

                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        message = "오디오 녹음 오류";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        message = "클라이언트 오류";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        message = "권한 부족";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        message = "네트워크 오류";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        message = "네트워크 타임아웃";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        message = "인식 결과 없음";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        message = "RECOGNIZER 가 사용중";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        message = "서버 오류";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        message = "음성인식 시간초과";
                        break;
                    default:
                        message = "알 수 없는 오류";
                        break;
                }
                Toast.makeText(context, "ERROR : " + message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                // 음성인식 결과가 준비되면 호출
                // 말을 하면 ArrayList에 단어를 넣고 textView에서 단어를 이어준다.
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                for(int i = 0; i < matches.size() ; i++){
                    edtxt.setText(matches.get(i));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // 부분 인식 결과를 사용할 수 있을 때 호출
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // 향후 이벤트를 추가하기 위해 예약
            }
        };

        sRecog.setRecognitionListener(listener);    // 리스너 할당
        sRecog.startListening(intent);  // 듣기
    }
}