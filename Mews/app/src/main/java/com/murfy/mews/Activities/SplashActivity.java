package com.murfy.mews.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.Utils.AnimationHelper;
import com.murfy.mews.Utils.Delayer;
import com.murfy.mews.databinding.ActivitySplashActivityBinding;

public class SplashActivity extends AppCompatActivity {

    final int SPLASH_SCREEN_DURATION_IN_MILLISECONDS = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashActivityBinding binding = ActivitySplashActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        AnimationHelper.getInstance().scaleUp(binding.logo, 1500).fadeIn(binding.logo, 2000);

        Delayer.getInstance().postAfter(() -> {
            AnimationHelper.getInstance().widenX(binding.logo, 1000).fadeOut(binding.logo, 900);
            Delayer.getInstance().postAfter(() -> {
                int drawableID = getResources().getIdentifier("ic_logo", "drawable", getPackageName());
                binding.logo.setImageResource(drawableID);
                AnimationHelper.getInstance().unwidenX(binding.logo, 1000).fadeIn(binding.logo, 600);
                return null;
            }, 1200);
            return null;
        }, 2100);

        AnimationHelper.getInstance().fadeIn(binding.text, 3000);

        DatabaseService.getInstance(getApplicationContext());

        Delayer.getInstance().postAfter(() -> {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            return null;
        }, SPLASH_SCREEN_DURATION_IN_MILLISECONDS);

    }
}