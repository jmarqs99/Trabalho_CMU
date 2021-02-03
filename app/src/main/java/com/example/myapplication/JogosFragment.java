package com.example.myapplication;

import android.app.ProgressDialog;
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
import com.example.myapplication.RecyclerView.JogoAdapter;
import com.example.myapplication.RecyclerView.Jogo_item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JogosFragment extends Fragment {

    RecyclerView mRecyclerView;
    JogoAdapter mAdapter;
    final SportsDataAPI service = RetrofitClient.getApi();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_jogos_ao_vivo, container, false);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "A carregar dados...", true);
        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        service.getJogos()
                .enqueue(new Callback<Partida>() {
                    @Override
                    public void onResponse(Call<Partida> call, Response<Partida> response) {
                        final Partida partida = response.body();

                        new AsyncTask<Void, Void, JogoAdapter>() {

                            @Override
                            protected void onPostExecute(JogoAdapter adapter) {
                                mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                            }

                            @Override
                            protected JogoAdapter doInBackground(Void... voids) {
                                List<Jogo_item> partidas = constroiLista(partida);
                                mAdapter = new JogoAdapter(getActivity(),partidas);
                                dialog.dismiss();
                                return mAdapter;
                            }

                        }.execute();

                    }

                    @Override
                    public void onFailure(Call<Partida> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });;
        return v;
    }

    private List<Jogo_item> constroiLista(Partida partida) {
        List<Jogo_item> jogos = new ArrayList<>();
        for (int i =0 ; i< partida.getData().size(); i++){
            Jogo_item jogo = new Jogo_item(partida.getData().get(i).getHome_team().getName(),
                    partida.getData().get(i).getAway_team().getName(),
                    partida.getData().get(i).getHome_team().getLogo(),
                    partida.getData().get(i).getAway_team().getLogo(),
                    partida.getData().get(i).getStats().getHome_score(),
                    partida.getData().get(i).getStats().getAway_score(),
                    partida.getData().get(i).getStatus());
            jogos.add(i,jogo);
        }
        return jogos;
    }



}