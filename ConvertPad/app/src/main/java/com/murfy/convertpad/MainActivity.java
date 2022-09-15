package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance(Locale.US);

    private boolean dollar_to_lbp = true;
    private TextView from_component;
    private TextView to_component;

    private EditText input;
    private TextView conversion_result_text;

    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get whether it is darkTheme from sharedPrefrences
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);


        super.onCreate(savedInstanceState);
        setTheme(isDarkTheme ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_main);

        // Change the theme radio button background to match the theme
        RadioGroup radio = findViewById(R.id.themeRadio);
        radio.setBackground(ContextCompat.getDrawable(getApplicationContext(),isDarkTheme ? R.drawable.ic_dark_thumb: R.drawable.ic_light_thumb));

        // Initialise all Views used
        input = findViewById(R.id.numberInput);
        conversion_result_text = findViewById(R.id.conversionResultText);
        from_component = findViewById(R.id.fromComponent);
        to_component = findViewById(R.id.toComponent);

    }

    // Bind to theme radio button to change the theme and save the change in sharedPrefrences
    public void changeTheme(View view){
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isDarkTheme", !isDarkTheme).apply();
        recreate();
    }

    // Bind to CONVERT button to do the currency conversion and show the formatted result
    public void convert(View view){
        if(NUMBER_FORMATTER.getMaximumFractionDigits() != 2) NUMBER_FORMATTER.setMaximumFractionDigits(2);
        String number = input.getText().toString();

        if(!Utils.isNumeric(number)) return;

        float EXCHANGE_RATE = 40000;
        if(dollar_to_lbp){
            BigDecimal number_value = BigDecimal.valueOf(Double.parseDouble(number));
            String lbp_equivalent = NUMBER_FORMATTER.format((number_value.multiply(BigDecimal.valueOf(EXCHANGE_RATE))));
            lbp_equivalent += " L.L.";
            conversion_result_text.setText(lbp_equivalent);
        }else{
            BigInteger number_value = BigInteger.valueOf((long) Double.parseDouble(number));
            String usd_equivalent = NUMBER_FORMATTER.format(number_value.divide(BigInteger.valueOf((long) EXCHANGE_RATE)));
            usd_equivalent += " $";
            conversion_result_text.setText(usd_equivalent);
        }
        conversion_result_text.setVisibility(View.VISIBLE);

        if(inputMethodManager == null) inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
    }

    // Switch Components between USD and LBP
    public void switchConversion(View view){
        dollar_to_lbp = !dollar_to_lbp;
        input.setText("");

        Utils.hideAndShowView(from_component, new Callable<Void>() {
            public Void call(){
                from_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_usd_component : R.drawable.ic_lbp_component, 0, 0);
                return null;
            }
        });
        Utils.hideAndShowView(to_component, new Callable<Void>() {
            public Void call(){
                to_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_lbp_component : R.drawable.ic_usd_component, 0, 0);
                return null;
            }
        });
    }
}