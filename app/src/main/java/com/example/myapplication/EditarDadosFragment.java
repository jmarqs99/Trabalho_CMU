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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditarDadosFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Button cancelar, editarPass;
    private EditText password;

    public EditarDadosFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.editar_dados_fragment, container, false);

        cancelar = v.findViewById(R.id.buttonCancelar);
        editarPass = v.findViewById(R.id.buttonConfirmar);
        password = v.findViewById(R.id.editTextTextPassword2);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditarDadosFragment.super.getContext(), MenuPrincipalAtivity.class);
                startActivity(intent);
            }
        });

        editarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mudarPass = password.getText().toString();

                if(mudarPass.isEmpty()) {
                    password.setError("Palavra passe é nula");
                }
                else if(mudarPass.length() < 6) {
                    password.setError("A palavra passe necessita no mínimo de 6 caracteres");
                }
                else {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    firebaseUser.updatePassword(mudarPass);

                    Intent intent = new Intent(EditarDadosFragment.super.getContext(), MenuPrincipalAtivity.class);
                    startActivity(intent);
                }

            }
        });

        return v;
    }
}