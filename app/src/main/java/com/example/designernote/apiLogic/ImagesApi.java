package com.example.designernote.apiLogic;

import com.example.designernote.R;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImagesApi {

    @GET("search/photos/?"+ R.string.client_api_id + "&per_page=30&query={searchValue}")
    Result[] images(@Path("searchValue") String searchValue);
}
