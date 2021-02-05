package com.example.myapplication;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    private FirebaseAuth mAuth;
    private GoogleSignInClient signInClient;
    LogoutSelected lg;
    EditarDadosSelected ed;
    private TextView emailUser, pontos;
    private Button loggout, editarPass;
    private String mailText;
    private int pontosUser;
    //private FirebaseFirestore db;
    FirebaseUser currentUser;


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

       // db = FirebaseFirestore.getInstance();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {



        mailText = getArguments().getString("maill");
        pontosUser = getArguments().getInt("pontos");

        View v = inflater.inflate(R.layout.perfil_fragment, container, false);

        Bundle b = getActivity().getIntent().getExtras();

        emailUser = v.findViewById(R.id.textViewEmailPerfil);
        loggout = v.findViewById(R.id.buttonLogout);
        editarPass = v.findViewById(R.id.buttonEditarPassword);
        pontos = v.findViewById(R.id.textViewPontos);

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
        pontos.setText("Os meus pontos: " + this.pontosUser);

        loggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lg.OnLogoutSelect();
            }
        });

        return v;
    }
}
