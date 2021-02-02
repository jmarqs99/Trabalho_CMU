package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.API.Models.Models_Classificacao.Classificacao;
import com.example.myapplication.API.Models_Equipa.Equipa;
import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.Models_Jogo.jogo;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.example.myapplication.RecyclerView.Equipa_item;
import com.example.myapplication.RecyclerView.JogoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JogosAoVivoFragment extends Fragment {

    RecyclerView mRecyclerView;
    JogoAdapter mAdapter;
    final SportsDataAPI service = RetrofitClient.getApi();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_jogos_ao_vivo, container, false);
        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        service.getJogo("2020-09-19")
                .enqueue(new Callback<Partida>() {
                    @Override
                    public void onResponse(Call<Partida> call, Response<Partida> response) {
                        final Partida partida = response.body();
                        Log.d("JOGOS:",partida.toString());

                    }

                    @Override
                    public void onFailure(Call<Partida> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });;


        return v;
    }




}