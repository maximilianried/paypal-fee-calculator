package com.maximilianried.feecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.maximilianried.feecalculator.models.FeeModel;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    // Get view elements
    TextView amountWithFeesInfoTv;
    TextView feeTv;
    TextView amountAfterFeesTv;
    TextView amountWithFeesTv;

    // Fee variables
    double standardPercent = 2.49;
    double donatePercent = 1.5;
    double microPercent = 10;
    double merchantSmallCap = 1.99;
    double merchantHighCap = 1.79;
    double microFix = 0.10;
    double standardFix = 0.35;

    String paymentType;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Show menu back button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Find view elements
        feeTv = findViewById(R.id.feesTv);
        amountAfterFeesTv = findViewById(R.id.amountAfterFeesTv);
        amountWithFeesTv = findViewById(R.id.amountWithFeesTv);
        amountWithFeesInfoTv = findViewById(R.id.amountWithFeesInfoTv);

        // Get selections from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            amount = Double.parseDouble(extras.getString("amount"));
            paymentType = extras.getString("paymentType");
        }

        // Create FeeModel with calculation
        FeeModel model = feeType(amount, paymentType);

        // Change view elements to results from model
        feeTv.setText("-" + formatter(model.getFee()) + "€");
        amountAfterFeesTv.setText(formatter(model.getAmountAfterFees()) + "€");
        amountWithFeesTv.setText(formatter(model.getAmountWithFees()) + "€");
        amountWithFeesInfoTv.setText("Wenn du " + formatter(model.getAmountWithFees()) + "€ sendest werden " + formatter(amount) + "€ beim Empfänger ankommen.");
    }

    // Determine payment type and return results in FeeModel object
    public FeeModel feeType(double amount, String paymentType) {
        switch (paymentType) {
            case "Ware oder Dienstleistung":
                return calcFees(amount, standardPercent, standardFix);
            case "Freunde und Familie":
                FeeModel model = new FeeModel();
                model.setFee(0);
                model.setAmountAfterFees(amount);
                model.setAmountWithFees(amount);
                return model;
            case "Spenden":
                return calcFees(amount, donatePercent, standardFix);
            case "Mikrozahlung":
                return calcFees(amount, microPercent, microFix);
            case "Händlerkonditionen 5.001€ - 25.000€":
                return calcFees(amount, merchantSmallCap, standardFix);
            case "Händlerkonditionen über 25.000€":
                return calcFees(amount, merchantHighCap, standardFix);
            default:
                throw new NoSuchElementException("Fee type " + paymentType + " invalid");
        }
    }

    // Calculate fees and return FeeModel object
    public FeeModel calcFees(double amount, double feePercent, double feeFix) {
        FeeModel model = new FeeModel();
        model.setFee(amount * feePercent / 100 + feeFix);
        model.setAmountAfterFees(amount - (amount * feePercent / 100 + feeFix));
        model.setAmountWithFees(((amount + feeFix) / (100 - feePercent)) * 100);
        return model;
    }

    // Trim results into two digit numbers with comma
    public String formatter(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value).replace(".", ",");
    }
}