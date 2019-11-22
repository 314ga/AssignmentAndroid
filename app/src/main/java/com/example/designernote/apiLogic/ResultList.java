package com.example.designernote.apiLogic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultList
{
        @SerializedName("results")
        @Expose
        private List<Result> images;

    public List<Result> getImages() {
        return images;
    }

    public void setImages(List<Result> images) {
        this.images = images;
    }
}
