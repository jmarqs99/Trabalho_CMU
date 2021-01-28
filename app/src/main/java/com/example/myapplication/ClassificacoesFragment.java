package com.example.myapplication;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.DividerItemDecoration;
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
        mRecyclerView = v.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){

                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        final Classificacao classificacao = response.body();
                        //Log.d("URL:", String.valueOf(call.request().url()));
                        //Log.d("MOSTRAR CLASSI" , classificacao.toString());

                        new AsyncTask<Void, Void, EquipaAdapter>() {

                            @Override
                            protected void onPostExecute(EquipaAdapter adapter) {
                                mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                            }

                            @Override
                            protected EquipaAdapter doInBackground(Void... voids) {
                                List<Equipa> equipas = getEquipas(classificacao);
                                Log.d("Main","Done getting teams");
                                List<Equipa_item> equipas_items = constroiClassificacao(classificacao,equipas);
                                mAdapter = new EquipaAdapter(getActivity(),equipas_items);
                                return mAdapter;
                            }

                        }.execute();
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
        return equipas;
    }


    private List<Equipa_item> constroiClassificacao(Classificacao classificacao,List<Equipa> equi){
        List<Equipa_item> equipas = new ArrayList<>();
        // i < 1 , para nao esgotar os pedidos da api , o correto e equi.size()
        for (int i = 0; i < 1; i++){

            Equipa_item equipa = new Equipa_item(equi.get(i).getData().getName(),
                    classificacao.getData().getStandings()[i].getPoints(),
                    classificacao.getData().getStandings()[i].getPosition(),
                    equi.get(i).getData().getLogo(),
                    classificacao.getData().getStandings()[i].getOverall().getGames_played(),
                    classificacao.getData().getStandings()[i].getOverall().getGoals_scored(),
                    classificacao.getData().getStandings()[i].getOverall().getGoals_against());
            equipas.add(i,equipa);
        }

        return equipas;
    }
}
