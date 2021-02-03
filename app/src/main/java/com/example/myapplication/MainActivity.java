package com.example.myapplication;

import android.Manifest;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LoginSelected, RegisterSelected{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient signInClient;
    private JobScheduler mScheduler;
    private final static int RC_SIGN_IN = 123;
    private boolean loginPeloGoogle = false;

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

        Intent intent = new Intent(this, CheckLocationService.class);
        startService(intent);

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
                if(task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    loginPeloGoogle = false;
                    /**
                    Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
                    intent.putExtra("mailUser", mail);
                    intent.putExtra("passUser", pass);
                    startActivity(intent);
                    finish();
                     */
                    errorMessages.setText("Login bem sucedido");
                }
                else {
                    updateUI(null);
                    errorMessages.setText("Login falhado");
                }
            }
        });
    }

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
        Log.d("AquiTbm", "chegou aqui tbm");
        loginPeloGoogle = true;

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

                Log.d("Aqui", "chegou aqui");
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


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
                                updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("googleFalhou", "Login com google falhou");
                                updateUI(null);
                            }

                            // ...
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

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(user != null) {
            String mail = user.getEmail();
            Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
            intent.putExtra("mailUser", mail);
            intent.putExtra("googleLogin", loginPeloGoogle);
            startActivity(intent);
            finish();
            CriarPergunta pergunta = new CriarPergunta();
            pergunta.gerarNovaPergunta();
        }
        else if(account != null) {
            String mail = account.getEmail();

            Intent intent = new Intent(MainActivity.this, MenuPrincipalAtivity.class);
            intent.putExtra("mailUser", mail);
            intent.putExtra("googleLogin", loginPeloGoogle);
            startActivity(intent);
            finish();
            CriarPergunta pergunta = new CriarPergunta();
            pergunta.gerarNovaPergunta();
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