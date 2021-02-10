package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditarDadosFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleSignInClient signInClient;
    private Button cancelar, editarPass;
    private EditText password, confirmarPass;

    public EditarDadosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(getContext(), signInOptions);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editar_dados_fragment, container, false);

        cancelar = v.findViewById(R.id.buttonCancelar);
        editarPass = v.findViewById(R.id.buttonConfirmar);
        password = v.findViewById(R.id.editTextTextPassword2);
        confirmarPass = v.findViewById(R.id.editTextTextPassword3);


        cancelar.setOnClickListener(this);
        editarPass.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //botão que irá cancelar a operação do user e leva-lo para a página de login
            case R.id.buttonCancelar:
                Intent intent = new Intent(EditarDadosFragment.super.getContext(), MenuPrincipalAtivity.class);
                startActivity(intent);
                break;

            //botão que irá editar a palavra-passe do user
            case R.id.buttonConfirmar:

                String mudarPass = password.getText().toString();
                String confirmarPassword = confirmarPass.getText().toString();

                if (mudarPass.isEmpty()) {
                    password.setError("Palavra passe é nula");
                } else if (mudarPass.length() < 6) {
                    password.setError("A palavra passe necessita no mínimo de 6 caracteres");
                } else {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (mudarPass.equals(confirmarPassword)) {
                        firebaseUser.updatePassword(mudarPass);
                        Intent intent2 = new Intent(EditarDadosFragment.super.getContext(), MenuPrincipalAtivity.class);
                        startActivity(intent2);
                    } else {
                        password.setError("Palavra passe não compatível");
                    }
                }
                break;
        }
    }
}