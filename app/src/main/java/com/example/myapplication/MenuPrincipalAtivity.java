package com.example.myapplication;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.PendingIntent;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    private GoogleSignInClient signInClient;
    private FirebaseFirestore db;
    private String mailUser;
    private int pontosUser;
    PerguntasDB perguntasDB;
    private List<Perguntas> allPerguntas;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                List<Perguntas> pergunta;

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
                    case R.id.jogos_live:
                        JogosAoVivoFragment jogosAoVivo = new JogosAoVivoFragment();
                        tr.replace(R.id.fragment2,jogosAoVivo);
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