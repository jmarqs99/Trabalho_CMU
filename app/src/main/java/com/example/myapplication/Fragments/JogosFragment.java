package com.example.myapplication.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerView.JogoAdapter;
import com.example.myapplication.RecyclerView.Jogo_item;
import com.example.myapplication.RecyclerView.Sem_jogosAdapter;
import com.example.myapplication.RecyclerView.Sem_jogos_item;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JogosFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private JogoAdapter mAdapter;
    private Sem_jogosAdapter sem_jogosAdapter;
    private final SportsDataAPI service = RetrofitClient.getApi();
    private ImageButton btnDatePicker;
    private TextView dataTextView;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.jogos_fragment, container, false);

        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnDatePicker = v.findViewById(R.id.btn_date);
        dataTextView = v.findViewById(R.id.data);
        btnDatePicker.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        // Dia atual
        Date hoje = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        // Dia seguinte ao atual
        Date amanha = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Transforma as datas em String e aplica o formato pretendido
        String todayAsString = dateFormat.format(hoje);
        String tomorrowAsString = dateFormat.format(amanha);

        dataTextView.setText(todayAsString);

        // Função para retornar os jogos do dia todayAsString , o dia atual
        getJogos(todayAsString, tomorrowAsString);

        return v;
    }

    /**
     * Retorna uma Lista de Jogo_Item dada uma Partida
     *
     * @param partida partida dada
     * @return Lista de Jogo_item
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

    @Override
    public void onClick(View view) {
        if (view == btnDatePicker) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Retorna a data do dia atual e dia seguinte em formato string
                            List<String> datas = constroiData(year, monthOfYear, dayOfMonth);


                            getJogos(datas.get(0), datas.get(1));
                            dataTextView.setText(datas.get(0));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    /**
     * Função para fazer pedido a api para jogos do dia data_inicio
     *
     * @param data_inicio Data dos jogos a visualizar
     * @param data_fim    Dia seguinte
     */
    private void getJogos(String data_inicio, String data_fim) {
        service.getJogos(data_inicio, data_fim)
                .enqueue(new Callback<Partida>() {
                    @Override
                    public void onResponse(Call<Partida> call, Response<Partida> response) {
                        final Partida partida = response.body();
                        if (response.code() == 200) {
                            new AsyncTask<Void, Void, JogoAdapter>() {

                                @Override
                                protected void onPostExecute(JogoAdapter adapter) {
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                }

                                @Override
                                protected JogoAdapter doInBackground(Void... voids) {
                                    // Constroi uma lista com Jogos_item dada as partidas
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
     * Transforma os dados Recebidos em uma lista de datas em String contendo a data do dia atual e
     * do dia seguinte
     *
     * @param ano ano da data atual
     * @param mes mes da data atual
     * @param dia dia da data atual
     * @return lista de string com as datas
     */
    private List<String> constroiData(int ano, int mes, int dia) {
        List<String> datas = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes, dia);

        Date hoje = cal.getTime();
        String data_inicio = dateFormat.format(hoje);

        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date fim = cal.getTime();
        String data_fim = dateFormat.format(fim);

        datas.add(0, data_inicio);
        datas.add(1, data_fim);

        return datas;
    }

}