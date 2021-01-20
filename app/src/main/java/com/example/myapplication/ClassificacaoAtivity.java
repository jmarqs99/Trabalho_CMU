package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.API.Classificacao;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificacaoAtivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EquipaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao_ativity);

        final SportsDataAPI service = RetrofitClient.getRetrofitInstance().create(SportsDataAPI.class);
        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){


                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        Classificacao classi = response.body();

                        Log.d("MOSTRAR CLASSI" , classi.toString());
                    }

                    @Override
                    public void onFailure(Call<Classificacao> call, Throwable t) {
                        Toast.makeText(ClassificacaoAtivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });


        List<Equipa> equipas = new ArrayList<>();

        Equipa equipa1 = new Equipa("Nome",60);


        equipas.add(0,equipa1);


        mAdapter = new EquipaAdapter(this,equipas);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}