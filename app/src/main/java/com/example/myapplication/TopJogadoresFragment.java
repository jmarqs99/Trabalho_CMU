package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.RecyclerView.JogadoresAdapter;
import com.example.myapplication.RecyclerView.Jogadores_item;
import com.example.myapplication.RecyclerView.JogoAdapter;

import java.util.ArrayList;
import java.util.List;


public class TopJogadoresFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private JogadoresAdapter mAdapter;

    public TopJogadoresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.top_jogadoresfragment, container, false);
        mRecyclerView = v.findViewById(R.id.mRecyclerVieTopJogadores);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        List<Jogadores_item> jogadores = new ArrayList<>();
        Jogadores_item jogadores_item = new Jogadores_item("1","mail","pontos");
        jogadores.add(0,jogadores_item);
        jogadores.add(1,jogadores_item);

        mAdapter = new JogadoresAdapter(getActivity(), jogadores);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}