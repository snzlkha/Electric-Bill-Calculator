package com.example.electricbillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //declaration
    EditText etUnitUsed;
    EditText etRebate;
    Button btnCalc,btnClear, btnAboutPage;
    TextView tvCost,tvUsed200, tvUsed100, tvUsed300,
            tvRate200, tvRate100, tvRate300, tvTotal200,
            tvTotal100, tvTotal300, tvTotalUsed,tvTotal, tvTotalRebate, tvTotalCost;

    // Declare DecimalFormat object as an instance variable
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the decimalFormat object
        decimalFormat = new DecimalFormat("#.00");

        //set variable with ui component
        etUnitUsed = findViewById(R.id.etUnitUsed);
        etRebate = findViewById(R.id.etRebate);
        btnCalc = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        btnAboutPage = findViewById(R.id.btnAboutPage);
        tvCost = findViewById(R.id.tvCost);
        tvUsed200 = findViewById(R.id.tvUsed200);
        tvUsed100 = findViewById(R.id.tvUsed100);
        tvUsed300 = findViewById(R.id.tvUsed300);
        tvRate200 = findViewById(R.id.tvRate200);
        tvRate100 = findViewById(R.id.tvRate100);
        tvRate300 = findViewById(R.id.tvRate300);
        tvTotal200 = findViewById(R.id.tvTotal200);
        tvTotal100 = findViewById(R.id.tvTotal100);
        tvTotal300 = findViewById(R.id.tvTotal300);
        tvTotalRebate = findViewById(R.id.tvTotalRebate);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        tvTotalUsed = findViewById(R.id.tvTotalUsed);
        tvTotal = findViewById(R.id.tvTotal);

        //listener (action)
        btnCalc.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnAboutPage.setOnClickListener(view -> openAboutPage());
    }
    public void openAboutPage(){
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCalculate) {
            try {
                String variable = etUnitUsed.getText().toString();
                String variable2 = etRebate.getText().toString();

                int unit = Integer.parseInt(variable);
                double rebate = Double.parseDouble(variable2) / 100;

                double first200 = 0.218;
                double next100 = 0.334;
                double next300 = 0.516;
                double moreThan700 = 0.546;

                double charges = calculateCharges(unit, first200, next100, next300, moreThan700);

                double finalCost = calculateFinalCost(charges, rebate);
                tvCost.setText("RM " + decimalFormat.format(finalCost));
                tvTotalCost.setText("Total Cost: RM " + decimalFormat.format(finalCost));

                // Update the values in the table layout
                tvUsed200.setText(decimalFormat.format(Math.min(unit, 200)));
                int max = Math.max(Math.min(unit - 200, 100), 0);
                tvUsed100.setText(String.valueOf(max));
                int i = Math.max(Math.min(unit - 300, 300), 0);
                tvUsed300.setText(String.valueOf(i));

                tvRate200.setText(String.valueOf(first200));
                tvRate100.setText(String.valueOf(next100));
                tvRate300.setText(String.valueOf(next300));

                tvTotal200.setText(String.valueOf(Math.min(unit, 200) * first200));
                tvTotal100.setText(decimalFormat.format(max * next100));
                tvTotal300.setText(String.valueOf(i * next300));

                // Calculate and set the total usage and total charges
                int totalUsed = Math.min(unit, 200) + max + i;

                double totalRebate = charges * rebate;
                double totalCost = charges;

                tvTotalUsed.setText(String.valueOf(totalUsed));
                tvTotalRebate.setText("Total Rebate: RM " + decimalFormat.format(totalRebate));
                tvTotal.setText(String.valueOf(totalCost));

            } catch (NumberFormatException nfe) {
                showToast("Please Enter a Valid Number");
            } catch (Exception exp) {
                showToast("Unknown Exception");
                Log.d("Exception", exp.getMessage());
            }
        }else if (v.getId() == R.id.btnClear) {
            //todo
            etUnitUsed.setText("");
            etRebate.setText("");
            Toast.makeText(this, "Clear Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateCharges(int unit, double first200, double next100, double next300, double moreThan700) {
        double charges = 0.0;

        if (unit <= 200) {
            charges = unit * first200;
        } else if (unit <= 300) {
            charges = (200 * first200) + ((unit - 200) * next100);
        } else if (unit <= 600) {
            charges = (200 * first200) + (100 * next100) + ((unit - 300) * next300);
        } else if (unit > 600) {
            charges = (200 * first200) + (100 * next100) + (300 * next300) + ((unit - 600) * moreThan700);
        }
        // Limiting charges to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String roundedChargesStr = decimalFormat.format(charges);
        return Double.parseDouble(roundedChargesStr);
    }

    private double calculateFinalCost(double charges, double rebate) {
        double finalCost = charges - (charges * rebate);

        // Limiting final cost to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String roundedFinalCostStr = decimalFormat.format(finalCost);
        return Double.parseDouble(roundedFinalCostStr);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}