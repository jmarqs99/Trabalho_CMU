package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements LoginSelected, RegisterSelected{

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button loginButton;

    private Button proxAtivity;


    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        LoginFragment loginFragment = new LoginFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainActivity, loginFragment, "home");
        ft.addToBackStack("home");
        ft.commit();

    }

    @Override
    public void onSelected(String mail, String pass) {

        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    Intent intent = new Intent(MainActivity.this,ClassificacaoAtivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                }
            }
        });
    }

    @Override
    public void onRegisterButtonSelected() {
        RegistarUserFragment registarUser = new RegistarUserFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.mainActivity, registarUser, "registarUtilizador");
        ft.addToBackStack("registarUtilizador");
        ft.commit();
    }

    @Override
    public void OnRegisterSelected(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    OnBackToLoginPageSelected();
                }
            }
        });
    }

    @Override
    public void OnBackToLoginPageSelected() {
        LoginFragment voltarLogin = new LoginFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.mainActivity, voltarLogin, "voltarLoginPage");
        ft.addToBackStack("voltarLoginPage");
        ft.commit();

    }
}