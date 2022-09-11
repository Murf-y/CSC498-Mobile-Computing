package com.murfy.convertpad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean dollar_to_lbp;
    private TextView from_component;
    private TextView to_component;

    private EditText input;
    private TextView conversion_result_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dollar_to_lbp = true;
        from_component = findViewById(R.id.fromComponent);
        to_component = findViewById(R.id.toComponent);
        input = findViewById(R.id.numberInput);
        conversion_result_text = findViewById(R.id.conversionResultText);
        setContentView(R.layout.activity_main);
    }

    public void convert(View view){
        String input_number_as_string = input.getText().toString();
    }
    public void switchConversion(View view){
        dollar_to_lbp = !dollar_to_lbp;
        from_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_usd_component : R.drawable.ic_lbp_component, 0, 0);
        to_component.setCompoundDrawablesWithIntrinsicBounds(0,dollar_to_lbp ? R.drawable.ic_lbp_component : R.drawable.ic_usd_component, 0, 0);
    }
}