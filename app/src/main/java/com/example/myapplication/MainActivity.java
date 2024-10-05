package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // About Me button
        Button aboutMeButton = findViewById(R.id.button);

        // Set onClickListener
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display my name and email
                Toast.makeText(MainActivity.this, "Ziang Tang - tang.zia@northeastern.edu", Toast.LENGTH_LONG).show();
            }
        });

// assignment 3 button
        Button quickCalcButton = findViewById(R.id.button_quick_calc);
        quickCalcButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        });

    }
}