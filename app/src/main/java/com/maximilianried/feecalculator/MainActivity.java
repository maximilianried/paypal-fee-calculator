package com.maximilianried.feecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Get view elements
    TextView amountTv;
    Spinner paymentTypeSpinner;
    Button calcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get elements from view
        amountTv = findViewById(R.id.amountTv);
        paymentTypeSpinner = findViewById(R.id.paymentTypeSpinner);
        calcBtn = findViewById(R.id.calcBtn);

        // On calculate button tap
        calcBtn.setOnClickListener(v -> {

            // Determine user input existence
            if (!amountTv.getText().toString().equals("")) {
                // Get values from view
                String amount = amountTv.getText().toString().replace(',', '.');
                String paymentType = paymentTypeSpinner.getSelectedItem().toString();

                // Intent management
                Intent activityResult = new Intent(MainActivity.this, ResultActivity.class);

                // Give inputs to ResultActivity
                activityResult.putExtra("amount", amount);
                activityResult.putExtra("paymentType", paymentType);

                startActivity(activityResult);
            } else {
                // Create a Toast with a warning if no input was found
                Toast.makeText(MainActivity.this, "Bitte Betrag eingeben", Toast.LENGTH_SHORT).show();
            }
        });
    }
}