package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Models.Classificacao;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificacoesFragment extends Fragment {

    RecyclerView mRecyclerView;
    EquipaAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.classificacoes_fragment, container, false);
        final SportsDataAPI service = RetrofitClient.getApi();
        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){

                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        Classificacao classificacao = response.body();
                        Log.d("URL:", String.valueOf(call.request().url()));
                        Log.d("MOSTRAR CLASSI" , classificacao.toString());
                        List<Equipa> equipas = constroiClassificacao(classificacao);
                        mAdapter = new EquipaAdapter(getActivity(),equipas);


                        mRecyclerView = v.findViewById(R.id.mRecyclerView);
                        mRecyclerView.setAdapter(mAdapter);

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }

                    @Override
                    public void onFailure(Call<Classificacao> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

        return v;
    }

    private List<Equipa> constroiClassificacao(Classificacao classificacao){
        List<Equipa> equipas = new ArrayList<>();

        for (int i = 0; i < classificacao.getData().getStandings().length; i++){
            Equipa equipa = new Equipa(classificacao.getData().getStandings()[i].getTeam_id() + "",classificacao.getData().getStandings()[i].getPoints());
            equipas.add(i,equipa);
        }
        return equipas;
    }
}
