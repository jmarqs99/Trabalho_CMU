package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.RecyclerView.PerguntasAdapter;

import java.util.List;

import DB.Pergunta;
import DB.PerguntasDB;


public class verRespostasFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PerguntasAdapter mAdapter;

    public verRespostasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_ver_respostas, container, false);

        mRecyclerView = v.findViewById(R.id.mRecyclerViewPerguntasRespondidas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));


        new AsyncTask<Void, Void, PerguntasAdapter>() {
            @Override
            protected void onPostExecute(PerguntasAdapter adapter) {
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
            }

            @Override
            protected PerguntasAdapter doInBackground(Void... voids) {
                // Vai buscar ao ROOM as perguntas que já foram respondidas
                List<Pergunta> perguntas = PerguntasDB.getInstance().perguntasDAO().getPerguntasRespondidas();
                mAdapter = new PerguntasAdapter(getActivity(), perguntas);
                return mAdapter;
            }
        }.execute();

        return v;
    }
}