package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

        // Dialog de loading enquanto os dados são carregados da api
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "A carregar dados...", true);
        mRecyclerView = v.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Fazer pedido a api pela classificação
        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){
                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        final Classificacao classificacao = response.body();
                        // Verificar se o status code da resposta foi 200
                        if (response.code() == 200) {
                            new AsyncTask<Void, Void, EquipaAdapter>() {

                                @Override
                                protected void onPostExecute(EquipaAdapter adapter) {
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                }

                                @Override
                                protected EquipaAdapter doInBackground(Void... voids) {
                                    // Retorna a lista de equipas presentes na classificação
                                    List<Equipa> equipas = getEquipas(classificacao);
                                    // Constroi a classificação com os dados das equipas e da classificação
                                    List<Equipa_item> equipas_items = constroiClassificacao(classificacao, equipas);

                                    mAdapter = new EquipaAdapter(getActivity(), equipas_items);

                                    // Fecha o dialog
                                    dialog.dismiss();

                                    return mAdapter;
                                }

                            }.execute();
                        } else {
                            Toast.makeText(getActivity(), "API Requests reached limit",
                                    Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Classificacao> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

        return v;
    }

    /**
     * Função que retorna as equipas dada a classificação
     * @param classificacao classificação onde se encontram as equipas
     * @return Lista de equipas
     */
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

    /**
     * Função que constroi a classificação final
     * @param classificacao classificação onde se encontram as equipas
     * @param equi lista de equipas
     * @return lista de Equipa_item
     */
    private List<Equipa_item> constroiClassificacao(Classificacao classificacao,List<Equipa> equi){
        List<Equipa_item> equipas = new ArrayList<>();
        for (int i = 0; i < equi.size(); i++){

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
