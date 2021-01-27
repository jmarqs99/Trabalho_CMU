package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.RecyclerView.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassificacoesFragment extends Fragment {

    RecyclerView mRecyclerView;
    EquipaAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.classificacoes_fragment, container, false);

        List<Equipa> equipas = new ArrayList<>();
        Equipa equipa = new Equipa("Nome",10);
        equipas.add(equipa);
        mAdapter = new EquipaAdapter(getActivity(),equipas);

        mRecyclerView = v.findViewById(R.id.mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }
}
