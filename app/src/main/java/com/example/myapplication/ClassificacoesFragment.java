package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Models.Models_Classificacao.Classificacao;
import com.example.myapplication.API.Models_Equipa.Equipa;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.example.myapplication.RecyclerView.Equipa_item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificacoesFragment extends Fragment {

    RecyclerView mRecyclerView;
    EquipaAdapter mAdapter;
    final SportsDataAPI service = RetrofitClient.getApi();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.classificacoes_fragment, container, false);

        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){

                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        final Classificacao classificacao = response.body();
                        Log.d("URL:", String.valueOf(call.request().url()));
                        Log.d("MOSTRAR CLASSI" , classificacao.toString());


                        new AsyncTask<Void, Void, String>() {
                            @Override
                            protected String doInBackground(Void... voids) {
                                List<Equipa> equipas = getEquipas(classificacao);
                                List<Equipa_item> equipas_items = constroiClassificacao(classificacao,equipas);
                                mAdapter = new EquipaAdapter(getActivity(),equipas_items);


                                mRecyclerView = v.findViewById(R.id.mRecyclerView);
                                mRecyclerView.setAdapter(mAdapter);

                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                return null;
                            }}.execute();
                    }

                    @Override
                    public void onFailure(Call<Classificacao> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

        return v;
    }

    private List<Equipa> getEquipas(Classificacao classificacao){
        final List<Equipa> equipas = new ArrayList<>();
        for (int i=0;i<classificacao.getData().getStandings().length;i++){
            Response<Equipa> response = null;
            try {
                response = service.getEquipa(classificacao.getData().getStandings()[i].getTeam_id())
                        .execute();
                        Equipa equipa = response.body();
                        equipas.add(equipa);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("URL:", "ahhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        return equipas;
    }


    private List<Equipa_item> constroiClassificacao(Classificacao classificacao,List<Equipa> equi){
        List<Equipa_item> equipas = new ArrayList<>();

        for (int i = 0; i < equi.size(); i++){

            Equipa_item equipa = new Equipa_item(equi.get(i).getData().getName(),classificacao.getData().getStandings()[i].getPoints());
            equipas.add(i,equipa);
        }

        return equipas;
    }
}
