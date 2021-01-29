package com.example.quizandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quizandroidapp.service.Observer;
import com.example.quizandroidapp.service.QuizService;

public class QuizActivity extends AppCompatActivity implements Observer {

    private QuizService quizService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.quizService = new QuizService(getApplicationContext());
        quizService.attach(this::update);
        quizService.start();
    }

    @Override
    public void update() {

    }
}