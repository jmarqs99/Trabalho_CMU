package com.example.myapplication;

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
import android.widget.Toast;

import com.example.myapplication.API.Models_Jogo.Partida;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
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
    private Button btnDatePicker;
    private EditText txtDate;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.jogos_fragment, container, false);
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
          //      "A carregar dados...", true);
        mRecyclerView = v.findViewById(R.id.mRecyclerViewJogos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnDatePicker=v.findViewById(R.id.btn_date);
        txtDate=v.findViewById(R.id.in_date);

        btnDatePicker.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        Date hoje = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date amanha = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String todayAsString = dateFormat.format(hoje);
        String tomorrowAsString = dateFormat.format(amanha);
        System.out.println(todayAsString);
        System.out.println(tomorrowAsString);


        service.getJogos(todayAsString,tomorrowAsString)
                .enqueue(new Callback<Partida>() {
                    @Override
                    public void onResponse(Call<Partida> call, Response<Partida> response) {
                        final Partida partida = response.body();
                        if(response.code() == 200) {
                            new AsyncTask<Void, Void, JogoAdapter>() {

                                @Override
                                protected void onPostExecute(JogoAdapter adapter) {
                                    mRecyclerView.setAdapter(mAdapter);
                                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                                }

                                @Override
                                protected JogoAdapter doInBackground(Void... voids) {
                                    List<Jogo_item> partidas = constroiLista(partida);
                                    mAdapter = new JogoAdapter(getActivity(), partidas);
                                    //dialog.dismiss();
                                    return mAdapter;
                                }

                            }.execute();
                        }else{
                            List<Sem_jogos_item> sem_jogos_items = new ArrayList<>();
                            Sem_jogos_item item = new Sem_jogos_item("Sem Jogos para mostrar");
                            sem_jogos_items.add(item);
                            sem_jogosAdapter = new Sem_jogosAdapter(getActivity(),sem_jogos_items);
                            mRecyclerView.setAdapter(sem_jogosAdapter);
                        }
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


    @Override
    public void onClick(View view) {
        if (view == btnDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}