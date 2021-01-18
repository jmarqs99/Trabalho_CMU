package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.RecyclerView.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassificacaoAtivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EquipaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao_ativity);


        List<Equipa> equipas = new ArrayList<>();

        Equipa equipa1 = new Equipa("Nome",60);


        equipas.add(0,equipa1);


        mAdapter = new EquipaAdapter(this,equipas);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}