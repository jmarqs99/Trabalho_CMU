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

    /**
     * Função para atribuir aleatoriamente uma pergunta ao user
     */
    public static void gerarNovaPergunta() {
        int random = new Random().nextInt(1);

        if (random == 0) {
            gerarPerguntaEstatica();
        } else {
            gerarPerguntaDinamica();
        }
    }

    /**
     * Atribiu uma pergunta presente no firestore
     */
    private static void gerarPerguntaEstatica() {
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

                                new AsyncTask<Void, Void, Void>() {

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

    /**
     * Atribiu uma pergunta gerada com dados obtidos pela api
     */
    private static void gerarPerguntaDinamica() {
        final SportsDataAPI service = RetrofitClient.getApi();
        final int min = 1;
        final int max = 4;
        // Valor aleatorio para escolher pergunta dinamica
        final int random2 = new Random().nextInt((max - min) + 1) + min;

        final Pergunta pergunta = new Pergunta();

        service.getMarcadores()
                .enqueue(new Callback<Marcador>() {
                    @Override
                    public void onResponse(Call<Marcador> call, Response<Marcador> response) {
                        final Marcador marcador = response.body();
                        if (response.code() == 200) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    List<String> opcoes = new ArrayList<>();
                                    // Criar perguntas e atrbuir resposta e opções com dados
                                    // retornados pela api
                                    switch (random2) {
                                        case 1:
                                            pergunta.pergunta = "Quantos golos tem o atual melhor marcador da Bundesliga?";
                                            pergunta.pontos = 100;
                                            int resposta = marcador.getData().get(0).getGoals().getOverall();
                                            opcoes.add(marcador.getData().get(2).getGoals().getOverall() + "");
                                            opcoes.add(resposta + "");
                                            opcoes.add(marcador.getData().get(6).getGoals().getOverall() + "");
                                            pergunta.opcoes = opcoes;
                                            pergunta.respostaCorreta = resposta + "";
                                            break;
                                        case 2:
                                            pergunta.pergunta = "Quem é o melhor marcador da Bundesliga?";
                                            pergunta.pontos = 100;
                                            String resposta1 = marcador.getData().get(0).getPlayer().getPlayer_name();
                                            opcoes.add(marcador.getData().get(1).getPlayer().getPlayer_name());
                                            opcoes.add(resposta1);
                                            opcoes.add(marcador.getData().get(4).getPlayer().getPlayer_name());
                                            pergunta.opcoes = opcoes;
                                            pergunta.respostaCorreta = resposta1;
                                            break;
                                        case 3:
                                            pergunta.pergunta = "Em que equipa joga o atual melhor marcador da Bundesliga?";
                                            pergunta.pontos = 100;
                                            String resposta2 = marcador.getData().get(0).getTeam().getTeam_name();
                                            opcoes.add(resposta2);
                                            opcoes.add(marcador.getData().get(5).getTeam().getTeam_name());
                                            opcoes.add(marcador.getData().get(1).getTeam().getTeam_name());
                                            pergunta.opcoes = opcoes;
                                            pergunta.respostaCorreta = resposta2;
                                            break;
                                        case 4:
                                            pergunta.pergunta = "Quantos golos marcou o atual melhor marcador da Bundesliga de penalti?";
                                            pergunta.pontos = 100;
                                            String resposta3 = marcador.getData().get(0).getPenalties() + "";
                                            opcoes.add(marcador.getData().get(1).getPenalties() + "");
                                            opcoes.add(marcador.getData().get(3).getPenalties() + "");
                                            opcoes.add(resposta3);
                                            pergunta.opcoes = opcoes;
                                            pergunta.respostaCorreta = resposta3;
                                            break;
                                    }
                                    return null;
                                }
                            }.execute();
                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    // Adicionar pergunta selecionada ao ROOM
                                    PerguntasDB.getInstance().perguntasDAO().addPergunta(pergunta);
                                    return null;
                                }
                            }.execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<Marcador> call, Throwable t) {
                        Log.d("Error", t.toString());
                    }
                });
    }

    /**
     * Função para retornar número de items na coleção de perguntas_default
     *
     * @param db db do firestore a procurar
     * @return tamanho
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static int size(FirebaseFirestore db) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = db.collection("perguntas_default").get();
        QuerySnapshot doc = Tasks.await(task);
        return doc.size();
    }

}
