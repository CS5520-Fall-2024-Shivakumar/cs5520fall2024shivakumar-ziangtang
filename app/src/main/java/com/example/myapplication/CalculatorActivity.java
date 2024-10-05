package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    private TextView calcText;
    private StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        calcText = findViewById(R.id.text_calc);

        // Assign onClickListeners for all buttons
        setupNumberButtons();
        setupOperatorButtons();
        setupSpecialButtons();
    }


    private void setupNumberButtons() {
        // Number 0-9
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> appendToExpression(((Button) v).getText().toString()));
        }
    }

    private void setupOperatorButtons() {
        // Operator (+, -, x)
        int[] operatorButtonIds = {
                R.id.button_plus, R.id.button_minus, R.id.button_multiply
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> appendToExpression(((Button) v).getText().toString()));
        }
    }

    private void setupSpecialButtons() {
        // delete button
        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(v -> {
            if (expression.length() > 0) {
                expression.deleteCharAt(expression.length() - 1);
                calcText.setText(expression.toString());
            }
        });

        // Equals button
        Button equalsButton = findViewById(R.id.button_equals);
        equalsButton.setOnClickListener(v -> evaluateExpression());
    }

    // calculator core
    private void appendToExpression(String value) {
        expression.append(value);
        calcText.setText(expression.toString());
    }

    // evaluate the current expression
    private void evaluateExpression() {
        try {
            double result = evaluateSimpleExpression(expression.toString());
            calcText.setText(String.valueOf(result));
            expression.setLength(0);
        } catch (Exception e) {
            calcText.setText("Error");
            expression.setLength(0);
        }
    }

    // This is a simple calculator
    private double evaluateSimpleExpression(String expr) throws Exception {

        if (expr.contains("+")) {
            String[] operands = expr.split("\\+");
            return Double.parseDouble(operands[0]) + Double.parseDouble(operands[1]);
        } else if (expr.contains("-")) {
            String[] operands = expr.split("-");
            return Double.parseDouble(operands[0]) - Double.parseDouble(operands[1]);
        } else if (expr.contains("x")) {
            String[] operands = expr.split("x");
            return Double.parseDouble(operands[0]) * Double.parseDouble(operands[1]);
        } else {
            throw new Exception("Invalid Expression");
        }
    }
}
