package com.example.quizandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int result = getIntent().getIntExtra("result", 0);
        TextView txtResult = findViewById(R.id.txtResult);
        txtResult.setText("Your result is: " + result);
    }
}