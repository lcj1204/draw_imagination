package com.example.draw_imagination.dalle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class dalleResponse {
    @SerializedName("created")
    Integer created;

    @SerializedName("data")
    public List<datum> data =null;

    public class datum {
        @SerializedName("url")
        public String url;
    }
}
