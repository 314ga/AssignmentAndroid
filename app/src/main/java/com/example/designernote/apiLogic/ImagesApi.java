package com.example.designernote.apiLogic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImagesApi {

    @GET("search/photos/")
    Call<ResultList> getImages(@Query("client_id") String clientId, @Query("per_page")String perPage, @Query("query") String searchValue);
}
