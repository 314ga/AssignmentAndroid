package com.example.designernote.ui.imageInspiration;
/*
GALLERY LIBRARY FROM:
https://github.com/firdausmaulan/GlideSlider
 */
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.request.RequestOptions;
import com.example.designernote.modules.FieldChecker;
import com.example.designernote.R;
import com.example.designernote.apiLogic.ImagesApi;
import com.example.designernote.apiLogic.Result;
import com.example.designernote.apiLogic.ResultList;
import com.example.designernote.apiLogic.ServiceGenerator;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageInspirationFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    private ImageInspirationViewModel imageInspirationViewModel;
    private Button searchButton;
    private EditText editSearchText;
    private  ArrayList<String> listUrl, listName;
    private ProgressBar progressBar;
    private FieldChecker fieldChecker;
    TextView imageLink;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        imageInspirationViewModel =
                ViewModelProviders.of(this).get(ImageInspirationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery_inspiration, container, false);
        imageInspirationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        fieldChecker = new FieldChecker();
        mDemoSlider = root.findViewById(R.id.slider);
        editSearchText = root.findViewById(R.id.searchEditText);
        searchButton = root.findViewById(R.id.searchForImages);
            //TODO: Possible to add number of loaded images to gallery
        progressBar = root.findViewById(R.id.progressLoadImages);
        imageLink = root.findViewById(R.id.imagePathValue);
        addImagesToGallery(null);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldChecker.checkIfFilled(editSearchText))
                {
                    mDemoSlider.removeAllSliders();
                    progressBar.setVisibility(View.VISIBLE);
                    String searchPath = editSearchText.getText().toString();
                    searchPath = searchPath.replace(" ","-");
                    requestImages(searchPath);
                }
                else
                    Toast.makeText(getContext(), "Type value for finding inspiration", Toast.LENGTH_SHORT).show();

            }
        });
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(7000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopCyclingWhenTouch(false);

        return root;
    }

    private void addImagesToGallery(List<Result> images)
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        listUrl = new ArrayList<>();
        listName = new ArrayList<>();
        if(images != null)
        {
            for (int i = 0; i < images.size(); i++) {
                listUrl.add(images.get(i).getUrls().getRegular());
                listName.add("Author: "+ images.get(i).getUser().getName());
            }
        }
        else
        {
            listUrl.add("https://www.dailydot.com/wp-content/uploads/c0e/55/b89c79206bf0c7ec5a13f7793f679e7d.jpg");
            imageLink.setText("https://www.dailydot.com/wp-content/uploads/c0e/55/b89c79206bf0c7ec5a13f7793f679e7d.jpg");
            listName.add("Get image inspiration from the internet");
        }
        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

    }

    private void requestImages(String imageSearch) {
        ImagesApi imagesApi = ServiceGenerator.getImagesApi();
        Call<ResultList> images = imagesApi.getImages("34fda7c5a984936ecaf16c5c00fdbd95d5978c39a08f5b908e57b596b5226fc0","30",imageSearch);
        images.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if (response.code() == 200) {
                    Log.i("Retrofit",response.body().getImages().get(0).getAltDescription() );
                    addImagesToGallery(response.body().getImages());
                }
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                t.printStackTrace();
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    @Override
    public void onSliderClick(BaseSliderView slider)
    {
        //TODO:Open new intent with full image path(will be necessary to create new variable)
        Toast.makeText(getContext(), slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }
    @Override
    public void onPageSelected(int position) {
        imageLink.setText(listUrl.get(position));
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}