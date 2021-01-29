package com.example.myapplication;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.RecyclerView.EquipaAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuPrincipalAtivity extends AppCompatActivity implements LogoutSelected, EditarDadosSelected{
    private FirebaseAuth mAuth;

    private String mailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();


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