package com.example.myapplication;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.myapplication.RecyclerView.EquipaAdapter;
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
    private boolean loginPeloGoogle;
    private int pontosUser;
    PerguntasDB perguntasDB;
    private List<Pergunta> allPerguntas;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PerguntasDB.getInstance(this); // Usado para instanciar o DAO pela primeira vez
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        startService(new Intent(this, NewQuestionsService.class));

        perguntasDB = Room.databaseBuilder(getApplicationContext(), PerguntasDB.class, "sample-db").build();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);



        new AsyncTask<Void, Void, EquipaAdapter>() {

            @Override
            protected void onPostExecute(EquipaAdapter adapter) {

            }

            @Override
            protected EquipaAdapter doInBackground(Void... voids) {

                List<Pergunta> pergunta;

                pontosUser = perguntasDB.perguntasDAO().getConta();

                allPerguntas = perguntasDB.perguntasDAO().getPerguntas();

                for(int i=0; i< allPerguntas.size();  i++) {
                    Log.d("Perguntas: ", allPerguntas.get(i).pergunta + "");
                }

                return null;
            }



            public EquipaAdapter apagarPerguntasNoLogout(Void... voids) {

                List<Pergunta> pergunta;

                pontosUser = perguntasDB.perguntasDAO().getConta();

                allPerguntas = perguntasDB.perguntasDAO().getPerguntas();

                for(int i=0; i< allPerguntas.size();  i++) {
                    perguntasDB.perguntasDAO().deletePergunta(allPerguntas.get(i));
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
        loginPeloGoogle = intent.getBooleanExtra("googleLogin", false);
        Log.d("LoginPeloGO", loginPeloGoogle + "");

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
                        b.putBoolean("googlelogin", loginPeloGoogle);

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
        //mailUser = currentUser.getEmail();

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


        if(user != null) {

            mailUser = user.getEmail();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();

            QuizFragment newQFragment = new QuizFragment();

            tr.replace(R.id.fragment2,newQFragment);
            tr.addToBackStack(null);
            tr.commit();
            Log.d("userNotNull", "usernaoNull");

        }
        else if (account != null) {
            mailUser = account.getEmail();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();

            Log.d("ContauserNotNull", "ContausernaoNull");



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