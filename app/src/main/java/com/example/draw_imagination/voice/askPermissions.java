package com.example.draw_imagination.voice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class askPermissions {
    private Context context;
    private Activity activity;

    // 권한 요청 결과값 확인
    static final int PERMISSION_REQUEST = 0x00000001;

    // 요청할 권한 배열
    private String[] permissions = {
            Manifest.permission.RECORD_AUDIO,   // 마이크
            Manifest.permission.READ_EXTERNAL_STORAGE,  // 외부 저장소 읽기
            Manifest.permission.WRITE_EXTERNAL_STORAGE  // 외부 저장소 쓰기 (처리는 읽기와 쓰기가 함께 됨)
    };
    private List permissionList;

    // 권한 요청
    public void requestPermissions() {
        // 권한 체크 후 리턴이 false로 들어오면
        if (!checkPermission()){
            //권한 요청
            requestPermission();
        }

    }

    //생성자에서 Activity와 Context를 파라미터로 받기
    public void PermissionSupport(Activity _activity, Context _context){
        this.activity = _activity;
        this.context = _context;
    }

    //배열로 선언한 권한 중 허용되지 않은 권한 있는지 체크
    public boolean checkPermission() {
        int result;
        permissionList = new ArrayList<>();

        for(String pm : permissions){
            result = ContextCompat.checkSelfPermission(context, pm);
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(pm);
            }
        }
        if(!permissionList.isEmpty()){
            return false;
        }
        return true;
    }
    //배열로 선언한 권한에 대해 사용자에게 허용 요청
    public void requestPermission(){
        ActivityCompat.requestPermissions(activity, (String[]) permissionList.toArray(new String[permissionList.size()]), PERMISSION_REQUEST);
    }
}
