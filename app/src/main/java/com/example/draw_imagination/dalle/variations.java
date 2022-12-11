package com.example.draw_imagination.dalle;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface variations {
    @Multipart
    @POST("variations")//
    @Headers("Authorization: Bearer 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")
    Call<dalleResponse> upload(@Part MultipartBody.Part image); //filename=\"img.png\"")); RequestBody image
    //@Part MultipartBody.Part n,
    //@Part MultipartBody.Part size
    //);
}
