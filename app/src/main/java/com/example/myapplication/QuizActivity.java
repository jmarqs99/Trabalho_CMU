package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import DB.Pergunta;
import DB.PerguntasDB;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private Button opcao1BTN;
    private Button opcao2BTN;
    private Button opcao3BTN;
    private int correctAwnser;
    private Pergunta pergunta;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        act = this;
        Intent intent = getIntent();
        pergunta = (Pergunta) intent.getSerializableExtra("Pergunta");
        correctAwnser = pergunta.pergunta.indexOf(pergunta.respostaCorreta);

        ((TextView)findViewById(R.id.textViewPergunta)).setText(pergunta.pergunta);

        opcao1BTN = findViewById(R.id.textViewOpcao1);
        opcao1BTN.setText(pergunta.opcoes.get(0));
        opcao1BTN.setOnClickListener(this);
        opcao2BTN = findViewById(R.id.textViewOpcao1);
        opcao2BTN.setText(pergunta.opcoes.get(1));
        opcao2BTN.setOnClickListener(this);
        opcao3BTN = findViewById(R.id.textViewOpcao1);
        opcao3BTN.setText(pergunta.opcoes.get(2));
        opcao3BTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO: ADICIONAR PONTOS NO FIRESTORE EM CASO DE ACERTO
        if (view.getId() == opcao1BTN.getId()){
            if (correctAwnser == 0){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    act.finishActivity(1);
                    Log.d("Quizact","jere");
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    pergunta.respostaUser = pergunta.opcoes.get(0);
                    PerguntasDB.getInstance().perguntasDAO().updatePergunta(pergunta);
                    return null;
                }
            }.execute();
        } else if (view.getId() == opcao2BTN.getId()){
            if (correctAwnser == 1){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    act.finishActivity(1);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    pergunta.respostaUser = pergunta.opcoes.get(1);
                    PerguntasDB.getInstance().perguntasDAO().updatePergunta(pergunta);
                    return null;
                }
            }.execute();
        } else if (view.getId() == opcao3BTN.getId()){
            if (correctAwnser == 2){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    act.finishActivity(1);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    pergunta.respostaUser = pergunta.opcoes.get(2);
                    PerguntasDB.getInstance().perguntasDAO().updatePergunta(pergunta);
                    return null;
                }
            }.execute();
        }
    }
}