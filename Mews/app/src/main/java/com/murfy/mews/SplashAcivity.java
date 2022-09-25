package com.murfy.mews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.murfy.mews.Utils.AnimationHelper;
import com.murfy.mews.Utils.Delayer;
import com.murfy.mews.databinding.ActivitySplashAcivityBinding;

import java.util.concurrent.Callable;

public class SplashAcivity extends AppCompatActivity {

    private ActivitySplashAcivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashAcivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AnimationHelper.getInstance().slideFromLeftToRight(binding.logo, 1000).fadeIn(binding.logo, 2000);
        AnimationHelper.getInstance().fadeIn(binding.text, 1000);

        Delayer.getInstance().postAfter(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

                return null;
            }
        }, 2000);

    }
}