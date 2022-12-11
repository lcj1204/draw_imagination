package com.example.draw_imagination;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.draw_imagination.dalle.dalleResponse;
import com.example.draw_imagination.dalle.variations;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_variation extends Fragment {

    Button btn_uploadImage;
    Button btn_val_make;
    Button btn_val_download;
    Button btn_val_guide;

    ImageView result_image;

    TextView text_uploadImage;

    Context context = MainActivity.ApplicationContext();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_variation, container, false);

        //이미지 선택 버튼
        result_image = (ImageView) view.findViewById(R.id.val_image1);
        text_uploadImage = (TextView) view.findViewById(R.id.text_uploadImage);

        btn_uploadImage = (Button) view.findViewById(R.id.btn_uploadImage);
        btn_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallary(view);
            }
        });

        //만들기 버튼
        btn_val_make = (Button) view.findViewById(R.id.btn_val_make);
//        String imageStr = "https://cdn.openai.com/dall-e-2/demos/text2im/astronaut/horse/photo/0.jpg";

//        GlideApp
//                .with(Fragment_variation.this)
//                .load(url)
//                .into(result_image);

        //이미지 저장 버튼
        btn_val_download = (Button) view.findViewById(R.id.btn_val_download);
        btn_val_download.setOnClickListener(new View.OnClickListener() {
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

        //가이드 버튼
        btn_val_guide = (Button) view.findViewById(R.id.btn_val_guide);

        return view;
    }

    // 이미지 선택 하면 파일명으로 바뀜
    public void setUpload_image_name(TextView textView, String image_name){
        textView.setText(image_name);
    }

    public void openGallary(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    // import image file
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri imgUri = data.getData();
                Log.i("Data", "데이터 Uri : " + imgUri);
                String[] Path = data.getData().getPath().split(":");
                String realPath = "/storage/emulated/0/" + Path[1];
                Log.i("Data", "데이터 realPath : " + realPath);
                ContentResolver resolver = getActivity().getContentResolver();

                try {
                    // put image to image view
//                    InputStream inputStream = resolver.openInputStream(imgUri);
//                    Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
//                    result_image.setImageBitmap(imgBitmap);
//                    inputStream.close();

                    // image absolute path
                    Log.i("info", "절대 경로 : 뜨기 전");
//                    String realPath = imagePath(imgUri);
//                    Log.i("info", "절대 경로 : " + realPath);
                    text_uploadImage.setText(realPath);
                    Toast.makeText(getActivity(), realPath,Toast.LENGTH_SHORT).show();

                    /* image variation request start */
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                            .connectTimeout(100, TimeUnit.SECONDS)
                            .readTimeout(100, TimeUnit.SECONDS)
                            .writeTimeout(100, TimeUnit.SECONDS);

                    httpClient.addInterceptor(logging);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.openai.com/v1/images/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient.build())
                            .build();

                    variations service = retrofit.create(variations.class);

                    File img = new File(realPath);
                    RequestBody fbody = RequestBody.create(MediaType.parse("image/png"), img);
                    System.out.println("image name: " + img.getName());

                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", img.getName(), fbody);
                    Call<dalleResponse> call = service.upload(filePart);

                    call.enqueue(new Callback<dalleResponse>() {
                        @Override
                        public void onResponse(Call<com.example.draw_imagination.dalle.dalleResponse> call, Response<dalleResponse> response) {

                            if (response.isSuccessful()) {
                                com.example.draw_imagination.dalle.dalleResponse resource = response.body();
                                List<dalleResponse.datum> datumList = resource.data;
                                String url = datumList.get(0).url;
                                Log.i("성공", "이미지 url : " + url);

                                // 구현
                                GlideApp
                                        .with(Fragment_variation.this)
                                        .load(url)
                                        .thumbnail(GlideApp.with(Fragment_variation.this).load(R.drawable.loading))
                                        .into(result_image);
                            }
                            else{
                                System.out.println("request is not successsful!");
                            }
                        }
                        @Override
                        public void onFailure(Call call, Throwable t) {
                        }
                        /* image variation request end */
                    });
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "이미지 불러오기 실패",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "이미지 불러오기 실패",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // get image absolute path from uri
    public String imagePath(Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return contentUri.getPath();
        }
        Log.i("info", " 1 ");
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Log.i("info", " 2 ");
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
//        Cursor cursor = context.getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        Log.i("info", " 3 ");
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex);
            }
        } finally {
            cursor.close();
        }
        return null;
    }

}
