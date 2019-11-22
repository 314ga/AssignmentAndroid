package com.example.designernote.apiLogic;

import android.util.Log;

import com.example.designernote.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestImage
{
    private ResultList imagesResult;
    private String clientId;
    private String imagesPerPage;
    public RequestImage(String clientId, String imagesPerPage)
    {
        this.clientId = clientId;
        imagesResult = new ResultList();
        this.imagesPerPage = imagesPerPage;
    }
    public void requestImage(String imageSearch, OnImagesLoad onImagesLoad) {
        List<Result> result;
        ImagesApi imagesApi = ServiceGenerator.getImagesApi();
        Call<ResultList> images = imagesApi.getImages(clientId,imagesPerPage,imageSearch);
        images.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if (response.code() == 200) {
                    Log.i("Retrofit",response.body().getImages().get(0).getAltDescription() );
                    onImagesLoad.OnImagesLoad(response.body().getImages());//Updating LiveData
                }
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                t.printStackTrace();
            }
        });
    }

    public ResultList getImageResult()
    {
        return imagesResult;
    }

}
