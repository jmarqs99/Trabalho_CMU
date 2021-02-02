package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginSelected, RegisterSelected{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient signInClient;
    private JobScheduler mScheduler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_fragment);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
        } else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
        }

        db = FirebaseFirestore.getInstance();

        Intent intent = new Intent(this,CheckLocation.class);
        startService(intent);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onSelected(final String mail, final String pass, final TextView errorMessages) {

        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
                    intent.putExtra("mailUser", mail);
                    intent.putExtra("passUser", pass);
                    startActivity(intent);
                    finish();
                    errorMessages.setText("Login bem sucedido");
                }
                else {
                    updateUI(null);
                    errorMessages.setText("Login falhado");
                }
            }
        });
    }

    @Override
    public void onLoginWithGoogleSelected() {

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);

        /**
        Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
        intent.putExtra("mailUser", signInOptions.getAccount());
        startActivity(intent);
        finish();
         */
       // Intent intent = signInClient.getSignInIntent();
       // startActivityForResult(intent);

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
    public void OnRegisterSelected(final String email, final String password, final TextView errorMessage) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    /** //teste
                    User user1 = new User(email);
                    DocumentReference documentReference = db.collection("users").document(email);

                    documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("FirestoreSuccess", "user foi adicionado");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FirestoreFailure", "user n foi adicionado");
                        }
                    });
                    */

                    updateUI(user);
                    OnBackToLoginPageSelected();
                    errorMessage.setText("Conta registada com sucesso!");
                }
                else {
                    updateUI(null);
                    errorMessage.setText("O registo de conta falhou!");
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


    private void updateUI(FirebaseUser user) {

        if(user != null) {
            Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
            startActivity(intent);
            finish();
        }
        else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.mainActivity, loginFragment, "home");
            ft.addToBackStack("home");
            ft.commit();
        }
    }


}