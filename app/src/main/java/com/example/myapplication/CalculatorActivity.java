package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;

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

    // Append to the current expression
    private void appendToExpression(String value) {
        expression.append(value);
        calcText.setText(expression.toString());
    }

    // Evaluate the current expression
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

    // Evaluate the mathematical expression with operator precedence
    private double evaluateSimpleExpression(String expr) throws Exception {
        expr = expr.replace("x", "*");

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < expr.length()) {
            char currentChar = expr.charAt(i);

            if (currentChar == ' ') {
                i++;
                continue;
            }

            if (Character.isDigit(currentChar) || currentChar == '-') {
                StringBuilder sb = new StringBuilder();

                if (currentChar == '-') {
                    sb.append(currentChar);
                    i++;
                    currentChar = expr.charAt(i);
                }

                while (i < expr.length() && (Character.isDigit(currentChar) || currentChar == '.')) {
                    sb.append(currentChar);
                    i++;
                    if (i < expr.length()) {
                        currentChar = expr.charAt(i);
                    }
                }

                numbers.push(Double.parseDouble(sb.toString()));
                continue;
            }

            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                while (!operators.isEmpty() && hasPrecedence(currentChar, operators.peek())) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(currentChar);
            }

            i++;
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private double applyOperator(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new Exception("Division by zero");
                }
                return a / b;
        }
        return 0;
    }
}
