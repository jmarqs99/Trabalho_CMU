package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.Models_Marcadores.Marcador;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.JogoAdapter;
import com.example.myapplication.RecyclerView.Jogo_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import DB.Pergunta;
import DB.PerguntasDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriarPergunta {

    public static void gerarNovaPergunta() {
        new Thread() {
            @Override
            public void run() {
                int size = 0;
                String posicao = "";
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                try {
                    size = size(db);
                    final int min = 1;
                    final int max = size;
                    final int random = new Random().nextInt((max - min) + 1) + min;
                    posicao = String.valueOf(random);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                DocumentReference docRef = db.collection("perguntas_default").document(posicao);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                final Pergunta pergunta = document.toObject(Pergunta.class);

                                new AsyncTask<Void,Void,Void>(){

                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        PerguntasDB.getInstance().perguntasDAO().addPergunta(pergunta);
                                        return null;
                                    }
                                }.execute();

                            } else {
                                Log.d("FIRESTORE", "No such document");
                            }
                        } else {
                            Log.d("FIRESTORE", "get failed with ", task.getException());
                        }
                    }
                });
            }
        }.start();
    }


    public static void gerarPerguntaDinamica(){
        final SportsDataAPI service = RetrofitClient.getApi();
        final Pergunta pergunta = new Pergunta();
        final Pergunta pergunta1 = new Pergunta();
        pergunta.pergunta = "Quantos golos tem o atual melhor marcador da Bundesliga?";
        pergunta.pontos = 100;
        pergunta1.pergunta = "Quem é o melhor marcador da Bundesliga?";
        service.getMarcadores()
                .enqueue(new Callback<Marcador>(){
                    @Override
                    public void onResponse(Call<Marcador> call, Response<Marcador> response) {
                        final Marcador marcador = response.body();
                        if(response.code() == 200){
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    List<String> opcoes = new ArrayList<>();
                                    int golos = marcador.getData().get(0).getGoals().getOverall();
                                    opcoes.set(0 ,marcador.getData().get(2).getGoals().getOverall() + "");
                                    opcoes.set(1, golos + "");
                                    opcoes.set(2 ,marcador.getData().get(6).getGoals().getOverall() + "");
                                    pergunta.respostaCorreta = golos + "";
                                    return null;
                                }
                            }.execute();
                        }
                    }
                    @Override
                    public void onFailure(Call<Marcador> call, Throwable t) {
                        Log.d("Error",t.toString());
                    }
                });
    }

    private static int size(FirebaseFirestore db) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = db.collection("perguntas_default").get();
        QuerySnapshot doc = Tasks.await(task);
        return doc.size();
    }

}
