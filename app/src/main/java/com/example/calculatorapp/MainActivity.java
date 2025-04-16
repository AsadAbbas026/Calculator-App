package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText display;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAdd, btnSub, btnMul, btnDiv, btnEq, btnC, btnDel;
    String currentInput = "";
    boolean isCalculationDone = false; // Flag to track calculation status
    boolean justEvaluated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Number Buttons
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        // Operator Buttons
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        // Other Buttons
        btnEq = findViewById(R.id.btnEq);
        btnC = findViewById(R.id.btnC);
        btnDel = findViewById(R.id.btnDel);

        // Setting onClick listeners for number buttons
        btn0.setOnClickListener(view -> updateDisplay("0"));
        btn1.setOnClickListener(view -> updateDisplay("1"));
        btn2.setOnClickListener(view -> updateDisplay("2"));
        btn3.setOnClickListener(view -> updateDisplay("3"));
        btn4.setOnClickListener(view -> updateDisplay("4"));
        btn5.setOnClickListener(view -> updateDisplay("5"));
        btn6.setOnClickListener(view -> updateDisplay("6"));
        btn7.setOnClickListener(view -> updateDisplay("7"));
        btn8.setOnClickListener(view -> updateDisplay("8"));
        btn9.setOnClickListener(view -> updateDisplay("9"));

        // Setting onClick listeners for operator buttons
        btnAdd.setOnClickListener(view -> updateDisplay("+"));
        btnSub.setOnClickListener(view -> updateDisplay("-"));
        btnMul.setOnClickListener(view -> updateDisplay("×"));
        btnDiv.setOnClickListener(view -> updateDisplay("÷"));

        // Setting onClick listeners for special buttons
        btnEq.setOnClickListener(view -> calculateResult());
        btnC.setOnClickListener(view -> clearAll());
        btnDel.setOnClickListener(view -> deleteLast());
    }

    // Update the display
    void updateDisplay(String input) {
        if (justEvaluated) {
            if (input.matches("[0-9]")) {
                // If number is clicked after evaluation, reset
                currentInput = input;
            } else {
                // If operator is clicked, continue from result
                currentInput += input;
            }
            justEvaluated = false;
        } else {
            currentInput += input;
        }
        display.setText(currentInput);
    }

    // Calculate result using the custom method
    void calculateResult() {
        // Basic calculation logic (you can enhance this as needed)
        currentInput = currentInput.replace("×", "*").replace("÷", "/");
        try {
            double result = calculateBasicExpression(currentInput);
            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result); // Update the input to the result
            isCalculationDone = true; // Mark that calculation is done
        } catch (Exception e) {
            display.setText("Error");
        }
    }

    // Clear the entire display
    void clearAll() {
        currentInput = "";
        display.setText(currentInput);
        isCalculationDone = false; // Reset the calculation flag
    }

    // Delete the last character
    void deleteLast() {
        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            display.setText(currentInput);
        }
    }

    // Calculate basic arithmetic expression
    public double calculateBasicExpression(String expression) {
        double result = 0.0;
        // Removing any spaces for better parsing
        expression = expression.replaceAll("\\s+", "");
        // If the expression has no operator, return as is
        if (!expression.contains("+") && !expression.contains("-") && !expression.contains("*") && !expression.contains("/")) {
            return Double.parseDouble(expression);
        }

        try {
            // Split by operators and parse operands
            String[] parts = expression.split("(?=[+\\-*/])|(?<=[+\\-*/])");
            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    break;
            }
        } catch (Exception e) {
            return Double.NaN; // Error
        }
        return result;
    }
}
