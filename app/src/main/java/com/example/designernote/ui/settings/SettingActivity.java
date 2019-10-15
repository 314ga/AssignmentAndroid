package com.example.designernote.ui.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.designernote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SettingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageView mImageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private Button uploadImage;
    ImageView imageView;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;
    private String cacheImageNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        uploadImage = findViewById(R.id.uploadImage);
        mImageView = findViewById(R.id.profileImage);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openFileChooser();
            }
        });
        cacheImageNumber = getCacheImageNumber();
        showImage();
}
    private void openFileChooser()
    {
        // start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();
                uploadImageToFirebase(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        // Create a storage reference from app
        StorageReference storageRef = storage.getReference();
        // Create a reference
        String userName = user.getEmail();
        String imageName = userName.replaceAll("\\.", "_");
        imageName = imageName.replaceAll("@","-");
        StorageReference nameImagesRef = storageRef.child("ProfileImages/" + imageName +".jpg");

        UploadTask uploadTask;
        Uri file = imageUri;
        uploadTask = nameImagesRef.putFile(file);


        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(SettingActivity.this, "Problem with changing picture " , Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                updateCacheImageNumber();
                showImage();
                Toast.makeText(SettingActivity.this, "Picture successfully uploaded" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showImage()
    {
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String userName = user.getEmail();
        String imageName = userName.replaceAll("\\.", "_");
        imageName = imageName.replaceAll("@","-");
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://designernote-52e1e.appspot.com/ProfileImages/" + imageName +".jpg");

        ImageView imageView = findViewById(R.id.profileImage);

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)

        Glide.with(this /* context */)
                .load(ref)
                .signature(new ObjectKey(cacheImageNumber))
                .into(imageView);
    }

    private void updateCacheImageNumber()
    {
        String filename = "profPic";
        String fileContents;
        fileContents = getCacheImageNumber();
        if(!fileContents.equals(""))
        {
            int addOne = Integer.parseInt(fileContents);
            addOne = addOne+1;
            fileContents = "" + addOne;
        }

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String getCacheImageNumber()
    {
        String filename = "profPic";
        String fileContents = "0";

        Context ctx = getApplicationContext();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = ctx.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            fileContents = bufferedReader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;

    }

}
