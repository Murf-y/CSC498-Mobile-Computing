package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean dollar_to_lbp = true;

    private TextView from_component;
    private TextView to_component;

    private EditText input;
    private TextView conversion_result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convert(View view){
        if(input == null) input = findViewById(R.id.numberInput);
        if(conversion_result_text == null) conversion_result_text = findViewById(R.id.conversionResultText);

        String number = input.getText().toString();
        conversion_result_text.setText(number);
        conversion_result_text.setVisibility(View.VISIBLE);
    }
    public void switchConversion(View view){
        if(from_component == null) from_component = findViewById(R.id.fromComponent);
        if(to_component == null) to_component = findViewById(R.id.toComponent);
        dollar_to_lbp = !dollar_to_lbp;
        from_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_usd_component : R.drawable.ic_lbp_component, 0, 0);
        to_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_lbp_component : R.drawable.ic_usd_component, 0, 0);
    }
}