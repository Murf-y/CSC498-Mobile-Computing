package com.murfy.mews.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.murfy.mews.Models.User;
import com.murfy.mews.R;
import com.murfy.mews.Services.DatabaseService;
import com.murfy.mews.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {


    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.pink, this.getTheme()));

        binding.loginButton.setOnClickListener(view -> {
            EditText input = binding.userNameInput;
            String user_name = input.getText().toString();
            login(user_name);
        });
        setContentView(rootView);
    }

    public void login(String user_name) {
        User user = DatabaseService.getInstance(getApplicationContext()).createOrGetUser(user_name);

        if(user == null){
            recreate();
        }
        else{
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        }
    }
}