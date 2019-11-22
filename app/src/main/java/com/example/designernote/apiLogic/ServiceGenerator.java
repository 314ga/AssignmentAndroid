package com.example.designernote.apiLogic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ImagesApi iamgesApi = retrofit.create(ImagesApi.class);

    public static ImagesApi getImagesApi() {
        return iamgesApi;
    }

}
