package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);
        super.onCreate(savedInstanceState);
        setTheme(isDarkTheme ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_login);

        RadioGroup radio = findViewById(R.id.themeRadio);
        radio.setBackground(ContextCompat.getDrawable(getApplicationContext(),isDarkTheme ? R.drawable.ic_dark_thumb: R.drawable.ic_light_thumb));
    }

    public void changeTheme(View view){
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isDarkTheme", !isDarkTheme).apply();
        recreate();
    }
    public void login(View view){
        EditText email_input = findViewById(R.id.emailInput);
        EditText password_input = findViewById(R.id.passwordInput);

        String email_value = email_input.getText().toString();
        String password_value = password_input.getText().toString();

        if(Utils.isUser(email_value, password_value)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            email_input.setText("");
            password_input.setText("");
            TextView errorText = findViewById(R.id.errorTextView);
            Utils.showAndHideView(errorText);
        }
    }
}