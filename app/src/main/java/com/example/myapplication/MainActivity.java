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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LoginSelected, RegisterSelected{

    private FirebaseAuth mAuth;
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

        Intent intent = new Intent(this,CheckLocation.class);
        startService(intent);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, NewQuestionsBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        am.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + 500, AlarmManager.INTERVAL_DAY, pi);
        Log.d("Main","Alarm setted up");

        mAuth = FirebaseAuth.getInstance();

        /**
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainActivity, loginFragment, "home");
        ft.addToBackStack("home");
        ft.commit();
*/
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getPackageName(),
                NewQuestionsService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceName)
                .setPeriodic(JobInfo.getMinPeriodMillis());
        JobInfo myJobInfo = builder.build();
        mScheduler.schedule(myJobInfo);
        Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT)
                .show();
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

                    //enviarDadosParaPerfil(mail, pass);
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
    public void onRegisterButtonSelected() {
        RegistarUserFragment registarUser = new RegistarUserFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.mainActivity, registarUser, "registarUtilizador");
        ft.addToBackStack("registarUtilizador");
        ft.commit();
    }

    @Override
    public void enviarDadosParaPerfil(String mail, String pass) {
        Bundle b = new Bundle();
        b.putString("mail", mail);
        b.putString("pass", pass);
        PerfilFragment perfilFragment = new PerfilFragment();
        perfilFragment.setArguments(b);
    }

    @Override
    public void OnRegisterSelected(String email, String password, final TextView errorMessage) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
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