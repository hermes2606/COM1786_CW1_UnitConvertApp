package com.example.unitconvertapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Spinner fromUnitSpinner, toUnitSpinner;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.input_value);
        fromUnitSpinner = findViewById(R.id.from_unit_spinner);
        toUnitSpinner = findViewById(R.id.to_unit_spinner);
        convertButton = findViewById(R.id.convert_button);
        resultText = findViewById(R.id.result_text);

        // Cấu hình spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Meter", "Millimeter", "Mile", "Foot"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        // Xử lý nút bấm
        convertButton.setOnClickListener(v -> {
            String input = inputValue.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            double value = Double.parseDouble(input);
            String fromUnit = fromUnitSpinner.getSelectedItem().toString();
            String toUnit = toUnitSpinner.getSelectedItem().toString();

            double result = convertUnits(value, fromUnit, toUnit);
            resultText.setText(String.format("Result: %.3f %s", result, toUnit));
        });
    }

    private double convertUnits(double value, String fromUnit, String toUnit) {
        double meters = 0;

        // Chuyển đổi từ đơn vị nguồn sang mét
        switch (fromUnit) {
            case "Meter":
                meters = value;
                break;
            case "Millimeter":
                meters = value / 1000;
                break;
            case "Mile":
                meters = value * 1609.34;
                break;
            case "Foot":
                meters = value * 0.3048;
                break;
            default:
                throw new IllegalArgumentException("Invalid from unit");
        }

        // Chuyển đổi từ mét sang đơn vị đích
        double result = 0;
        switch (toUnit) {
            case "Meter":
                result = meters;
                break;
            case "Millimeter":
                result = meters * 1000;
                break;
            case "Mile":
                result = meters / 1609.34;
                break;
            case "Foot":
                result = meters / 0.3048;
                break;
            default:
                throw new IllegalArgumentException("Invalid to unit");
        }

        return result;
    }
}

