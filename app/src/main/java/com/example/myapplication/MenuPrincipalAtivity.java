package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.example.myapplication.Services.CheckLocationService;
import com.example.myapplication.Services.NewQuestionsService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import DB.Pergunta;
import DB.PerguntasDB;

public class MenuPrincipalAtivity extends AppCompatActivity implements LogoutSelected, EditarDadosSelected {
    private FirebaseAuth mAuth;
    private GoogleSignInClient signInClient;
    private FirebaseFirestore db;
    private String mailUser;
    private int pontosUser;
    PerguntasDB perguntasDB;
    private List<Pergunta> allPerguntas;
    private View dualFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PerguntasDB.getInstance(this); // Usado para instanciar o DAO pela primeira vez
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        startService(new Intent(this, NewQuestionsService.class)); // Serviço foreground para dar quizz de x em x tempo
        startService(new Intent(this, CheckLocationService.class)); // Serviço que dá quizzes baseado na localização

        perguntasDB = Room.databaseBuilder(getApplicationContext(), PerguntasDB.class, "sample-db").build();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_fragment);

        Intent intent = getIntent();
        mailUser = intent.getStringExtra("mailUser");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();

        dualFrag = findViewById(R.id.quizFragment);

        Fragment defaultFrag;
        if (dualFrag != null) {
            defaultFrag = new ClassificacoesFragment();

            FragmentTransaction tr2 = fm.beginTransaction();
            Fragment quizFrag = new QuizFragment();
            tr2.replace(R.id.quizFragment,quizFrag);
            tr2.addToBackStack(null);
            tr2.commit();
        } else {
            defaultFrag = new QuizFragment();
        }
        tr.replace(R.id.fragment2,defaultFrag);
        tr.addToBackStack(null);
        tr.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction tr = fm.beginTransaction();

                if (dualFrag != null) {
                    Fragment quizFrag = new QuizFragment();
                    tr.replace(R.id.quizFragment,quizFrag);
                    tr.addToBackStack(null);
                    tr.commit();
                }

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
                    case R.id.jogos:
                        JogosFragment jogos = new JogosFragment();
                        tr.replace(R.id.fragment2,jogos);
                        tr.addToBackStack(null);
                        tr.commit();
                        break;
                    case R.id.jogos_live:
                        jogosLiveFragment jogoslive = new jogosLiveFragment();
                        tr.replace(R.id.fragment2,jogoslive);
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
    }

    @Override
    public void OnLogoutSelect() {
       mAuth.signOut();
       signInClient.signOut();
       updateUI(null);
       apagarPerguntasNoLogout();
    }

    private void apagarPerguntasNoLogout() {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                PerguntasDB.getInstance().perguntasDAO().removerPerguntas();
                Log.d("PerguntasEliminadas", PerguntasDB.getInstance().perguntasDAO().getPerguntas().toString());

                return null;
            }
        }.execute();

    }


    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        if(user != null || account != null) {
            mailUser = user.getEmail();
        }
        else if (account != null) {
            mailUser = account.getEmail();
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