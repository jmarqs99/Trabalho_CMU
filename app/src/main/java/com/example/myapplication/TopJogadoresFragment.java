package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.RecyclerView.JogadoresAdapter;
import com.example.myapplication.RecyclerView.Jogadores_item;
import com.example.myapplication.RecyclerView.JogoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import DB.Pergunta;


public class TopJogadoresFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private JogadoresAdapter mAdapter;
    private FirebaseFirestore db;

    public TopJogadoresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.top_jogadoresfragment, container, false);
        mRecyclerView = v.findViewById(R.id.mRecyclerVieTopJogadores);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        db = FirebaseFirestore.getInstance();

        getJogadoresFirestore();

        return v;
    }

    private void getJogadoresFirestore() {
        new AsyncTask<Void, Void, List<Jogadores_item>>() {

            @Override
            protected List<Jogadores_item> doInBackground(Void... voids) {
                final List<Jogadores_item> jogadores = new ArrayList<>();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int i = 1;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Jogadores_item jogador = new Jogadores_item(i + ".",document.getId(), (Long) document.get("pontos") );
                                        jogadores.add(jogador);
                                        i++;
                                    }
                                    List<Jogadores_item> jogadoresItemList = trataLista(jogadores);
                                    mAdapter = new JogadoresAdapter(getActivity(), jogadoresItemList);
                                    mRecyclerView.setAdapter(mAdapter);
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
                return jogadores;
            }
        }.execute();
    }

    private List<Jogadores_item> trataLista(List<Jogadores_item> jogadores){
        Log.d("SORT",jogadores.toString());
        Collections.sort(jogadores, Collections.reverseOrder());
        Log.d("SORT",jogadores.toString());

        if( jogadores.size() < 10){
            return  jogadores.subList(0,jogadores.size());
        }
        else{
            return  jogadores.subList(0,10);
        }
    }




}