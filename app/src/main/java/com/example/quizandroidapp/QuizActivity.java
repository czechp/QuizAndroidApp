package com.example.quizandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizandroidapp.model.QuizQuestion;
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
        assignQuestionContent(quizService.getNextQuestion());
    }

    public void onClickTrueButton(View view) {
        checkCorrectAnswer(true);
    }

    public void onClickFalseButton(View view) {
        checkCorrectAnswer(false);
    }

    private void checkCorrectAnswer(boolean answer) {
        showToastResult(quizService.isCorrect(answer));
        quizService.checkAnswer(answer);
        if (quizService.quizFinished()) {
            Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
            resultIntent.putExtra("result", quizService.getResult());
            startActivity(resultIntent);
        } else {
            assignQuestionContent(quizService.getNextQuestion());
        }
    }

    private void showToastResult(boolean correct) {
        String result = correct ? "Correct":"Incorrect";
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
                .show();

    }

    private void assignQuestionContent(QuizQuestion quizQuestion) {
        TextView txtQuestionContent = findViewById(R.id.txtQuestionContent);
        txtQuestionContent.setText(quizQuestion.getQuestion());
    }
}