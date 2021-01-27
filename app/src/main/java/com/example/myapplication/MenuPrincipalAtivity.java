package com.example.myapplication;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.Models.Classificacao;
import com.example.myapplication.API.RetrofitClient;
import com.example.myapplication.API.SportsDataAPI;
import com.example.myapplication.RecyclerView.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipalAtivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EquipaAdapter mAdapter;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificacao_ativity);

        final SportsDataAPI service = RetrofitClient.getApi();
        service.getClassificacao()
                .enqueue(new Callback<Classificacao>(){

                    @Override
                    public void onResponse(Call<Classificacao> call, Response<Classificacao> response) {
                        Classificacao classi = response.body();
                        Log.d("URL:", String.valueOf(call.request().url()));
                        Log.d("MOSTRAR CLASSI" , classi.toString());
                    }

                    @Override
                    public void onFailure(Call<Classificacao> call, Throwable t) {
                        Log.d("Error",t.toString());
                        Toast.makeText(MenuPrincipalAtivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });


        List<Equipa> equipas = new ArrayList<>();

        Equipa equipa1 = new Equipa("Nome",60);


        equipas.add(0,equipa1);


        mAdapter = new EquipaAdapter(this,equipas);
       /* mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        logoutButton = findViewById(R.id.logoutButton);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction tr = fm.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(MenuPrincipalAtivity.this, "Something", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        ClassificacoesFragment newFragment = new ClassificacoesFragment();

                        tr.replace(R.id.fragment2,newFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                        Toast.makeText(MenuPrincipalAtivity.this, "Classificações", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        PerfilFragment newPFragment = new PerfilFragment();

                        tr.replace(R.id.fragment2,newPFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                        Toast.makeText(MenuPrincipalAtivity.this, "Perfil", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }
}