package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import DB.Pergunta;

public class CriarPergunta {

    public void getPerguntaFirestore(final FirebaseFirestore db) {
        new Thread() {
            @Override
            public void run() {
                int size = 0;
                String posicao = "";
                try {
                    size = size(db);
                    final int min = 1;
                    final int max = size;
                    final int random = new Random().nextInt((max - min) + 1) + min;
                    posicao = String.valueOf(random);
                    Log.d("POSICAO", posicao + "");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("size", size + "");
                DocumentReference docRef = db.collection("perguntas_default").document(posicao);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("FIRESTORE", "DocumentSnapshot data: " + document.getData());
                                Pergunta pergunta = document.toObject(Pergunta.class);
                                Log.d("PERGUNTA", "pERGUNTA: " + pergunta.pergunta);
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

    private int size(FirebaseFirestore db) throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = db.collection("perguntas_default").get();
        QuerySnapshot doc = Tasks.await(task);
        return doc.size();
    }

}
