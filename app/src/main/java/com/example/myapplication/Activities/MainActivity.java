package com.example.myapplication.Activities;

import android.Manifest;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Fragments.LoginFragment;
import com.example.myapplication.Fragments.RegistarUserFragment;
import com.example.myapplication.LoginSelected;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelected;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginSelected, RegisterSelected {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient signInClient;
    private JobScheduler mScheduler;
    private final static int RC_SIGN_IN = 123;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_fragment);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        createRequest();
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
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user.isEmailVerified()) {
                        updateUI(user);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Confirme a sua conta no email que foi enviado por favor!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (mail.isEmpty()) {
                        errorMessages.setText("Login falhado! Email vazio!");
                        errorMessages.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                errorMessages.setVisibility(View.INVISIBLE);
                            }
                        }, 2500); //desparece passados 2,5 segundos
                    }
                    errorMessages.setText("Login falhado! Verifique os dados inseridos!");
                    errorMessages.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            errorMessages.setVisibility(View.INVISIBLE);
                        }
                    }, 2500); //desparece passados 2,5 segundos
                }
            }
        });
    }

    /**
     * Função para criar um request do google
     */
    private void createRequest() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);
    }

    @Override
    public void onLoginWithGoogleSelected() {
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Função para fazer login na aplicação com o conta do Google
     *
     * @param idToken
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("googleSucesso", "Login com google sucesso");

                            FirebaseUser user = mAuth.getCurrentUser();
                            enviarPontosUserParaFirestore(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("googleFalhou", "Login com google falhou");
                            updateUI(null);
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
    public void OnRegisterSelected(final String email, final String password, final TextView errorMessage) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    enviarPontosUserParaFirestore(user);
                    errorMessage.setText("Conta registada com sucesso!");
                    errorMessage.setVisibility(View.VISIBLE);
                    sendEmailVerification();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            errorMessage.setVisibility(View.INVISIBLE);
                            OnBackToLoginPageSelected();
                        }
                    }, 2500);
                } else {
                    errorMessage.setText("O registo de conta falhou!");
                    errorMessage.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            errorMessage.setVisibility(View.INVISIBLE);
                            updateUI(null);
                        }
                    }, 2500); //desparece passados 2,5 segundos
                    Log.d("registoFalhou", "o registo falhou");
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

    /**
     * Função para atualizar a UI do utilizador
     *
     * @param user o utilizador
     */
    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (user != null) {
            if (user.isEmailVerified()) {
                Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
                startActivity(intent);
                finish();
            } else {
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.mainActivity, loginFragment, "home");
                ft.addToBackStack("home");
                ft.commit();
            }
        } else if (account != null) {
            Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
            startActivity(intent);
            finish();
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.mainActivity, loginFragment, "home");
            ft.addToBackStack("home");
            ft.commit();
        }
    }

    /**
     * Função para enviar um email de verificação para o email do user que se registou na app
     */
    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TAG", "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Função para enviar para o documento do user na cloud firestore a informação das perguntas
     *
     * @param user o utilizador
     */
    private void enviarPontosUserParaFirestore(final FirebaseUser user) {

        final DocumentReference documentReference = db.collection("users").document(user.getEmail());

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("Error", error.getMessage());
                } else {
                    if (value.exists()) {
                        Log.d("ErrorDB", "ja existe um documento com esta referencia");
                    } else {
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("pontos", 0);
                        user1.put("numRespostasCorretas", 0);
                        user1.put("numRespostasErradas", 0);
                        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("EnviarDadosSuccess", "Foram enviados os pontos para o user");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("EnviarDadosFailure", "Erro ao  enviar os pontos para o user");
                            }
                        });
                    }
                }
            }
        });
    }
}