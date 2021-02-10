package com.example.myapplication.Fragments;

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

import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerView.JogoAdapter;
import com.example.myapplication.RecyclerView.Jogo_item;
import com.example.myapplication.RecyclerView.Sem_jogosAdapter;
import com.example.myapplication.RecyclerView.Sem_jogos_item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class jogosLiveFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private JogoAdapter mAdapter;
    private Sem_jogosAdapter sem_jogosAdapter;
    private final SportsDataAPI service = RetrofitClient.getApi();

    public jogosLiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.jogos_live_fragment, container, false);

        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Thread para fazer pedido dos jogos de 1 em 1 minuto para atualizar os minutos
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    getJogos();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return v;
    }

    /**
     * Função para fazer pedido a api para retornar jogos que estejam a acontecer no momento
     */
    private void getJogos() {
        service.getJogosLive()
                .enqueue(new Callback<Partida>() {
                    @Override
                    public void onResponse(Call<Partida> call, Response<Partida> response) {
                        final Partida partida = response.body();
                        // Verificar se a resposta tem codigo 200
                        if (response.code() == 200) {
                            if (!partida.getData().isEmpty()) {
                                new AsyncTask<Void, Void, JogoAdapter>() {

                                    @Override
                                    protected void onPostExecute(JogoAdapter adapter) {
                                        mRecyclerView.setAdapter(mAdapter);
                                    }

                                    @Override
                                    protected JogoAdapter doInBackground(Void... voids) {
                                        // Constroi a lista de Jogo_item com as partidas retornadas pela api
                                        List<Jogo_item> partidas = constroiLista(partida);
                                        mAdapter = new JogoAdapter(getActivity(), partidas);

                                        return mAdapter;
                                    }

                                }.execute();
                            } else {
                                // Caso não existam jogos para mostrar
                                List<Sem_jogos_item> sem_jogos_items = new ArrayList<>();
                                Sem_jogos_item item = new Sem_jogos_item("Sem Jogos para mostrar");
                                sem_jogos_items.add(item);
                                sem_jogosAdapter = new Sem_jogosAdapter(getActivity(), sem_jogos_items);
                                mRecyclerView.setAdapter(sem_jogosAdapter);
                            }
                        } else {
                            Toast.makeText(getActivity(), "API Requests reached limit",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Partida> call, Throwable t) {
                        Log.d("Error", t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
        ;
    }

    /**
     * Constroi uma lista de Jogo_item atraves das partidas retornadas na api
     * @param partida partida da api
     * @return lista de jogo_item
     */
    public List<Jogo_item> constroiLista(Partida partida) {
        List<Jogo_item> jogos = new ArrayList<>();
        for (int i = 0; i < partida.getData().size(); i++) {
            Jogo_item jogo = new Jogo_item(partida.getData().get(i).getHome_team().getName(),
                    partida.getData().get(i).getAway_team().getName(),
                    partida.getData().get(i).getHome_team().getLogo(),
                    partida.getData().get(i).getAway_team().getLogo(),
                    partida.getData().get(i).getStats().getHome_score(),
                    partida.getData().get(i).getStats().getAway_score(),
                    partida.getData().get(i).getStatus_code(),
                    partida.getData().get(i).getMatch_start(),
                    partida.getData().get(i).getMinute());
            jogos.add(i, jogo);
        }
        return jogos;
    }

}