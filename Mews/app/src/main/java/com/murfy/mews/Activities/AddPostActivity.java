package com.murfy.mews.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.murfy.mews.Models.Post;
import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.Utils.AnimationHelper;
import com.murfy.mews.Utils.Delayer;
import com.murfy.mews.databinding.ActivityAddPostBinding;

import java.io.IOException;
import java.util.concurrent.Callable;

public class AddPostActivity extends AppCompatActivity {

    ActivityAddPostBinding binding;
    User current_user;
    boolean adding_post = false;
    Bitmap imageBitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);


        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null) {
                            Uri imageUri = result.getData() != null ? result.getData().getData() : null;
                            try {
                                imageBitMap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                                binding.imagePreview.setVisibility(View.VISIBLE);
                                binding.addImageButton.setVisibility(View.GONE);
                                binding.addImageLabel.setVisibility(View.GONE);
                                binding.imagePreview.setImageURI(imageUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        current_user = (User) getIntent().getSerializableExtra("user");

        binding.postButton.setOnClickListener(view -> {
            submitPost();
        });
        binding.cancelButton.setOnClickListener(view -> {
            finish();
        });
        binding.addImageButton.setOnClickListener(view -> {
            Intent pickIntent = new Intent();
            pickIntent.setType("image/*");
            pickIntent.setAction(Intent.ACTION_GET_CONTENT);

            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String pickTitle = "Select or take a new Picture";
            Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
            chooserIntent.putExtra
                    (
                            Intent.EXTRA_INITIAL_INTENTS,
                            new Intent[] { takePhotoIntent }
                    );

            imagePickerActivityResult.launch(pickIntent);
        });
    }

    private void submitPost() {
        String title = binding.postTitleInput.getText().toString();
        String content = binding.postContentInput.getText().toString();
        String location = binding.postLocationInput.getText().toString();

        if(title.trim().length() == 0){
            AnimationHelper.getInstance()
                    .slideFromLeftToRight(binding.titleError, 200)
                    .fadeIn(binding.titleError, 300);
        }
        else if(content.trim().length() == 0){
            binding.titleError.setAlpha(0);
            AnimationHelper.getInstance()
                    .slideFromLeftToRight(binding.contentError, 200)
                    .fadeIn(binding.contentError, 300);
        } else if(!adding_post){
            binding.titleError.setAlpha(0);
            binding.contentError.setAlpha(0);
            binding.locationError.setAlpha(0);
            DatabaseService.getInstance(getApplicationContext()).addPostToDb(current_user, title, content, location, imageBitMap);
            adding_post = true;

            Delayer.getInstance().postAfter(() -> {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("user", current_user);
                startActivity(i);
                return null;
            }, 1000);
        }

    }
}