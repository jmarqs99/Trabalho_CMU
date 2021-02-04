package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.RecyclerView.EquipaAdapter;

import java.util.List;

import DB.Pergunta;
import DB.PerguntasDB;

public class QuizFragment extends Fragment implements View.OnClickListener{

    PerguntasDB dataBase;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.quizs_fragment, container, false);
        context = v.getContext();
        new AsyncTask<Void, Void, List<Pergunta>>() {
            @Override
            protected void onPostExecute(List<Pergunta> perguntas) {
                super.onPostExecute(perguntas);
                TextView nmrPerguntas = v.findViewById(R.id.perguntasCount);

                nmrPerguntas.setText(perguntas.size() + "");

                if (perguntas.size() == 0){
                    v.findViewById(R.id.awnserQuizz).setEnabled(false);
                }
            }

            @Override
            protected List<Pergunta> doInBackground(Void... voids) {
                List<Pergunta> perguntas = PerguntasDB.getInstance().perguntasDAO().getPerguntasPorResponder();
                return perguntas;
            }
        }.execute();

        v.findViewById(R.id.awnserQuizz).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.awnserQuizz){
            new AsyncTask<Void, Void, List<Pergunta>>() {
                @Override
                protected void onPostExecute(List<Pergunta> perguntas) {
                    super.onPostExecute(perguntas);
                    Intent intent = new Intent(context, QuizActivity.class);
                    intent.putExtra("Pergunta",perguntas.get(0));
                    startActivityForResult(intent,1);
                }

                @Override
                protected List<Pergunta> doInBackground(Void... voids) {
                    List<Pergunta> perguntas = PerguntasDB.getInstance().perguntasDAO().getPerguntasPorResponder();
                    return perguntas;
                }
            }.execute();
        }
    }
}
