package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilFragment extends Fragment {

    private String pontosDoUser;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private GoogleSignInClient signInClient;
    LogoutSelected lg;
    EditarDadosSelected ed;
    private TextView emailUser;
    private TextView pontos;
    private TextView TextViewnumCorretas;
    private TextView TextViewnumErradas;
    private TextView TextViewpercCorretas;
    private TextView TextViewpercErradas;
    private Button loggout, editarPass, partilhar;
    private String mailText;
    private int pontosUser;
    private FirebaseUser currentUser;
    private ProgressDialog dialog;
    private final String partilharText = "Vem conhecer a nossa App  'FutQuizz'!!! Segue o link github em anexo: https://github.com/jmarqs99/Trabalho_CMU ";


    public PerfilFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(PerfilFragment.super.getContext(), signInOptions);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser  = mAuth.getCurrentUser();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.lg = (LogoutSelected)  context;
        this.ed = (EditarDadosSelected) context;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        dialog = ProgressDialog.show(getActivity(), "",
                "A carregar dados...", true);
        mailText = getArguments().getString("maill");
        pontosUser = getArguments().getInt("pontos");

        View v = inflater.inflate(R.layout.perfil_fragment, container, false);

        Bundle b = getActivity().getIntent().getExtras();

        emailUser = v.findViewById(R.id.textViewEmailPerfil);
        loggout = v.findViewById(R.id.buttonLogout);
        editarPass = v.findViewById(R.id.buttonEditarPassword);
        pontos = v.findViewById(R.id.textViewPontos);
        partilhar = v.findViewById(R.id.partilhar);
        TextViewnumCorretas = v.findViewById(R.id.num_corretas);
        TextViewnumErradas = v.findViewById(R.id.num_erradas);
        TextViewpercCorretas = v.findViewById(R.id.percent_corretas);
        TextViewpercErradas = v.findViewById(R.id.percent_erradas);

        retornarDadosFirestore(mailText);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(PerfilFragment.super.getContext());

        if(account != null) {
            editarPass.setVisibility(v.GONE);
        }

        if(signInClient.getApiOptions().getServerClientId() == mAuth.getUid()) {
            editarPass.setVisibility(v.GONE);
        }

        Log.d("ABC", mAuth.toString());
        Log.d("CBA", signInClient.toString());

        editarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.editarPass();
            }
        });

        emailUser.setText(this.mailText);

        loggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lg.OnLogoutSelect();
            }
        });


        partilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, partilharText);
                intent.setType("text/plain");
                intent = Intent.createChooser(intent,  "Share by");
                startActivity(intent);
            }
        });

        return v;
    }

    private void retornarDadosFirestore(String mail) {
        final DocumentReference docRef = db.collection("users").document(mail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Long pontosUser = (Long) document.get("pontos");
                        Long numCorretas = (Long)document.get("numRespostasCorretas");
                        Long numErradas = (Long)document.get("numRespostasErradas");

                        pontos.setText("QI Desportivo:\n" + pontosUser + "");
                        TextViewnumCorretas.setText("Nº de Respostas Corretas:\n" + numCorretas + "");
                        TextViewnumErradas.setText("Nº de Respostas Erradas:\n" + numErradas + "");
                        if(numCorretas + numErradas == 0){

                            TextViewpercCorretas.setText("% Respostas Corretas:\n" +"0%");
                            TextViewpercErradas.setText("% Respostas Erradas:\n" + "0%");
                        }else{
                            DecimalFormat df = new DecimalFormat();
                            df.setMaximumFractionDigits(1);
                            float percentagemCorretas = (Float.valueOf(numCorretas)/ Float.valueOf(numCorretas + numErradas))*100f;
                            float percentagemErradas = (Float.valueOf(numErradas)/ Float.valueOf(numCorretas + numErradas))*100f;
                            TextViewpercCorretas.setText("% Respostas Corretas:\n" + df.format(percentagemCorretas) + "%");
                            TextViewpercErradas.setText("% Respostas Erradas:\n" + df.format(percentagemErradas) + "%");
                        }
                        pontos.setVisibility(View.VISIBLE);
                        TextViewnumCorretas.setVisibility(View.VISIBLE);
                        TextViewnumErradas.setVisibility(View.VISIBLE);
                        TextViewpercCorretas.setVisibility(View.VISIBLE);
                        TextViewpercErradas.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

}

}
