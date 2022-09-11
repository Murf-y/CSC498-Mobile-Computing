package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText email_input = findViewById(R.id.emailInput);
        EditText password_input = findViewById(R.id.passwordInput);

        String email_value = email_input.getText().toString();
        String password_value = password_input.getText().toString();

        if(email_value.equals("user.lb") && password_value.equals("1234")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            TextView errorText = findViewById(R.id.errorTextView);
            email_input.setText("");
            password_input.setText("");
            errorText.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    errorText.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }
}