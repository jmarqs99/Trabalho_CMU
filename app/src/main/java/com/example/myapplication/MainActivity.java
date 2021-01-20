package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button loginButton;
    private TextView tv;

    private Button proxAtivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button3);



        proxAtivity = findViewById(R.id.buttonClassi);


        tv = findViewById(R.id.textView);

        mAuth = FirebaseAuth.getInstance();


        tv = findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = email.getText().toString();
                String userPass = password.getText().toString();

                if(userMail.isEmpty()) {
                    email.setError("Insira um email");
                    email.requestFocus();
                }
                else if(userPass.isEmpty()) {
                    password.setError("Insira uma password");
                    password.requestFocus();
                }
                else if(userMail.isEmpty() || userPass.isEmpty()) {
                    tv.setText("Parametros vazios");
                }
                else {
                   loginUser(userMail, userPass);
                }


                proxAtivity.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, RegistarActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });

    }

    private void loginUser(String userMail, String userPass) {
        mAuth.signInWithEmailAndPassword(userMail, userPass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    tv.setText("Login bem sucedido");
                    Intent intent = new Intent(MainActivity.this,ClassificacaoAtivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    tv.setText("Login falhou");
                }
            }
        });
    }

}