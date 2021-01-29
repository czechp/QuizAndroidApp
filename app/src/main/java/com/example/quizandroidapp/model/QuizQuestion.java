package com.example.quizandroidapp.model;

import com.example.quizandroidapp.service.Observable;
import com.example.quizandroidapp.service.Observer;

public class QuizQuestion {
    private String question;
    private boolean answer;

    public QuizQuestion(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public QuizQuestion() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "question='" + question + '\'' +
                ", answer=" + answer +
                '}';
    }
}
