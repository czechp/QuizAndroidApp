package com.example.quizandroidapp.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizandroidapp.model.QuizQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QuizService implements Observable {
    private static final String URL = "https://opentdb.com/api.php?amount=10&category=18&type=boolean";
    private List<QuizQuestion> questions = new ArrayList<>();
    private RequestQueue requestQueue;
    private HashSet<Observer> observers = new HashSet<>();
    private Context context;

    public QuizService(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }

    public void start() {
        JsonObjectRequest questionRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, "Questions retrieve from server", Toast.LENGTH_LONG)
                                    .show();
                            questions = mapResponseToQuestionsList(response);
                            notifyObservers();
                        } catch (JSONException e) {
                            Log.e("ParsingError", "Error during parsing questions");
                            Toast.makeText(context, "Error during parsing questions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpRequestError", "Error during fetching questions from API");
                        Toast.makeText(context, "Error during fetching questions from APi", Toast.LENGTH_LONG)
                                .show();
                    }
                });
        requestQueue.add(questionRequest);
    }

    private List<QuizQuestion> mapResponseToQuestionsList(JSONObject response) throws JSONException {
        ArrayList<QuizQuestion> result = new ArrayList<>();
        JSONArray jsonQuestions = response.getJSONArray("results");
        for (int i = 0; i < jsonQuestions.length(); i++) {
            result.add(
                    new QuizQuestion(
                            jsonQuestions.getJSONObject(i).getString("question"),
                            jsonQuestions.getJSONObject(i).getBoolean("correct_answer")
                    )
            );
        }
        return result;
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        if (observers.contains(observer))
            observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.stream()
                .forEach(observer -> observer.update());
    }
}
