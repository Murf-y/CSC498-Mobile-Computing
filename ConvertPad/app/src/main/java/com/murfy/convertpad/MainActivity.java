package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance(Locale.US);

    private boolean dollar_to_lbp = true;
    private TextView from_component;
    private TextView to_component;

    private EditText input;
    private TextView conversion_result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);
        super.onCreate(savedInstanceState);
        setTheme(isDarkTheme ? R.style.DarkTheme : R.style.LightTheme);
        setContentView(R.layout.activity_main);

        RadioGroup radio = findViewById(R.id.themeRadio);
        radio.setBackground(ContextCompat.getDrawable(getApplicationContext(),isDarkTheme ? R.drawable.ic_dark_thumb: R.drawable.ic_light_thumb));
    }
    public void changeTheme(View view){
        boolean isDarkTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkTheme", false);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isDarkTheme", !isDarkTheme).apply();
        recreate();
    }
    public void convert(View view){
        if(input == null) input = findViewById(R.id.numberInput);
        if(conversion_result_text == null) conversion_result_text = findViewById(R.id.conversionResultText);
        if(NUMBER_FORMATTER.getMaximumFractionDigits() != 2) NUMBER_FORMATTER.setMaximumFractionDigits(2);
        String number = input.getText().toString();


        float EXCHANGE_RATE = 40000;
        if(dollar_to_lbp){
            float number_value = Float.parseFloat(number);
            String lbp_equivalent = NUMBER_FORMATTER.format((int) (number_value * EXCHANGE_RATE));
            lbp_equivalent += " L.L.";
            conversion_result_text.setText(lbp_equivalent);
        }else{
            int number_value = (int) Float.parseFloat(number);
            String usd_equivalent = NUMBER_FORMATTER.format(number_value / EXCHANGE_RATE);
            usd_equivalent += " $";
            conversion_result_text.setText(usd_equivalent);
        }
        conversion_result_text.setVisibility(View.VISIBLE);
    }
    public void switchConversion(View view){
        if(from_component == null) from_component = findViewById(R.id.fromComponent);
        if(to_component == null) to_component = findViewById(R.id.toComponent);
        if(input == null) input = findViewById(R.id.numberInput);

        dollar_to_lbp = !dollar_to_lbp;
        input.setText("");
        from_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_usd_component : R.drawable.ic_lbp_component, 0, 0);
        to_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_lbp_component : R.drawable.ic_usd_component, 0, 0);
    }
}