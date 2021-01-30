package com.example.myapplication;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.API.Models_Equipa.Equipa;
import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.example.myapplication.RecyclerView.Equipa_item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import DB.Perguntas;
import DB.PerguntasDAO;
import DB.PerguntasDB;

public class MenuPrincipalAtivity extends AppCompatActivity implements LogoutSelected, EditarDadosSelected {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String mailUser;
    private int pontosUser;
    PerguntasDB perguntasDB;
    private List<Perguntas> allPerguntas;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final Perguntas pergunta1 = new Perguntas();
        pergunta1.pontos = 10;
        pergunta1.acertou = true;
        pergunta1.pergunta = "sdsd";
        pergunta1.resposta = "sd";

        perguntasDB = Room.databaseBuilder(getApplicationContext(), PerguntasDB.class, "sample-db").build();


        new AsyncTask<Void, Void, EquipaAdapter>() {

            @Override
            protected void onPostExecute(EquipaAdapter adapter) {

            }

            @Override
            protected EquipaAdapter doInBackground(Void... voids) {

                List<Perguntas> pergunta;
                //perguntasDB.perguntasDAO().addPergunta(pergunta1);

                pontosUser = perguntasDB.perguntasDAO().getConta();

                allPerguntas = perguntasDB.perguntasDAO().getPerguntas();

                for(int i=0; i< allPerguntas.size();  i++) {
                    Log.d("Perguntas: ", allPerguntas.get(i).pergunta + "");
                }

                return null;
            }

        }.execute();

        //perguntasDB.perguntasDAO().addPergunta(pergunta1);
       // pontosUser = perguntasDB.perguntasDAO().getConta();

   //     PerguntasDB.getInstance(this).perguntasDAO().addPergunta(perguntas);
//        pontosUser = PerguntasDB.getInstance(this).perguntasDAO().getConta();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_fragment);

        Intent intent = getIntent();
        mailUser = intent.getStringExtra("mailUser");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();

        QuizFragment newQFragment = new QuizFragment();

        tr.replace(R.id.fragment2,newQFragment);
        tr.addToBackStack(null);
        tr.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction tr = fm.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_quizz:
                        QuizFragment newQFragment = new QuizFragment();

                        tr.replace(R.id.fragment2,newQFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                        break;
                    case R.id.action_standings:
                        ClassificacoesFragment newFragment = new ClassificacoesFragment();

                        tr.replace(R.id.fragment2,newFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                        break;
                    case R.id.action_profile:

                        Bundle b = new Bundle();
                        b.putString("maill", mailUser);
                        b.putInt("pontos", pontosUser);

                        PerfilFragment newPFragment = new PerfilFragment();

                        newPFragment.setArguments(b);
                        tr.replace(R.id.fragment2,newPFragment);
                        tr.addToBackStack(null);
                        tr.commit();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        mailUser = currentUser.getEmail();

    }

    @Override
    public void OnLogoutSelect() {
        mAuth.signOut();
        updateUI(null);
    }



    private void updateUI(FirebaseUser user) {
        if(user != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();

            QuizFragment newQFragment = new QuizFragment();

            tr.replace(R.id.fragment2,newQFragment);
            tr.addToBackStack(null);
            tr.commit();

        }
        else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void editarPass() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();

        EditarDadosFragment editarDadosFragment = new EditarDadosFragment();

        tr.replace(R.id.fragment2,editarDadosFragment);
        tr.addToBackStack(null);
        tr.commit();
    }

}