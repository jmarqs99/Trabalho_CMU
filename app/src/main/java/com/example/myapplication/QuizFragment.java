package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import DB.Pergunta;
import DB.PerguntasDB;

public class QuizFragment extends Fragment implements View.OnClickListener{

    PerguntasDB dataBase;
    private Context context;
    private View view;
    private final int TIME_BETWEEN_QR = 30 * 60; //segundos

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quizs_fragment, container, false);
        context = view.getContext();

        view.findViewById(R.id.awnserQuizz).setOnClickListener(this);
        view.findViewById(R.id.practiceBtn).setOnClickListener(this);
        view.findViewById(R.id.scanQR).setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AsyncTask<Void, Void, List<Pergunta>>() {
            @Override
            protected void onPostExecute(List<Pergunta> perguntas) {
                super.onPostExecute(perguntas);
                TextView nmrPerguntas = view.findViewById(R.id.perguntasCount);

                nmrPerguntas.setText(perguntas.size() + "");

                if (perguntas.size() == 0){
                    view.findViewById(R.id.awnserQuizz).setEnabled(false);
                }
            }

            @Override
            protected List<Pergunta> doInBackground(Void... voids) {
                List<Pergunta> perguntas = PerguntasDB.getInstance().perguntasDAO().getPerguntasPorResponder();
                return perguntas;
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.awnserQuizz){
            new AsyncTask<Void, Void, List<Pergunta>>() {
                @Override
                protected void onPostExecute(List<Pergunta> perguntas) {
                    super.onPostExecute(perguntas);
                    if(perguntas.size() > 0) {
                        Intent intent = new Intent(context, QuizActivity.class);
                        intent.putExtra("Pergunta", perguntas.get(0));
                        intent.putExtra("real", true);
                        startActivityForResult(intent, 1);
                    }
                }

                @Override
                protected List<Pergunta> doInBackground(Void... voids) {
                    List<Pergunta> perguntas = PerguntasDB.getInstance().perguntasDAO().getPerguntasPorResponder();
                    return perguntas;
                }
            }.execute();
        } else if(view.getId() == R.id.practiceBtn){
            new Thread() {
                @Override
                public void run() {
                    int size = 0;
                    String posicao = "";
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    try {
                        Task<QuerySnapshot> task = db.collection("perguntas_default").get();
                        size = Tasks.await(task).size();
                        final int min = 1;
                        final int random = new Random().nextInt((size - min) + 1) + min;
                        posicao = String.valueOf(random);

                        DocumentReference docRef = db.collection("perguntas_default").document(posicao);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    final Pergunta pergunta = document.toObject(Pergunta.class);
                                    Intent intent = new Intent(context, QuizActivity.class);
                                    intent.putExtra("Pergunta",pergunta);
                                    intent.putExtra("real",false);
                                    startActivityForResult(intent,1);
                                }
                            }
                            }
                        });
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if(view.getId() == R.id.scanQR) {
            final SharedPreferences mPrefs = context.getSharedPreferences("lastQRRead", 0);
            Long lastTimestamp = mPrefs.getLong("timestamp", 0);
            if (System.currentTimeMillis()/1000 - lastTimestamp >  TIME_BETWEEN_QR){
                mPrefs.edit().putLong("timestamp", System.currentTimeMillis() / 1000).commit();
                Intent intent = new Intent(context, ScanQRCode.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Deste scan a um QR code á pouco tempo", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
