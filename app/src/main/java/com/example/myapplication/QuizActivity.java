package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import DB.Pergunta;
import DB.PerguntasDB;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private Button opcao1BTN;
    private Button opcao2BTN;
    private Button opcao3BTN;
    private int correctAwnser;
    private Pergunta pergunta;
    private Activity act;
    private Boolean real;
    private Intent intent;
    private float currentTimer;
    private FirebaseUser user;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        user = FirebaseAuth.getInstance().getCurrentUser();

        act = this;

        intent = getIntent();
        pergunta = (Pergunta) intent.getSerializableExtra("Pergunta");
        real = intent.getBooleanExtra("real",false);
        correctAwnser = pergunta.opcoes.indexOf(pergunta.respostaCorreta);
        ((TextView)findViewById(R.id.textViewPergunta)).setText(pergunta.pergunta);

        opcao1BTN = findViewById(R.id.textViewOpcao1);
        opcao1BTN.setText(pergunta.opcoes.get(0));
        opcao1BTN.setOnClickListener(this);
        opcao2BTN = findViewById(R.id.textViewOpcao2);
        opcao2BTN.setText(pergunta.opcoes.get(1));
        opcao2BTN.setOnClickListener(this);
        opcao3BTN = findViewById(R.id.textViewOpcao3);
        opcao3BTN.setText(pergunta.opcoes.get(2));
        opcao3BTN.setOnClickListener(this);

        final ProgressBar mProgressBar;

        mProgressBar= findViewById(R.id.progressbar);
        mProgressBar.setProgress(0);
        mCountDownTimer=new CountDownTimer(30000,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                int milisPassed = (int)Math.abs(millisUntilFinished - 30000);
                currentTimer = (float) (milisPassed/30000.0);
                mProgressBar.setProgress(Math.round(currentTimer*100));
            }

            @Override
            public void onFinish() {
                mProgressBar.setProgress(100);
                pergunta.acertou = false;
                HandleQuizzResponse(pergunta);
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == opcao1BTN.getId()){
            if (correctAwnser == 0){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            pergunta.respostaUser = pergunta.opcoes.get(0);
            HandleQuizzResponse(pergunta);
        } else if (view.getId() == opcao2BTN.getId()){
            if (correctAwnser == 1){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            pergunta.respostaUser = pergunta.opcoes.get(1);
            HandleQuizzResponse(pergunta);
        } else if (view.getId() == opcao3BTN.getId()){
            if (correctAwnser == 2){
                pergunta.acertou = true;
            } else {
                pergunta.acertou = false;
            }
            pergunta.respostaUser = pergunta.opcoes.get(2);
            HandleQuizzResponse(pergunta);
        }
    }

    private void HandleQuizzResponse(final Pergunta pergunta){
        mCountDownTimer.cancel();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                String message;

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference documentReference = db.collection("users").document(user.getEmail());

                if(pergunta.acertou){
                    message = "Acertou";
                    int pontos = pergunta.pontos;
                    if(currentTimer > 0.5 && currentTimer < 0.75){
                        pontos = Math.round(pontos * 0.7f);
                    } else if(currentTimer >= 0.75) {
                        pontos = Math.round(pontos * 0.5f);
                    }
                    if (real) {
                        documentReference
                                .update("pontos", FieldValue.increment(pontos));
                        documentReference
                                .update("numRespostasCorretas", FieldValue.increment(1));
                    }
                } else {
                    message = "Errou";
                    if (real) {
                        documentReference
                                .update("numRespostasErradas", FieldValue.increment(1));
                    }
                }

                new AlertDialog.Builder(act)
                        .setTitle("Resultado do Quizz")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                act.finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                if (real) {
                    PerguntasDB.getInstance().perguntasDAO().updatePergunta(pergunta);
                }
                return null;
            }
        }.execute();
    }
    // Impede que seja possivel retroceder da activity manualmente
    @Override
    public void onBackPressed() {
    }
}